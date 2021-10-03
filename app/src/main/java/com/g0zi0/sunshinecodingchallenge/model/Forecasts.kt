package com.g0zi0.sunshinecodingchallenge.model

import com.google.gson.annotations.SerializedName

data class Forecasts(
    @SerializedName("city")
    var city: CityInfo,
    var cod: Int,
    var message: String,
    var cnt: Int,
    @SerializedName("list")
    var dailyForecast: List<DailyForecast>
)

data class CityInfo(
    var id: Int,
    var name: String,
    var coordinates: Coordinates,
    var country: String,
    var population: Int,
    var timezone: Int
)

data class Coordinates(
    var lon: Double,
    var lat: Double
)

data class DailyForecast(
    var dt: Int,
    var sunrise: Int,
    var sunset: Int,
    @SerializedName("temp")
    var temperature: Temperature,
    @SerializedName("feels_like")
    var feelsLike: FeelsLikeTemp,
    var pressure: Double,
    var humidity: Double,
    @SerializedName("weather")
    var weather: List<Weather>,
    var speed: Double,
    var degree: Double,
    var gust: Double,
    var clouds: Int,
    var pop: Double
)

data class Temperature(
    var day: Double,
    var min: Double,
    var max: Double,
    var night: Double,
    var evening: Double,
    var morning: Double,
)

data class FeelsLikeTemp(
    var day: Double,
    var night: Double,
    var evening: Double,
    var morning: Double
)

data class Weather(
    var id: Int,
    var main: String,
    var description: String,
    var icon: String
)

data class CurrentWeather(
    var coordinates: Coordinates,
    var weather: List<Weather>,
    var base: String,
    var main: Main,
    var visibility: Int,
    var wind: Wind,
    var clouds: Clouds,
    var dt: Int,
    var sys: Sys,
    var timezone: Int,
    var id: Int,
    var name: String,
    var cod: Int
)

data class Clouds(var all: Int)

data class Main(
    @SerializedName("temp")
    var temperature: Double,
    @SerializedName("feels_like")
    var feelsLike: Double,
    var tempMin: Double,
    var tempMax: Double,
    var pressure: Double,
    var humidity: Double,
)

data class Wind(
    var speed: Double,
    var deg: Double,
    var gust: Double
)

data class Sys(
    var type: Int,
    var id: Int,
    var country: String,
    var sunrise: Int,
    var sunset: Int
)