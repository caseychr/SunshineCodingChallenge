package com.g0zi0.sunshinecodingchallenge.ui

import android.Manifest
import android.animation.ObjectAnimator
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.g0zi0.sunshinecodingchallenge.*
import com.g0zi0.sunshinecodingchallenge.model.CurrentWeather
import com.g0zi0.sunshinecodingchallenge.model.DailyForecast
import com.g0zi0.sunshinecodingchallenge.model.Forecasts
import com.g0zi0.sunshinecodingchallenge.viewmodel.ForecastViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class ForecastFragment: Fragment() {

    companion object {
        fun newInstance(): ForecastFragment {
            return ForecastFragment()
        }
    }
    lateinit var cityTextView: TextView
    lateinit var weatherImageView: ImageView
    lateinit var weatherTextView: TextView
    lateinit var degreeTextView: TextView
    lateinit var feelsLikeTextView: TextView
    lateinit var recyclerView: RecyclerView
    lateinit var refreshImageView: ImageView

    lateinit var fusedLocationClient: FusedLocationProviderClient
    var latitude: Double? = null
    var longitude: Double? = null

    private val viewModel by viewModels<ForecastViewModel>()
    private lateinit var forecastAdapter: ForecastAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this@ForecastFragment.requireContext())
        lastLocationCall()
        return inflater.inflate(R.layout.fragment_forecast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cityTextView = view.findViewById(R.id.cityTextView)
        weatherImageView = view.findViewById(R.id.weatherImageView)
        weatherTextView = view.findViewById(R.id.weatherTextView)
        degreeTextView = view.findViewById(R.id.degreesTextView)
        feelsLikeTextView = view.findViewById(R.id.feelsLikeTempTextView)
        recyclerView = view.findViewById(R.id.forecastRecyclerView)
        refreshImageView = view.findViewById(R.id.refreshImageView)
        onSubscribe()
        viewModel.getForecasts()
    }

    private fun onSubscribe() {
        viewModel.apply {
            forecastsLiveData.observe(viewLifecycleOwner, ResourceViewObserver(getForecastWeatherResourceView))
            currentWeatherLiveData.observe(viewLifecycleOwner, ResourceViewObserver(getCurrentWeatherResourceView))
        }
    }

    private val getCurrentWeatherResourceView = object : Resource.ResourceView<CurrentWeather> {
        override fun showData(data: CurrentWeather) {
            initCurrentWeatherView(data)
        }

        override fun showLoading(isLoading: Boolean) { onLoading(isLoading) }
        override fun showError(throwable: Throwable) { onError(throwable.localizedMessage) }
    }

    private val getForecastWeatherResourceView = object : Resource.ResourceView<Forecasts> {
        override fun showData(data: Forecasts) {
            initRecyclerView(data.dailyForecast)
            viewModel.getCurrentWeather(latitude.toString(), longitude.toString())
        }
        override fun showLoading(isLoading: Boolean) { onLoading(isLoading) }
        override fun showError(throwable: Throwable) { onError(throwable.localizedMessage) }
    }

    private fun initRecyclerView(forecasts: List<DailyForecast>) {
        forecastAdapter = ForecastAdapter(forecasts)
        recyclerView.layoutManager = LinearLayoutManager(this@ForecastFragment.context)
        recyclerView.adapter = forecastAdapter
    }

    private fun initCurrentWeatherView(currentWeather: CurrentWeather) {
        cityTextView.fadeInText()
        weatherImageView.fadeInText()
        weatherTextView.fadeInText()
        degreeTextView.fadeInText()
        feelsLikeTextView.fadeInText()
        cityTextView.text //TODO location is not coming through in call so figure this out
        weatherImageView.setImageResource(loadWeatherIcon(currentWeather.weather[0].icon))
        weatherTextView.text = currentWeather.weather[0].description.capitalizeFirstLetters()
        degreeTextView.text = getString(R.string.degree, currentWeather.main.temperature.toInt().toString())
        feelsLikeTextView.text = getString(R.string.feelsLikeCurrentWeatherDegree, currentWeather.main.feelsLike.toInt().toString())
        refreshImageView.setOnClickListener {
            refreshImageView.rotateButton()
            viewModel.getCurrentWeather(latitude.toString(), longitude.toString())
        }
    }

    private fun onLoading(isLoading: Boolean) {

    }

    private fun onError(msg: String) {

    }

    fun loadWeatherIcon(iconValue: String): Int { //TODO belongs in extension functions class
        var res: Int = -1
        res = when(iconValue) {
            "01d" -> R.drawable.ic_weather_sunny_white
            "01n" -> R.drawable.ic_weather_moon_white
            "02d" -> R.drawable.ic_weather_cloudy_white
            "02n" -> R.drawable.ic_weather_cloudy_night_white
            "03d" -> R.drawable.ic_weather_partly_sunny_white
            "03n" -> R.drawable.ic_weather_cloudy_white
            "04d" -> R.drawable.ic_weather_cloudy_white
            "04n" -> R.drawable.ic_weather_cloudy_white
            "09d" -> R.drawable.ic_weather_rainy_white
            "09n" -> R.drawable.ic_weather_rainy_white
            "10d" -> R.drawable.ic_weather_rainy_2_white
            "10n" -> R.drawable.ic_weather_rainy_2_white
            "11d" -> R.drawable.ic_weather_stormy_white
            "11n" -> R.drawable.ic_weather_stormy_white
            "13d" -> R.drawable.ic_weather_rainy_2_white
            "13n" -> R.drawable.ic_weather_rainy_2_white
            "50d" -> R.drawable.ic_weather_sunny_white
            "50n" -> R.drawable.ic_weather_sunny_white
            else -> R.drawable.ic_weather_cloudy_white
        }
        return res
    }

    private fun lastLocationCall() {
        if (ActivityCompat.checkSelfPermission(
                this@ForecastFragment.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this@ForecastFragment.requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener {location ->
            latitude = location?.latitude
            longitude = location?.longitude
        }
    }

}