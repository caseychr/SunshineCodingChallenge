package com.g0zi0.sunshinecodingchallenge.repository

import com.g0zi0.sunshinecodingchallenge.Resource
import com.g0zi0.sunshinecodingchallenge.api.WeatherApi
import com.g0zi0.sunshinecodingchallenge.model.CurrentWeather
import com.g0zi0.sunshinecodingchallenge.model.Forecasts

class Repository(private val weatherApi: WeatherApi) {

    suspend fun getForecasts(): Resource<Forecasts> {
        return loadApiResource { weatherApi.getForecasts() }
    }

    suspend fun getCurrentWeather(lat: String, lon: String): Resource<CurrentWeather> {
        return loadApiResource { weatherApi.getCurrentWeather() }
    }

    suspend fun <T> loadApiResource(loader: suspend () -> T): Resource<T> {
        return try {
            Resource.Success((loader()))
        } catch (error: Exception) {
            error.printStackTrace()
            return Resource.Error(error)
        }
    }
}