package com.trintduringer.simpleweather.weather.presentation.location_search.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.trintduringer.simpleweather.R
import com.trintduringer.simpleweather.ui.theme.SimpleWeatherTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationSearchBar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onSearchOnDemand: (String) -> Unit,
    onImeSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    //Need to create a UI that matches the figma
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChanged,
        shape = RoundedCornerShape(100),
        placeholder = { Text(stringResource(R.string.search_bar_placeholder)) },
        trailingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        Log.d("LocationSearchBar: Icon", "Search icon clicked")
                        // Can't get this working properly
                        //The click is detected but the onSearchOnDemand lambda is not getting called.
                        onSearchOnDemand
                    }

            )
        },
        singleLine = true,
        keyboardActions = KeyboardActions(
            onSearch = {
                onImeSearch()
            }
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.Gray.copy(alpha = 0.15f),
            unfocusedContainerColor = Color.Gray.copy(alpha = 0.15f)
        ),
        modifier = modifier
    )

}

@Preview(showBackground = true)
@Composable
private fun LocationSearchBarPreview() {
    SimpleWeatherTheme {
        LocationSearchBar(
            searchQuery = "",
            onSearchQueryChanged = {},
            onImeSearch = { },
            onSearchOnDemand = {}
        )
    }
}