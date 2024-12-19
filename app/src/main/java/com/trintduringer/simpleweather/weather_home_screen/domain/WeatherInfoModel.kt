package com.trintduringer.simpleweather.weather_home_screen.domain

data class WeatherInfoModel(
    val temperatureF: Double,
    val temperatureC: Double,
    val weatherCondition: WeatherCondition,
    val humidity: Double,
    val uvIndex: Double,
    val feelsLikeF: Double,
    val feelsLikeC: Double,
)

data class WeatherCondition(
    val text: String,
    val icon: String,
)
