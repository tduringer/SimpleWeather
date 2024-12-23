package com.trintduringer.simpleweather.weather.presentation.location_search.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trintduringer.simpleweather.R
import com.trintduringer.simpleweather.ui.theme.SimpleWeatherTheme

@Composable
fun NoCitySelectedBox(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,


        ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier,
        ) {
            Text(
                text = stringResource(R.string.no_city_selected_title),
                fontSize = 48.sp,
                modifier = modifier
            )
            Spacer(modifier = modifier.height(16.dp))
            Text(
                text = stringResource(R.string.no_city_selected_sub_title),
                fontSize = 22.sp,
                modifier = modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NoCitySelectedBoxPreview() {
    SimpleWeatherTheme {
        NoCitySelectedBox()
    }
}