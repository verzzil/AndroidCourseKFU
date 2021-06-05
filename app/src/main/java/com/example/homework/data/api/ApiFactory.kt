package com.example.homework.data.api

import com.example.homework.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiFactory {

    private const val QUERY_API_KEY = "appid"
    private const val QUERY_METRIC = "units"
    private const val QUERY_LANG = "lang"

    private val apiKeyInterceptor = Interceptor { chain ->
        val original = chain.request()
        original.url().newBuilder()
            .addQueryParameter(QUERY_API_KEY, BuildConfig.API_KEY)
            .build()
            .let {
                chain.proceed(
                    original.newBuilder().url(it).build()
                )
            }
    }
    private val metricInterceptor = Interceptor { chain ->
        val original = chain.request()
        original.url().newBuilder()
            .addQueryParameter(QUERY_METRIC, BuildConfig.METRIC)
            .build()
            .let {
                chain.proceed(
                    original.newBuilder().url(it).build()
                )
            }
    }
    private val langInterceptor = Interceptor { chain ->
        val original = chain.request()
        original.url().newBuilder()
            .addQueryParameter(QUERY_LANG, BuildConfig.LANG)
            .build()
            .let {
                chain.proceed(
                    original.newBuilder().url(it).build()
                )
            }
    }

    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(metricInterceptor)
            .addInterceptor(langInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val weatherApi: WeatherApi by lazy {
        retrofit.create(WeatherApi::class.java)
    }
}