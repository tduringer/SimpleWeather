package com.trintduringer.simpleweather.weather.data.network

import com.trintduringer.simpleweather.core.domain.DataError
import com.trintduringer.simpleweather.core.domain.Result
import com.trintduringer.simpleweather.weather.data.dto.SearchedWeatherInfoDto

interface RemoteWeatherInfoDataSource {
    suspend fun searchWeatherInfoForLocation(
        query: String,
    ) : Result<SearchedWeatherInfoDto, DataError.Remote>
}