package com.g0zi0.sunshinecodingchallenge.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.g0zi0.sunshinecodingchallenge.R
import com.g0zi0.sunshinecodingchallenge.model.DailyForecast
import java.text.SimpleDateFormat
import java.util.*

class ForecastAdapter(var forecasts: List<DailyForecast>, private val onItemClick: OnItemClick): RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    class ViewHolder(viewItem: View): RecyclerView.ViewHolder(viewItem) {
        var weatherImageView: ImageView = itemView.findViewById(R.id.weatherImageView)
        var day: TextView = itemView.findViewById(R.id.dayTextView)
        var weather: TextView = itemView.findViewById(R.id.weatherTextView)
        var maxTemperature: TextView = itemView.findViewById(R.id.maxDegreesTextView)
        var minTemperature: TextView = itemView.findViewById(R.id.minDegreesTextView)
        var humidity: TextView = itemView.findViewById(R.id.humidityTextView)
        var pressure: TextView = itemView.findViewById(R.id.pressureTextView)
        var wind: TextView = itemView.findViewById(R.id.windTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_forecast,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            day.text = getDayOfWeek(forecasts[position].dt.toLong())
            weatherImageView.setImageResource(loadWeatherIcon(forecasts[position].weather[0].icon))
            weather.text = forecasts[position].weather[0].main
            maxTemperature.text = "${forecasts[position].temperature.max.toInt()}°"
            minTemperature.text = "${forecasts[position].temperature.min.toInt()}°"
            humidity.text = "Humidity: ${forecasts[position].humidity.toInt()} %"
            pressure.text = "Pressure: ${forecasts[position].pressure.toInt()}"
            wind.text = "Wind: ${forecasts[position].gust.toInt()}"
            itemView.setOnClickListener {
                onItemClick.onClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return forecasts.size
    }

    private fun getDayOfWeek(millis: Long): String {
        val sdf = SimpleDateFormat("EEEE")
        val dateFormat = Date(millis * 1000)
        return sdf.format(dateFormat)
    }

    private fun loadWeatherIcon(iconValue: String): Int { //TODO belongs in extension functions class
        var res: Int = -1
        res = when(iconValue) {
            "01d" -> R.drawable.ic_weather_sunny
            "01n" -> R.drawable.ic_weather_moon
            "02d" -> R.drawable.ic_weather_cloudy
            "02n" -> R.drawable.ic_weather_cloudy_night
            "03d" -> R.drawable.ic_weather_partly_sunny
            "03n" -> R.drawable.ic_weather_cloudy
            "04d" -> R.drawable.ic_weather_cloudy
            "04n" -> R.drawable.ic_weather_cloudy
            "09d" -> R.drawable.ic_weather_rainy
            "09n" -> R.drawable.ic_weather_rainy
            "10d" -> R.drawable.ic_weather_rainy_2
            "10n" -> R.drawable.ic_weather_rainy_2
            "11d" -> R.drawable.ic_weather_stormy
            "11n" -> R.drawable.ic_weather_stormy
            "13d" -> R.drawable.ic_weather_rainy_2
            "13n" -> R.drawable.ic_weather_rainy_2
            "50d" -> R.drawable.ic_weather_sunny
            "50n" -> R.drawable.ic_weather_sunny
            else -> R.drawable.ic_weather_cloudy
        }
        return res
    }

    interface OnItemClick {
        fun onClick(id: Int)
    }
}