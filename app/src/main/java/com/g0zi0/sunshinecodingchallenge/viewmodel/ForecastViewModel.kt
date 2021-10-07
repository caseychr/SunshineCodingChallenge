package com.g0zi0.sunshinecodingchallenge.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.g0zi0.sunshinecodingchallenge.Resource
import com.g0zi0.sunshinecodingchallenge.api.NetworkService
import com.g0zi0.sunshinecodingchallenge.model.CurrentWeather
import com.g0zi0.sunshinecodingchallenge.model.Forecasts
import com.g0zi0.sunshinecodingchallenge.repository.Repository

class ForecastViewModel(application: Application): BaseViewModel(application) {

    var repository = Repository(application.applicationContext, NetworkService.service())

    val currentWeatherLiveData = MutableLiveData<Resource<CurrentWeather>>()
    val forecastsLiveData = MutableLiveData<Resource<Forecasts>>()

    /**
     * @param lat
     * @param lon
     * Live data observable that receives [CurrentWeather] from repository
     */
    fun getCurrentWeather(lat: String, lon: String) {
        currentWeatherLiveData.loadResource { repository.getCurrentWeather(lat, lon) }
    }

    /**
     * @param lat
     * @param lon
     * Live data observable that receives [Forecasts] from repository
     */
    fun getForecasts(lat: String, lon: String) {
        forecastsLiveData.loadResource { repository.getForecasts(lat, lon) }
    }

}