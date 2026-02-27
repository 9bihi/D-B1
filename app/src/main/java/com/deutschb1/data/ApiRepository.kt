package com.deutschb1.data

import com.deutschb1.network.RetrofitClient
import com.deutschb1.network.TranslateRequest
import com.deutschb1.network.VerbConjugation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    /** Translate text using LibreTranslate */
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

    /** Fetch the definition of a word in English from Free Dictionary API */
    suspend fun fetchWordDefinition(word: String): Result<String> =
        withContext(Dispatchers.IO) {
            try {
                val entries = RetrofitClient.dictionaryApi.getDefinition(word)
                val definition = entries
                    .firstOrNull()
                    ?.meanings
                    ?.firstOrNull()
                    ?.definitions
                    ?.firstOrNull()
                    ?.definition
                    ?: "No definition found."
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
}
