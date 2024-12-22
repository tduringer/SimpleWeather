package com.trintduringer.simpleweather.weather.presentation.location_search

import com.trintduringer.simpleweather.weather.domain.WeatherInfo

data class LocationSearchState(
    val searchQuery: String = "92596",
    val searchResult: WeatherInfo? = null,
    val savedWeatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)