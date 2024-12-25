package com.trintduringer.simpleweather.weather.presentation.location_search.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.trintduringer.simpleweather.R
import com.trintduringer.simpleweather.ui.theme.SimpleWeatherTheme
import com.trintduringer.simpleweather.weather.domain.WeatherInfo
import com.trintduringer.simpleweather.weather.domain.sampleWeatherInfo1

private const val ICON_PREPEND = "https://"

@Composable
fun SearchResultItem(
    weatherInfo: WeatherInfo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(32.dp),
        modifier = modifier
            .clickable(onClick = onClick),
        color = Color.Gray.copy(alpha = 0.15f),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = weatherInfo.cityName,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = stringResource(
                        R.string.add_degree_symbol_template,
                        weatherInfo.temperatureC.toString()
                    ),
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 64.sp
                )
            }
            Box(
                modifier = Modifier
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                var imageLoadResult by remember {
                    mutableStateOf<Result<Painter>?>(null)
                }
                //https://cdn.weatherapi.com/weather/64x64/day/116.png
                val painter = rememberAsyncImagePainter(
                    model = ICON_PREPEND + weatherInfo.weatherCondition.icon,
                    onSuccess = {
                        Log.d("SearchResultItem: painter", "onSuccess start")
                        imageLoadResult = Result.success(it.painter)
                    },
                    onError = {
                        Log.d("SearchResultItem: painter", "onError start")
                        it.result.throwable.printStackTrace()
                        imageLoadResult = Result.failure(it.result.throwable)
                    }

                )

                when (val result = imageLoadResult) {
                    null -> CircularProgressIndicator()
                    else -> {
                        Image(
                            painter = if (result.isSuccess) painter else painterResource(R.drawable.ic_launcher_background),
                            contentDescription = weatherInfo.weatherCondition.text,
                            contentScale = if (result.isSuccess) {
                                ContentScale.Crop
                            } else {
                                ContentScale.Fit
                            },
                            modifier = Modifier
                                .aspectRatio(
                                    ratio = 1f,
                                    matchHeightConstraintsFirst = true
                                )
                        )
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeatherInfoItemPreview() {
    SimpleWeatherTheme {
        SearchResultItem(
            weatherInfo = sampleWeatherInfo1,
            onClick = { },
        )
    }
}