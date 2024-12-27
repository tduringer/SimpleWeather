package com.trintduringer.simpleweather.weather.presentation.location_search

import com.trintduringer.simpleweather.weather.domain.WeatherInfo

sealed interface LocationSearchAction {
    data class OnSearchOnDemand(val newQuery: String): LocationSearchAction
    data class OnSearchQueryChanged(val newQuery: String) : LocationSearchAction
    data class OnWeatherInfoClick(val weatherInfo: WeatherInfo, val searchQuerySubmitted: String) :
        LocationSearchAction
}