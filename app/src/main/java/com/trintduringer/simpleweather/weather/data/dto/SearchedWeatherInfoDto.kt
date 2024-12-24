package com.trintduringer.simpleweather.weather.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchedWeatherInfoDto(
    @SerialName("location") val location: SearchedWeatherLocation,
    @SerialName("current") val current: SearchedWeatherCurrent,
)

@Serializable
data class SearchedWeatherLocation(
    @SerialName("name") val name: String
)

@Serializable
data class SearchedWeatherCurrent(
    @SerialName("temp_c") val tempC: Double,
    @SerialName("condition") val weatherCondition: SearchedWeatherCondition,
    @SerialName("humidity") val humidity: Double,
    @SerialName("uv") val uv: Double,
    @SerialName("feelslike_c") val feelsLikeC: Double,
)

@Serializable
data class SearchedWeatherCondition(
    @SerialName("text") val text: String,
    @SerialName("icon") val icon: String,
)

//"location, current, name, temp_c, condition, humidity, uv, feelslike_c, text, icon"