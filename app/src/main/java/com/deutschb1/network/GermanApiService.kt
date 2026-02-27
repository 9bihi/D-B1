package com.deutschb1.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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
// NOTE: DictionaryApiService is intentionally NOT using Retrofit here.
// Retrofit + Gson crashes with "Class cannot be cast to ParameterizedType"
// when deserializing List<T> return types through Retrofit's dynamic proxy.
// Dictionary lookups are done via raw OkHttp in ApiRepository.fetchWordDefinition().
