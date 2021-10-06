package com.g0zi0.sunshinecodingchallenge.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {

    fun service(): WeatherApi {
        return buildRetrofit().create(WeatherApi::class.java)
    }

    /**
     * Builds Retrofit instance with CoroutineCallAdapter and GsonConverter for Modeling
     */
    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildClient())
            .build()
    }

    /**
     * Builds Http client for logging
     */
    private fun buildClient(): OkHttpClient {
        val logging  = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }
}

private const val BASE_URL = "https://api.openweathermap.org"