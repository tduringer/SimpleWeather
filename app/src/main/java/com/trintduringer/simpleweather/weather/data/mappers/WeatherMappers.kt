package com.trintduringer.simpleweather.weather.data.mappers

import com.trintduringer.simpleweather.weather.data.dto.SearchedWeatherCondition
import com.trintduringer.simpleweather.weather.data.dto.SearchedWeatherInfoDto
import com.trintduringer.simpleweather.weather.domain.WeatherCondition
import com.trintduringer.simpleweather.weather.domain.WeatherInfo

fun SearchedWeatherInfoDto.toWeatherInfo(): WeatherInfo {
    return WeatherInfo(
        cityName = this.location.name,
        temperatureC = this.current.tempC,
        weatherCondition = this.current.weatherCondition.toWeatherCondition(),
        humidity = this.current.humidity,
        uvIndex = this.current.uv,
        feelsLikeC = this.current.feelsLikeC
    )
}

fun SearchedWeatherCondition.toWeatherCondition() : WeatherCondition {
    return WeatherCondition(
        text = this.text,
        icon = this.icon
    )
}