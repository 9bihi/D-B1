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
import java.net.URLEncoder

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val message: String) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
}

object ApiRepository {

    private val api: GermanApiService = RetrofitClient.api

    /** Wiktionary Lookup */
    suspend fun lookupWord(word: String): ApiResult<WiktionaryDefinition> = withContext(Dispatchers.IO) {
        try {
            val url = "https://en.wiktionary.org/api/rest_v1/page/definition/${word.lowercase().trim()}"
            val response = api.lookupWord(url)
            val body = response.body()
            val deSection = body?.get("de")
            
            if (deSection.isNullOrEmpty()) {
                ApiResult.Error("Kein Eintrag für \"$word\" gefunden.")
            } else {
                val entries = deSection.map { entry ->
                    WiktEntryDetail(
                        partOfSpeech = entry.partOfSpeech ?: "",
                        definitions = entry.definitions.map { it.definition.stripHtml() },
                        examples = entry.definitions.flatMap { it.examples?.map { ex -> ex.stripHtml() } ?: emptyList() }
                    )
                }
                ApiResult.Success(WiktionaryDefinition(word, entries))
            }
        } catch (e: Exception) {
            ApiResult.Error("Verbindung fehlgeschlagen. Bitte erneut versuchen.")
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

    /** Translation with MyMemory + LibreTranslate Fallback */
    suspend fun translate(text: String, langpair: String): ApiResult<String> = withContext(Dispatchers.IO) {
        try {
            // PRIMARY: MyMemory
            val encodedText = URLEncoder.encode(text, "UTF-8")
            val url = "https://api.mymemory.translated.net/get?q=$encodedText&langpair=$langpair"
            val resp = api.translateMyMemory(url)
            val body = resp.body()
            
            if (resp.isSuccessful && body != null && body.responseStatus == 200) {
                ApiResult.Success(body.responseData.translatedText)
            } else if (resp.code() == 403 || (body != null && body.responseStatus == 403)) {
                translateWithLibre(text, langpair) // Fallback
            } else {
                ApiResult.Error("Übersetzung fehlgeschlagen (${body?.responseStatus ?: resp.code()}).")
            }
        } catch (e: Exception) {
            ApiResult.Error("Keine Verbindung. Bitte erneut versuchen.")
        }
    }

    private suspend fun translateWithLibre(text: String, langpair: String): ApiResult<String> {
        return try {
            val parts = langpair.split("|")
            val url = "https://translate.argosopentech.com/translate"
            val body = LibreTranslateRequest(q = text, source = parts[0], target = parts[1])
            val resp = api.translateLibre(url, body)
            if (resp.isSuccessful && resp.body() != null) {
                ApiResult.Success(resp.body()!!.translatedText)
            } else {
                ApiResult.Error("Tages-Limit erreicht und Backup fehlgeschlagen.")
            }
        } catch (e: Exception) {
            ApiResult.Error("Alle Übersetzungsdienste nicht erreichbar.")
        }
    }
}

/** Extension to strip HTML tags from Wiktionary strings */
fun String.stripHtml(): String {
    return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString().trim()
}
