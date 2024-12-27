package com.trintduringer.simpleweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.trintduringer.simpleweather.core.domain.DataStoreManager
import com.trintduringer.simpleweather.ui.theme.SimpleWeatherTheme
import com.trintduringer.simpleweather.weather.presentation.location_search.LocationSearchScreenRoot
import com.trintduringer.simpleweather.weather.presentation.location_search.LocationSearchViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel = koinViewModel<LocationSearchViewModel>()
            val context = LocalContext.current
            val dataStoreManager = DataStoreManager(context)
            SimpleWeatherTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LocationSearchScreenRoot(
                        viewModel = viewModel,
                        dataStoreManager = dataStoreManager,
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}