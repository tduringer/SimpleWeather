package com.trintduringer.simpleweather.weather.presentation.location_search.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.trintduringer.simpleweather.ui.theme.SimpleWeatherTheme

@Composable
fun ErrorBox(
    errorMessage: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = errorMessage,
            color = Color.Red,
            fontSize = 32.sp,
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorBoxPreview() {
    SimpleWeatherTheme {
        ErrorBox(
            "Internet Not Available"
        )
    }
}