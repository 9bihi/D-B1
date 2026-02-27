package com.deutschb1.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val httpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()

    /** German Verb Conjugation API — https://german-verbs-api.onrender.com */
    val verbApi: VerbApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://german-verbs-api.onrender.com/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VerbApiService::class.java)
    }

    /**
     * LibreTranslate — free, no API key required.
     * Using translate.argosopentech.com as the public free mirror.
     * Fallback mirrors (in case this is down):
     *   https://libretranslate.de/  (often rate-limited)
     *   https://translate.terraprint.co/
     */
    val translateApi: TranslateApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://translate.argosopentech.com/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TranslateApiService::class.java)
    }

    // Note: DictionaryApiService has been removed from Retrofit because Gson cannot
    // deserialize List<T> through Retrofit's reflection proxy without a TypeToken.
    // Dictionary lookups now use raw OkHttp calls in ApiRepository.fetchWordDefinition().
}
