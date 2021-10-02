package com.g0zi0.sunshinecodingchallenge.api

import com.g0zi0.sunshinecodingchallenge.model.CurrentWeather
import com.g0zi0.sunshinecodingchallenge.model.Forecasts
import retrofit2.http.GET

interface WeatherApi {

    @GET("/data/2.5/forecast/daily?q=Atlanta&mode=json&cnt=10&units=imperial&apikey=3aa158b2f14a9f493a8c725f8133d704")
    suspend fun getForecasts(key: String): Forecasts

    @GET("/data/2.5/weather?lat=35&lon=139&appid=3aa158b2f14a9f493a8c725f8133d704")
    suspend fun getCurrentWeather(lat: String, lon: String, key: String): CurrentWeather
}