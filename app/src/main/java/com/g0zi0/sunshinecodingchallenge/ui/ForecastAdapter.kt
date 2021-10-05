package com.g0zi0.sunshinecodingchallenge.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.g0zi0.sunshinecodingchallenge.R
import com.g0zi0.sunshinecodingchallenge.model.DailyForecast
import java.text.SimpleDateFormat
import java.util.*

class ForecastAdapter(var forecasts: List<DailyForecast>): RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    lateinit var context: Context

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var weatherImageView: ImageView = this.itemView.findViewById(R.id.weatherImageView)
        var day: TextView = this.itemView.findViewById(R.id.dayTextView)
        var weather: TextView = this.itemView.findViewById(R.id.weatherTextView)
        var maxTemperature: TextView = this.itemView.findViewById(R.id.maxDegreesTextView)
        var minTemperature: TextView = this.itemView.findViewById(R.id.minDegreesTextView)
        var humidity: TextView = this.itemView.findViewById(R.id.humidityTextView)
        var pressure: TextView = this.itemView.findViewById(R.id.pressureTextView)
        var wind: TextView = this.itemView.findViewById(R.id.windTextView)
        var hiddenLayout: ConstraintLayout = this.itemView.findViewById(R.id.hiddenLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
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
            day.text = dayOfWeek(forecasts[position].dt.toLong())
            weatherImageView.setImageResource(loadWeatherIcon(forecasts[position].weather[0].icon))
            weather.text = forecasts[position].weather[0].main
            maxTemperature.text = context.getString(R.string.degree, forecasts[position].temperature.max.toInt().toString())
            minTemperature.text = context.getString(R.string.degree, forecasts[position].temperature.min.toInt().toString())
            humidity.text = context.getString(R.string.humidity, forecasts[position].humidity.toInt().toString())
            pressure.text = context.getString(R.string.pressure, forecasts[position].pressure.toInt().toString())
            wind.text = context.getString(R.string.wind, forecasts[position].speed.toInt().toString(), getDirectionFromDegrees(forecasts[position].degree))
            itemView.setOnClickListener {
                if (hiddenLayout.isVisible) {
                    collapse(hiddenLayout)
                } else {
                    expandAction(hiddenLayout)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return forecasts.size
    }

    private fun dayOfWeek(millis: Long): String {
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

    fun expandAction(view: View): Animation {
        view.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val actualheight = view.measuredHeight
        view.layoutParams.height = 0
        view.visibility = View.VISIBLE
        val animation: Animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                view.layoutParams.height =
                    if (interpolatedTime == 1f) ViewGroup.LayoutParams.WRAP_CONTENT else (actualheight * interpolatedTime).toInt()
                view.requestLayout()
            }
        }
        animation.duration = (actualheight / view.context.resources.displayMetrics.density).toLong()
        view.startAnimation(animation)
        return animation
    }

    fun collapse(view: View) {
        val actualHeight = view.measuredHeight
        val animation: Animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                if (interpolatedTime == 1f) {
                    view.visibility = View.GONE
                } else {
                    view.layoutParams.height =
                        actualHeight - (actualHeight * interpolatedTime).toInt()
                    view.requestLayout()
                }
            }
        }
        animation.duration = (actualHeight / view.context.resources.displayMetrics.density).toLong()
        view.startAnimation(animation)
    }

    private fun getDirectionFromDegrees(degrees: Double): String {
        var direction: String = ""
        when {
            degrees < 10 || degrees > 350 -> { direction = "N" }
            degrees > 80 && degrees < 100 -> { direction = "E" }
            degrees > 170 && degrees < 190 -> { direction = "S" }
            degrees > 260 && degrees < 280 -> { direction = "W" }
            degrees > 10 && degrees < 80 -> { direction = "NE" }
            degrees > 100 && degrees < 170 -> { direction = "SE" }
            degrees > 190 && degrees < 260 -> { direction = "SW" }
            degrees > 280 && degrees < 350 -> { direction = "NW" }
        }
        return direction
    }
}