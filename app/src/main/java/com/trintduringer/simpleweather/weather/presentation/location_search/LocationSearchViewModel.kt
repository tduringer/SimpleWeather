package com.trintduringer.simpleweather.weather.presentation.location_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trintduringer.simpleweather.core.domain.onError
import com.trintduringer.simpleweather.core.domain.onSuccess
import com.trintduringer.simpleweather.core.presentation.toUiText
import com.trintduringer.simpleweather.weather.domain.WeatherInfoRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationSearchViewModel(
    private val weatherInfoRepository: WeatherInfoRepository
) : ViewModel() {

    private var searchJob: Job? = null

    private val _state = MutableStateFlow(LocationSearchState())
    val state = _state
        .onStart {
            observeSearchQuery()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: LocationSearchAction) {
        when (action) {
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
                        searchQuery = action.weatherInfo.cityName,
                        savedWeatherInfo = action.weatherInfo
                    )
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                errorMessage = null,
                            )
                        }
                    }

                    query.length >= 2 -> {
                        searchJob?.cancel()
                        searchJob = searchWeatherInfoForLocation(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchWeatherInfoForLocation(query: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        weatherInfoRepository
            .searchWeatherInfoForLocation(query)
            .onSuccess { searchResult ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        searchResult = searchResult
                    )
                }

            }
            .onError { error ->
                _state.update {
                    it.copy(
                        searchResult = null,
                        isLoading = false,
                        errorMessage = error.toUiText()
                    )
                }
            }
    }
}