package com.trintduringer.simpleweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.trintduringer.simpleweather.core.data.HttpClientFactory
import com.trintduringer.simpleweather.ui.theme.SimpleWeatherTheme
import com.trintduringer.simpleweather.weather.data.network.KtorRemoteWeatherInfoDataSource
import com.trintduringer.simpleweather.weather.data.repository.DefaultWeatherInfoRepository
import com.trintduringer.simpleweather.weather.presentation.location_search.LocationSearchScreenRoot
import com.trintduringer.simpleweather.weather.presentation.location_search.LocationSearchViewModel
import io.ktor.client.engine.okhttp.OkHttp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            SimpleWeatherTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LocationSearchScreenRoot(
                        viewModel = remember {
                            LocationSearchViewModel(
                                weatherInfoRepository = DefaultWeatherInfoRepository(
                                    remoteWeatherInfoDataSource = KtorRemoteWeatherInfoDataSource(
                                        httpClient = HttpClientFactory.create(
                                            engine = OkHttp.create()
                                        )
                                    )
                                )
                            )
                        },
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SimpleWeatherTheme {

    }
}