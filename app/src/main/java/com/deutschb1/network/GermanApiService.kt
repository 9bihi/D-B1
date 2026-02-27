package com.deutschb1.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

// ─── Verb Conjugation ────────────────────────────────────────────────────────

data class VerbConjugation(
    val verb: String? = null,
    val tense: String? = null,
    val ich: String? = null,
    val du: String? = null,
    val er: String? = null,
    val wir: String? = null,
    val ihr: String? = null,
    val sie: String? = null
)

interface VerbApiService {
    @GET("german-verbs-api")
    suspend fun getConjugation(
        @Query("verb") verb: String,
        @Query("tense") tense: String = "PRASENS"
    ): VerbConjugation
}

// ─── LibreTranslate ──────────────────────────────────────────────────────────

data class TranslateRequest(
    val q: String,
    val source: String,
    val target: String,
    val format: String = "text"
)

data class TranslateResponse(
    val translatedText: String? = null
)

interface TranslateApiService {
    @POST("translate")
    suspend fun translate(@Body body: TranslateRequest): TranslateResponse
}

// ─── Free Dictionary API ─────────────────────────────────────────────────────

data class DictionaryPhonetic(
    val text: String? = null
)

data class DictionaryDefinition(
    val definition: String? = null,
    val example: String? = null
)

data class DictionaryMeaning(
    val partOfSpeech: String? = null,
    val definitions: List<DictionaryDefinition>? = null
)

data class DictionaryEntry(
    val word: String? = null,
    val phonetics: List<DictionaryPhonetic>? = null,
    val meanings: List<DictionaryMeaning>? = null
)

interface DictionaryApiService {
    @GET("api/v2/entries/en/{word}")
    suspend fun getDefinition(
        @Path("word") word: String
    ): List<DictionaryEntry>
}
