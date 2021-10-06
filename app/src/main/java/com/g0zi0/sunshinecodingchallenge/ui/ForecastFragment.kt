package com.g0zi0.sunshinecodingchallenge.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
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
import com.google.android.material.snackbar.Snackbar

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
    lateinit var toolbar: Toolbar

    private val locationRequestCode = 1000

    lateinit var fusedLocationClient: FusedLocationProviderClient

    private val viewModel by viewModels<ForecastViewModel>()
    private lateinit var forecastAdapter: ForecastAdapter

    lateinit var sView: View

    var wayLatitude = 0.0
    var wayLongitude = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
   //     (activity as ForecastActivity).supportActionBar?.show()
     //   (activity as ForecastActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this@ForecastFragment.requireContext())
        checkPerms()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sView = inflater.inflate(R.layout.fragment_forecast, container, false)
    //    toolbar = activity?.findViewById(R.id.toolbar)!!//sView.findViewById(R.id.toolbar)
   //     (activity as ForecastActivity).supportActionBar?.customView
      //  (activity as ForecastActivity).setSupportActionBar(toolbar)
        return sView//inflater.inflate(R.layout.fragment_forecast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //toolbar = view.findViewById(R.id.toolbar)
        cityTextView = view.findViewById(R.id.cityTextView)
        weatherImageView = view.findViewById(R.id.weatherImageView)
        weatherTextView = view.findViewById(R.id.weatherTextView)
        degreeTextView = view.findViewById(R.id.degreesTextView)
        feelsLikeTextView = view.findViewById(R.id.feelsLikeTempTextView)
        recyclerView = view.findViewById(R.id.forecastRecyclerView)
        refreshImageView = view.findViewById(R.id.refreshImageView)
        onSubscribe()
    }

    private fun onSubscribe() {
        viewModel.apply {
            forecastsLiveData.observe(
                viewLifecycleOwner, ResourceViewObserver(
                    getForecastWeatherResourceView
                )
            )
            currentWeatherLiveData.observe(
                viewLifecycleOwner, ResourceViewObserver(
                    getCurrentWeatherResourceView
                )
            )
        }
    }

    private val getCurrentWeatherResourceView = object : Resource.ResourceView<CurrentWeather> {
        override fun showData(data: CurrentWeather) {
            initCurrentWeatherView(data)
        }

        override fun showLoading(isLoading: Boolean) { /** Do nothing */ }
        override fun showError(throwable: Throwable) { handleError(throwable) }
    }

    private val getForecastWeatherResourceView = object : Resource.ResourceView<Forecasts> {
        override fun showData(data: Forecasts) {
            viewModel.getCurrentWeather(wayLatitude.toString(), wayLongitude.toString())
            cityTextView.text = data.city.name
            initRecyclerView(data.dailyForecast)
        }
        override fun showLoading(isLoading: Boolean) { /** Do nothing */ }
        override fun showError(throwable: Throwable) { handleError(throwable) }
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
        weatherImageView.setImageResource(loadWeatherIcon(currentWeather.weather[0].icon))
        weatherTextView.text = currentWeather.weather[0].description.capitalizeFirstLetters()
        degreeTextView.text = getString(
            R.string.degree,
            currentWeather.main.temperature.toInt().toString()
        )
        feelsLikeTextView.text = getString(
            R.string.feelsLikeCurrentWeatherDegree,
            currentWeather.main.feelsLike.toInt().toString()
        )
        refreshImageView.setOnClickListener {
            refreshImageView.rotateButton()
            viewModel.getCurrentWeather(wayLatitude.toString(), wayLongitude.toString())
        }
    }

    private fun handleError(error: Throwable) {
        error.printStackTrace()
        Snackbar.make(this.requireView(), error.message.toString(), Snackbar.LENGTH_SHORT).show()
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1000 -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty()
                    && grantResults[0] === PackageManager.PERMISSION_GRANTED
                ) {
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
                    fusedLocationClient.lastLocation.addOnSuccessListener((activity as ForecastActivity)) { location ->
                        if (location != null) {
                            wayLatitude = location.getLatitude()
                            wayLongitude = location.getLongitude()
                            Toast.makeText(this@ForecastFragment.requireContext(), "${wayLatitude}, ${wayLongitude}", Toast.LENGTH_SHORT).show()
                            //viewModel.getCurrentWeather(wayLatitude.toString(), wayLongitude.toString())
                            viewModel.getForecasts(wayLatitude.toString(), wayLongitude.toString())
                        }
                    }
                } else {
                    Toast.makeText(this@ForecastFragment.requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // check permission
    private fun checkPerms() {
        if (ActivityCompat.checkSelfPermission(
                this@ForecastFragment.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this@ForecastFragment.requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                (activity as ForecastActivity),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                locationRequestCode
            )

        } else {
            // already permission granted. Get lat lon and invoke getCurrentWeather
            fusedLocationClient.lastLocation.addOnSuccessListener((activity as ForecastActivity)) { location ->
                if (location != null) {
                    wayLatitude = location.latitude
                    wayLongitude = location.longitude
                    viewModel.getForecasts(wayLatitude.toString(), wayLongitude.toString())
                }
            }
        }
    }

}