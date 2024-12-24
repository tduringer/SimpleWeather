package com.trintduringer.simpleweather.weather.data.network

import com.trintduringer.simpleweather.core.data.safeCall
import com.trintduringer.simpleweather.core.domain.DataError
import com.trintduringer.simpleweather.core.domain.Result
import com.trintduringer.simpleweather.weather.data.dto.SearchedWeatherInfoDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://api.weatherapi.com/v1"
private const val URL_FILE = "current.json"
private const val API_KEY = "03e2d4ed182a40eaaf8192525241912"

/**
 * http://api.weatherapi.com/v1/current.json?key=03e2d4ed182a40eaaf8192525241912&q=92596&aqi=no
 * Base URL: http://api.weatherapi.com/v1
 *
 * MyApiKey: 03e2d4ed182a40eaaf8192525241912
 * q=92596
 *
 * How best to hide the API key?
 */

class KtorRemoteWeatherInfoDataSource(
    private val httpClient: HttpClient
) : RemoteWeatherInfoDataSource {
    override suspend fun searchWeatherInfoForLocation(
        query: String,
    ): Result<SearchedWeatherInfoDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "$BASE_URL/$URL_FILE"
            ) {
                parameter("key", API_KEY)
                parameter("q", query)
                parameter(
                    "fields",
                    "location, current, name, temp_c, condition, humidity, uv, feelslike_c, text, icon"
                )
            }
        }
    }

}