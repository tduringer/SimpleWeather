package com.trintduringer.simpleweather.weather.domain

data class WeatherInfo(
    val cityName: String,
    val temperatureC: Double,
    val weatherCondition: WeatherCondition,
    val humidity: Double,
    val uvIndex: Double,
    val feelsLikeC: Double,
)

data class WeatherCondition(
    val text: String,
    val icon: String,
)

val sampleWeatherInfo1 = WeatherInfo(
    cityName = "Hyderabad",
    temperatureC = 31.00,
    weatherCondition = WeatherCondition(
        text = "Sunny",
        icon = "google.com/images/sun.png"
    ),
    humidity = 20.00,
    uvIndex = 4.00,
    feelsLikeC = 38.00
)

val sampleWeatherInfo2 = WeatherInfo(
    cityName = "Mumbai",
    temperatureC = 20.00,
    weatherCondition = WeatherCondition(
        text = "Cloudy",
        icon = "google.com/images/clouds.png"
    ),
    humidity = 20.00,
    uvIndex = 4.00,
    feelsLikeC = 38.00
)