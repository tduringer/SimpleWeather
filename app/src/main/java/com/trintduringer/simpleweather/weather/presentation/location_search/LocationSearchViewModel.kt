package com.trintduringer.simpleweather.weather.presentation.location_search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trintduringer.simpleweather.core.domain.DataStoreManager
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
    private val weatherInfoRepository: WeatherInfoRepository,
    private val dataStoreManager: DataStoreManager,
) : ViewModel() {

    private var searchJob: Job? = null
    private var savedLocation: String? = null

    private val _state = MutableStateFlow(LocationSearchState())
    val state = _state
        .onStart {
//            observeSavedLocation()
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
                Log.d("LocationSearchViewModel: onAction", "OnSearchQueryChanged start")
                viewModelScope.launch {
                    dataStoreManager.clearDataStore()
                }
                _state.update {
                    it.copy(
                        searchQuery = action.newQuery
                    )
                }
            }

            is LocationSearchAction.OnWeatherInfoClick -> {
                viewModelScope.launch {
                    savedLocation = action.searchQuerySubmitted
                    dataStoreManager.saveNewLocation(newLocation = action.searchQuerySubmitted)
                }
                _state.update {
                    it.copy(
                        savedWeatherInfo = action.weatherInfo,
                        searchResult = null,
                        savedLocation = action.searchQuerySubmitted
                    )
                }
            }

            is LocationSearchAction.OnSearchOnDemand -> searchOnDemand(query = action.newQuery)
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
                        searchOnDemand(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun searchOnDemand(query: String) {
        searchJob?.cancel()
        searchJob = searchWeatherInfoForLocation(query)
    }

    private suspend fun observeSavedLocation() {
        dataStoreManager.location.distinctUntilChanged().collect {
            savedLocation = it
        }
        _state.update { it.copy(
            searchQuery = savedLocation.orEmpty()
        ) }
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