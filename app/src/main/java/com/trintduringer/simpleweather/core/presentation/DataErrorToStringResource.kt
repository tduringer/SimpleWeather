package com.trintduringer.simpleweather.core.presentation

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

fun DataError.toUiText(): UiText {
    val strResId = when (this) {
        DataError.Remote.REQUEST_TIMEOUT -> R.string.data_error_request_timed_out
        DataError.Remote.TOO_MANY_REQUESTS -> R.string.data_error_too_many_requests
        DataError.Remote.NO_INTERNET -> R.string.data_error_no_internet
        DataError.Remote.SERVER -> R.string.data_error_server_error
        DataError.Remote.SERIALIZATION -> R.string.data_error_serialization_error
        DataError.Remote.UNKNOWN -> R.string.data_error_unknown_error
    }
    return UiText.StringResourceInt(int = strResId)
}