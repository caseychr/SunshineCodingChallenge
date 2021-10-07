package com.g0zi0.sunshinecodingchallenge.repository

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.g0zi0.sunshinecodingchallenge.Resource
import com.g0zi0.sunshinecodingchallenge.api.WeatherApi
import com.g0zi0.sunshinecodingchallenge.model.CurrentWeather
import com.g0zi0.sunshinecodingchallenge.model.Forecasts

class Repository(val context: Context, private val weatherApi: WeatherApi,
                 val sharedPreferences: SharedPreferences = context.getSharedPreferences("com.g0zi0.sunshinecodingchallenge_preferences", Context.MODE_PRIVATE)) {

    init {
        sharedPreferences.edit().putString(apiKey, "3aa158b2f14a9f493a8c725f8133d704").apply()
        sharedPreferences.edit().putString(units, "imperial").apply()
    }

    /**
     * Fetches forecasts for [lat] [lon] location
     */
    suspend fun getForecasts(lat: String, lon: String): Resource<Forecasts> {
        return loadApiResource { weatherApi.getForecasts(lat, lon, 10, sharedPreferences.getString("units", "").toString(), sharedPreferences.getString("apiKey", "").toString()) }
    }

    /**
     * Fetches currentWeather for [lat] [lon] location.
     * [Units] set to imperial and [apikey] retrieved from shared prefs
     */
    suspend fun getCurrentWeather(lat: String, lon: String): Resource<CurrentWeather> {
        return loadApiResource { weatherApi.getCurrentWeather(lat, lon, sharedPreferences.getString("units", "").toString(), sharedPreferences.getString("apiKey", "").toString()) }
    }

    /**
     * Runs a suspended loader function which should return [T], this will await for the result
     * of [T] and return the result in a wrapped [Resource] of [T]. The [Resource] will
     * either be a [Resource.Success] if the loading was successful a [Resource.NotFound] if the
     * loading failed due to the resource not existing or [Resource.Error] if there was a error while
     * trying to load [T].
     */
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