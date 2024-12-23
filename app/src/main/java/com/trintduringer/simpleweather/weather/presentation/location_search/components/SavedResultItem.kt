package com.trintduringer.simpleweather.weather.presentation.location_search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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

@Composable
fun SavedResultItem(
    weatherInfo: WeatherInfo,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .height(250.dp),
            contentAlignment = Alignment.Center
        ) {
            var imageLoadResult by remember {
                mutableStateOf<Result<Painter>?>(null)
            }
            val painter = rememberAsyncImagePainter(
                model = weatherInfo.weatherCondition.icon,
                onSuccess = {
                    imageLoadResult = Result.success(it.painter)
                },
                onError = {
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
                                ratio = 0.75f,
                                matchHeightConstraintsFirst = true
                            )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.add_arrow_symbol_template, weatherInfo.cityName),
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 36.sp
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.add_degree_symbol_template, weatherInfo.temperatureC),
            style = MaterialTheme.typography.titleLarge,
            fontSize = 64.sp
        )

        Spacer(modifier = Modifier.height(8.dp))
        AdvancedDetailsSection(weatherInfo = weatherInfo)
    }
}

@Composable
fun AdvancedDetailsSection(
    weatherInfo: WeatherInfo,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(32.dp),
        modifier = modifier,
        color = Color.Gray.copy(alpha = 0.15f),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = stringResource(R.string.humidity_label))
                Text(
                    text = weatherInfo.humidity.toString() + "%"
                )
            }
            Column {
                Text(text = stringResource(R.string.uv_label))
                Text(text = weatherInfo.uvIndex.toInt().toString())
            }
            Column {
                Text(text = stringResource(R.string.feels_like_label))
                Text(
                    text = stringResource(
                        R.string.add_degree_symbol_template,
                        weatherInfo.feelsLikeC
                    )
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun AdvancedDetailsSectionPreview() {
    SimpleWeatherTheme {
        AdvancedDetailsSection(
            sampleWeatherInfo1
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SavedResultItemPreview() {
    SimpleWeatherTheme {
        SavedResultItem(weatherInfo = sampleWeatherInfo1)
    }
}