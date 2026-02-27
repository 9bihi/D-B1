package com.deutschb1.data

import com.deutschb1.network.RetrofitClient
import com.deutschb1.network.TranslateRequest
import com.deutschb1.network.VerbConjugation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

object ApiRepository {

    /** Fetch verb conjugation for a given verb + tense.
     *  Tense codes: PRASENS, PERFEKT, PRATERITUM, FUTUR1, KONJUNKTIV2
     */
    suspend fun fetchVerbConjugation(verb: String, tense: String = "PRASENS"): Result<VerbConjugation> =
        withContext(Dispatchers.IO) {
            try {
                val result = RetrofitClient.verbApi.getConjugation(verb, tense)
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    /** Translate text using LibreTranslate (translate.argosopentech.com — free, no key) */
    suspend fun translateText(text: String, source: String, target: String): Result<String> =
        withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClient.translateApi.translate(
                    TranslateRequest(q = text, source = source, target = target)
                )
                val translated = response.translatedText ?: return@withContext Result.failure(
                    Exception("Empty translation response")
                )
                Result.success(translated)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    /**
     * Fetch the definition of a word using the Free Dictionary API.
     *
     * NOTE: This uses raw OkHttp (not Retrofit) because Gson crashes with
     * "Class cannot be cast to ParameterizedType" when deserializing List<T>
     * via Retrofit's reflection proxy.
     *
     * The word MUST be English. For German words, translate first, then call this.
     */
    suspend fun fetchWordDefinition(word: String): Result<String> =
        withContext(Dispatchers.IO) {
            try {
                val url = "https://api.dictionaryapi.dev/api/v2/entries/en/${word.trim().lowercase()}"
                val json = URL(url).readText()
                val array = JSONArray(json)
                if (array.length() == 0) return@withContext Result.success("No definition found.")
                val entry = array.getJSONObject(0)
                val meanings = entry.optJSONArray("meanings")
                    ?: return@withContext Result.success("No definition found.")
                val firstMeaning = meanings.getJSONObject(0)
                val definitions = firstMeaning.optJSONArray("definitions")
                    ?: return@withContext Result.success("No definition found.")
                val definition = definitions.getJSONObject(0).optString("definition", "No definition found.")
                Result.success(definition)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    /** Fetch the 5000-word German B1 word list from GitHub */
    suspend fun fetchB1WordList(): Result<List<String>> =
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
                Result.success(words)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    /**
     * Common German B1 verbs for the Verb Conjugation browser.
     * These are shown as a browsable list so the user can tap any verb instead of
     * having to type it manually.
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
