package com.trintduringer.simpleweather.weather.presentation.location_search

import com.trintduringer.simpleweather.R
import com.trintduringer.simpleweather.core.presentation.UiText
import com.trintduringer.simpleweather.weather.domain.WeatherInfo
import com.trintduringer.simpleweather.weather.domain.sampleWeatherInfo1
import com.trintduringer.simpleweather.weather.domain.sampleWeatherInfo2

data class LocationSearchState(
    val searchQuery: String = "",
    val searchResult: WeatherInfo? = null,
    val savedWeatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
)

/**
 * First check for error. If an error exists then everything else
 * is overridden unless the state.isLoading is true.
 * Then check for isLoading, then check for if we have a search result,
 * then check for if we have a savedWeatherInfo, else we show
 * no city selected box.
 */

val locationSearchStateError = LocationSearchState(
    searchQuery = "Mumbai",
    searchResult = sampleWeatherInfo2,
    savedWeatherInfo = sampleWeatherInfo1,
    isLoading = false,
    errorMessage = UiText.StringResourceInt(int = R.string.data_error_no_internet)
)

val locationSearchStateLoading = LocationSearchState(
    searchQuery = "Mumbai",
    searchResult = sampleWeatherInfo2,
    savedWeatherInfo = null,
    isLoading = true,
    errorMessage = null
)

val locationSearchStateWithSearchResult = LocationSearchState(
    searchQuery = "Mumbai",
    searchResult = sampleWeatherInfo2,
    savedWeatherInfo = null,
    isLoading = false,
    errorMessage = null
)

val locationSearchStateWithSavedWeatherInfo = LocationSearchState(
    searchQuery = "Mumbai",
    searchResult = null,
    savedWeatherInfo = sampleWeatherInfo1,
    isLoading = false,
    errorMessage = null
)

val locationSearchStateNoCitySelected = LocationSearchState(
    searchQuery = "",
    searchResult = null,
    savedWeatherInfo = null,
    isLoading = false,
    errorMessage = null
)