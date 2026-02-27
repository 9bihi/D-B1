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

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
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

    /** LibreTranslate community mirror — no key required */
    val translateApi: TranslateApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://libretranslate.de/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TranslateApiService::class.java)
    }

    /** Free Dictionary API for word definitions */
    val dictionaryApi: DictionaryApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApiService::class.java)
    }
}
