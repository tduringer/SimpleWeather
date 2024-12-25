package com.trintduringer.simpleweather.weather.presentation.location_search

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.trintduringer.simpleweather.core.domain.DataStoreManager
import com.trintduringer.simpleweather.ui.theme.SimpleWeatherTheme
import com.trintduringer.simpleweather.weather.presentation.location_search.components.ErrorBox
import com.trintduringer.simpleweather.weather.presentation.location_search.components.LoadingIndicatorBox
import com.trintduringer.simpleweather.weather.presentation.location_search.components.LocationSearchBar
import com.trintduringer.simpleweather.weather.presentation.location_search.components.NoCitySelectedBox
import com.trintduringer.simpleweather.weather.presentation.location_search.components.SavedResultItem
import com.trintduringer.simpleweather.weather.presentation.location_search.components.SearchResultItem
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun LocationSearchScreenRoot(
    viewModel: LocationSearchViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val dataStoreManager = DataStoreManager(context)
    val location by dataStoreManager.location.collectAsState("")
    val state by viewModel.state.collectAsStateWithLifecycle()
//    val updatedState = state.copy(
//        searchQuery = location
//    )

    LocationSearchScreen(
        state = state,
        onAction = { action ->
            viewModel.onAction(action)
        },
        savedLocation = location,
        modifier = modifier,
    )
}

@Composable
private fun LocationSearchScreen(
    state: LocationSearchState,
    onAction: (LocationSearchAction) -> Unit,
    savedLocation: String = "",
    modifier: Modifier = Modifier,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val dataStoreManager = DataStoreManager(context)
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LocationSearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChanged = { onAction(LocationSearchAction.OnSearchQueryChanged(it)) },
            onImeSearch = {
                keyboardController?.hide()
            },
            modifier = modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )
        /**
         * First check for error. If an error exists then everything else
         * is overridden unless the state.isLoading is true.
         * Then check for isLoading, then check for if we have a search result,
         * then check for if we have a savedWeatherInfo, else we show
         * no city selected box.
         */

        if (state.errorMessage != null && !state.isLoading) {
            ErrorBox(errorMessage = state.errorMessage.asString())
        } else if (state.isLoading) {
            LoadingIndicatorBox()
        } else if (state.searchResult != null) {
            SearchResultItem(
                weatherInfo = state.searchResult,
                onClick = {
                    Log.d("LocationSearchScreen: SearchResultItem", "onClick start")
                    onAction(LocationSearchAction.OnWeatherInfoClick(state.searchResult))
                    scope.launch {
                        dataStoreManager.saveNewLocation(newLocation = savedLocation)
                    }
                },
                modifier = Modifier
                    .padding(16.dp)
            )
        } else if (state.savedWeatherInfo != null) {
            SavedResultItem(
                weatherInfo = state.savedWeatherInfo,
                modifier = modifier
                    .padding(16.dp)
            )
        } else if (state.searchQuery.isEmpty()) {
            NoCitySelectedBox()
        }
    }

}

@Preview
@Composable
private fun LocationSearchScreenPreview() {
    SimpleWeatherTheme {
        LocationSearchScreen(
            state = locationSearchStateWithSearchResult,
            onAction = {},
        )
    }
}