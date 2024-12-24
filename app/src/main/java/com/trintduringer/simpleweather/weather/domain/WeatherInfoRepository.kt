package com.trintduringer.simpleweather.weather.domain

import com.trintduringer.simpleweather.core.domain.DataError
import com.trintduringer.simpleweather.core.domain.Result

interface WeatherInfoRepository {
    suspend fun searchWeatherInfoForLocation(query: String): Result<WeatherInfo,DataError.Remote>
}