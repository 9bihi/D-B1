package com.deutschb1.data

import com.deutschb1.network.RetrofitClient
import com.deutschb1.network.TranslateRequest
import com.deutschb1.network.VerbConjugation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val message: String) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
}

object ApiRepository {

    /** Fetch verb conjugation for a given verb + tense.
     *  Tense codes: PRASENS, PERFEKT, PRATERITUM, FUTUR1, KONJUNKTIV2
     */
    suspend fun fetchVerbConjugation(verb: String, tense: String = "PRASENS"): ApiResult<VerbConjugation> =
        withContext(Dispatchers.IO) {
            try {
                val result = RetrofitClient.verbApi.getConjugation(verb, tense)
                ApiResult.Success(result)
            } catch (e: Exception) {
                ApiResult.Error(e.localizedMessage ?: "Unknown error occurred")
            }
        }

    /** Translate text using LibreTranslate (translate.argosopentech.com — free, no key) */
    suspend fun translateText(text: String, source: String, target: String): ApiResult<String> =
        withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClient.translateApi.translate(
                    TranslateRequest(q = text, source = source, target = target)
                )
                val translated = response.translatedText ?: return@withContext ApiResult.Error(
                    "Empty translation response"
                )
                ApiResult.Success(translated)
            } catch (e: Exception) {
                ApiResult.Error(e.localizedMessage ?: "Translation failed")
            }
        }

    /**
     * Fetch the definition of a word using the Free Dictionary API.
     */
    suspend fun fetchWordDefinition(word: String): ApiResult<String> =
        withContext(Dispatchers.IO) {
            try {
                val url = "https://api.dictionaryapi.dev/api/v2/entries/en/${word.trim().lowercase()}"
                val json = URL(url).readText()
                val array = JSONArray(json)
                if (array.length() == 0) return@withContext ApiResult.Success("No definition found.")
                val entry = array.getJSONObject(0)
                val meanings = entry.optJSONArray("meanings")
                    ?: return@withContext ApiResult.Success("No definition found.")
                val firstMeaning = meanings.getJSONObject(0)
                val definitions = firstMeaning.optJSONArray("definitions")
                    ?: return@withContext ApiResult.Success("No definition found.")
                val definition = definitions.getJSONObject(0).optString("definition", "No definition found.")
                ApiResult.Success(definition)
            } catch (e: Exception) {
                ApiResult.Error(e.localizedMessage ?: "Could not fetch definition")
            }
        }

    /** Fetch the 5000-word German B1 word list from GitHub */
    suspend fun fetchB1WordList(): ApiResult<List<String>> =
        withContext(Dispatchers.IO) {
            try {
                val url = java.net.URL(
                    "https://raw.githubusercontent.com/Jonny-exe/German-Words-Library/master/German-words-5000-words.json"
                )
                val json = url.readText()
                // Parse the plain JSON array ["word1","word2",...]
                val words = json
                    .trim()
                    .removeSurrounding("[", "]")
                    .split(",")
                    .map { it.trim().removeSurrounding("\"") }
                    .filter { it.isNotBlank() }
                ApiResult.Success(words)
            } catch (e: Exception) {
                ApiResult.Error(e.localizedMessage ?: "Failed to load word list")
            }
        }

    /**
     * Common German B1 verbs for the Verb Conjugation browser.
     */
    val commonB1Verbs: List<String> = listOf(
        "sein", "haben", "werden", "können", "müssen", "dürfen", "wollen", "sollen",
        "mögen", "möchten", "lassen", "gehen", "kommen", "fahren", "fliegen", "laufen",
        "sprechen", "sagen", "fragen", "antworten", "erklären", "verstehen", "wissen",
        "denken", "glauben", "meinen", "finden", "suchen", "sehen", "schauen", "hören",
        "lesen", "schreiben", "lernen", "arbeiten", "spielen", "helfen", "kaufen", "zahlen",
        "essen", "trinken", "kochen", "schlafen", "aufstehen", "waschen", "anziehen",
        "treffen", "besuchen", "reisen", "wohnen", "leben", "heißen", "studieren",
        "machen", "tun", "nehmen", "geben", "bringen", "holen", "schicken", "öffnen",
        "schließen", "beginnen", "anfangen", "aufhören", "warten", "bleiben", "zeigen",
        "brauchen", "hoffen", "versuchen", "planen", "entscheiden", "erinnern", "vergessen",
        "kennen", "führen", "folgen", "passieren", "funktionieren", "ändern", "wechseln"
    ).sorted()
}
