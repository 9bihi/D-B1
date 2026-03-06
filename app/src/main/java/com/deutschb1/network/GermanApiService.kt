package com.deutschb1.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

// ─── Wiktionary ──────────────────────────────────────────────────────────────

data class WiktDefinitionEntry(
    val definition: String,
    val examples: List<String>? = null
)

data class WiktEntry(
    val partOfSpeech: String?,
    val definitions: List<WiktDefinitionEntry>
)

// The API returns a Map<String, List<WiktEntry>> where key is language code "de"
typealias WiktionaryResponse = Map<String, List<WiktEntry>>

data class WiktionaryDefinition(
    val word: String,
    val entries: List<WiktEntryDetail>
)

data class WiktEntryDetail(
    val partOfSpeech: String,
    val definitions: List<String>,
    val examples: List<String>
)

// ─── Verb Conjugation ────────────────────────────────────────────────────────

data class VerbConjugation(
    val verb: String,
    val auxiliary: String,
    val praesens: Map<String, String>,
    val praeteritum: Map<String, String>,
    val perfekt: Map<String, String>,
    val konjunktiv2: Map<String, String>,
    val imperativ: Map<String, String>
)

// ─── Translation ──────────────────────────────────────────────────────────

data class MyMemoryResponse(
    val responseData: MyMemoryData,
    val responseStatus: Int,
    val quotaFinished: Boolean? = null
)

data class MyMemoryData(
    val translatedText: String
)

data class LibreTranslateRequest(
    val q: String,
    val source: String,
    val target: String,
    val format: String = "text",
    val api_key: String = ""
)

data class LibreTranslateResponse(
    val translatedText: String
)

// ─── API Interface ──────────────────────────────────────────────────────────

interface GermanApiService {
    @GET
    suspend fun lookupWord(@Url url: String): Response<WiktionaryResponse>

    @GET
    suspend fun conjugateVerb(@Url url: String): Response<VerbConjugation>

    @GET
    suspend fun translateMyMemory(@Url url: String): Response<MyMemoryResponse>

    @POST
    suspend fun translateLibre(@Url url: String, @Body body: LibreTranslateRequest): Response<LibreTranslateResponse>
}
