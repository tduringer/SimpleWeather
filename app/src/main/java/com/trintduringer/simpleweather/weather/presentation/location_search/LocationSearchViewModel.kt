package com.trintduringer.simpleweather.weather.presentation.location_search

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LocationSearchViewModel: ViewModel() {

    private val _state = MutableStateFlow(LocationSearchState())
    val state = _state.asStateFlow()

    fun onAction(action: LocationSearchAction) {
        when(action) {
            is LocationSearchAction.OnSearchQueryChanged -> {
                _state.update {
                    it.copy(
                        searchQuery = action.newQuery
                    )
                }
            }
            is LocationSearchAction.OnWeatherInfoClick -> {
                _state.update {
                    it.copy(
                        savedWeatherInfo = action.weatherInfo
                    )
                }
            }
        }
    }
}