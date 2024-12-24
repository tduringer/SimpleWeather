package com.trintduringer.simpleweather.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.trintduringer.simpleweather.R
import com.trintduringer.simpleweather.core.domain.DataError

/**
 * Request Timed Out
 * Too Many Requests
 * No internet
 * Server error
 * Serialization error
 * Unknown error
 */

@Composable
fun DataError.toUiText(): String {
    return when(this) {
        DataError.Remote.REQUEST_TIMEOUT -> stringResource(R.string.data_error_request_timed_out)
        DataError.Remote.TOO_MANY_REQUESTS -> stringResource(R.string.data_error_too_many_requests)
        DataError.Remote.NO_INTERNET -> stringResource(R.string.data_error_no_internet)
        DataError.Remote.SERVER -> stringResource(R.string.data_error_server_error)
        DataError.Remote.SERIALIZATION -> stringResource(R.string.data_error_serialization_error)
        DataError.Remote.UNKNOWN -> stringResource(R.string.data_error_unknown_error)
    }
}