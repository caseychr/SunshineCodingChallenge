package com.g0zi0.sunshinecodingchallenge.api

import com.g0zi0.sunshinecodingchallenge.model.CurrentWeather
import com.g0zi0.sunshinecodingchallenge.model.Forecasts
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {

    @GET("/data/2.5/forecast/daily")
    suspend fun getForecasts(@Query("lat") lat: String, @Query("lon") lon: String,
                             @Query("cnt") count: Int, @Query("units") units: String, @Query("appid") key: String): Forecasts

    @GET("/data/2.5/weather")
    suspend fun getCurrentWeather(@Query("lat") lat: String, @Query("lon") lon: String, @Query("units") units: String, @Query("appid") key: String): CurrentWeather
}