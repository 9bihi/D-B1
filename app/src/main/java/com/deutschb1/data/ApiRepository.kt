package com.deutschb1.data

import android.text.Html
import com.deutschb1.network.GermanApiService
import com.deutschb1.network.LibreTranslateRequest
import com.deutschb1.network.RetrofitClient
import com.deutschb1.network.VerbConjugation
import com.deutschb1.network.WiktEntryDetail
import com.deutschb1.network.WiktionaryDefinition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.net.URLEncoder
import java.util.concurrent.TimeUnit

data class DictApiEntry(
    val word: String,
    val phonetic: String?,
    val meanings: List<DictMeaning>
)

data class DictMeaning(
    val partOfSpeech: String,
    val definitions: List<DictDefinition>
)

data class DictDefinition(
    val definition: String,
    val example: String?
)

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val message: String) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
}

object ApiRepository {

    private val api: GermanApiService = RetrofitClient.api

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(15, java.util.concurrent.TimeUnit.SECONDS)
        .readTimeout(15, java.util.concurrent.TimeUnit.SECONDS)
        .build()

    /** Dictionary Lookup: Online DictionaryAPI.dev */
    suspend fun lookupWord(word: String): ApiResult<List<DictApiEntry>> = withContext(Dispatchers.IO) {
        try {
            val encoded = URLEncoder.encode(word.lowercase().trim(), "UTF-8")
            val url = "https://api.dictionaryapi.dev/api/v2/entries/de/$encoded"
            val request = Request.Builder().url(url).get().build()
            val response = httpClient.newCall(request).execute()

            when (response.code) {
                200 -> {
                    val body = response.body?.string() ?: return@withContext ApiResult.Error("Leere Antwort.")
                    val type = object : com.google.gson.reflect.TypeToken<List<DictApiEntry>>() {}.type
                    val entries: List<DictApiEntry> = com.google.gson.Gson().fromJson(body, type)
                    ApiResult.Success(entries)
                }
                404 -> ApiResult.Error("\"$word\" nicht im Wörterbuch gefunden.")
                else -> ApiResult.Error("Fehler ${response.code}. Bitte erneut versuchen.")
            }
        } catch (e: Exception) {
            ApiResult.Error("Keine Verbindung. Bitte erneut versuchen.")
        }
    }

    /** Verb Conjugation */
    private fun sanitizeVerbForApi(verb: String): String = verb
        .lowercase().trim()
        .replace("ü", "ue").replace("ö", "oe")
        .replace("ä", "ae").replace("ß", "ss")

    suspend fun conjugateVerb(verb: String): ApiResult<VerbConjugation> = withContext(Dispatchers.IO) {
        try {
            val sanitized = sanitizeVerbForApi(verb)
            val url = "https://german-verbs-api.onrender.com/german-verbs-api/$sanitized"
            val response = api.conjugateVerb(url)
            if (response.isSuccessful && response.body() != null) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Verb \"$verb\" nicht gefunden.")
            }
        } catch (e: Exception) {
            ApiResult.Error("Verbindung fehlgeschlagen. Server lädt möglicherweise noch (~30s).")
        }
    }

    /** Translation: Google Translate gtx Endpoint (Guest) */
    suspend fun translate(text: String, langpair: String): ApiResult<String> = withContext(Dispatchers.IO) {
        try {
            val parts = langpair.split("|")
            if (parts.size != 2) return@withContext ApiResult.Error("Ungültiges Sprachpaar.")
            
            val sourceLang = parts[0]
            val targetLang = parts[1]
            
            val encoded = URLEncoder.encode(text, "UTF-8")
            val url = "https://translate.googleapis.com/translate_a/single" +
                      "?client=gtx&sl=$sourceLang&tl=$targetLang&dt=t&q=$encoded"
            
            val request = Request.Builder().url(url).get().build()
            val response = httpClient.newCall(request).execute()
            
            if (!response.isSuccessful) {
                return@withContext ApiResult.Error("Übersetzung fehlgeschlagen (HTTP ${response.code}).")
            }
            
            val body = response.body?.string()
                ?: return@withContext ApiResult.Error("Leere Antwort vom Server.")
            
            // Parse the nested JSON array manually
            val jsonArray = JSONArray(body)
            val translationsArray = jsonArray.getJSONArray(0)
            val sb = StringBuilder()
            for (i in 0 until translationsArray.length()) {
                val part = translationsArray.getJSONArray(i)
                if (!part.isNull(0)) sb.append(part.getString(0))
            }
            
            val result = sb.toString().trim()
            if (result.isBlank()) ApiResult.Error("Keine Übersetzung gefunden.")
            else ApiResult.Success(result)
            
        } catch (e: Exception) {
            ApiResult.Error("Keine Verbindung. Bitte erneut versuchen.")
        }
    }
}

/** Extension to strip HTML tags from Wiktionary strings */
fun String.stripHtml(): String {
    return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString().trim()
}
