package com.deutschb1.network

import com.deutschb1.BuildConfig
import com.deutschb1.DeutschB1Application
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private val context = DeutschB1Application.getContext()

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE
    }

    private val cache = Cache(File(context.cacheDir, "http_cache"), 100L * 1024 * 1024) // 100 MB

    val httpClient: OkHttpClient = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    /** 
     * Centralized API service using @Url on endpoints.
     * The base URL is just a placeholder because all methods use absolute URLs via @Url.
     */
    val api: GermanApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://localhost/") // Placeholder
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GermanApiService::class.java)
    }
}
