package com.g0zi0.sunshinecodingchallenge.repository

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.g0zi0.sunshinecodingchallenge.Resource
import com.g0zi0.sunshinecodingchallenge.api.WeatherApi
import com.g0zi0.sunshinecodingchallenge.model.CurrentWeather
import com.g0zi0.sunshinecodingchallenge.model.Forecasts

class Repository(val context: Context, val weatherApi: WeatherApi,
                 val sharedPreferences: SharedPreferences = context.getSharedPreferences("com.g0zi0.sunshinecodingchallenge_preferences", Context.MODE_PRIVATE)) {

    init {
        sharedPreferences.edit().putString(apiKey, "3aa158b2f14a9f493a8c725f8133d704").apply()
        sharedPreferences.edit().putString(units, "imperial").apply()
    }

    suspend fun getForecasts(lat: String, lon: String): Resource<Forecasts> {
        return loadApiResource { weatherApi.getForecasts(lat, lon, 10, sharedPreferences.getString("units", "").toString(), sharedPreferences.getString("apiKey", "").toString()) }
    }

    suspend fun getCurrentWeather(lat: String, lon: String): Resource<CurrentWeather> {
        return loadApiResource { weatherApi.getCurrentWeather(lat, lon, sharedPreferences.getString("units", "").toString(), sharedPreferences.getString("apiKey", "").toString()) }
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

const val units = "units"
const val apiKey = "apiKey"