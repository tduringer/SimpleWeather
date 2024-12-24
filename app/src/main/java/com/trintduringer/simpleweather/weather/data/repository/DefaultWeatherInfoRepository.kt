package com.trintduringer.simpleweather.weather.data.repository

import com.trintduringer.simpleweather.core.domain.DataError
import com.trintduringer.simpleweather.core.domain.Result
import com.trintduringer.simpleweather.core.domain.map
import com.trintduringer.simpleweather.weather.data.mappers.toWeatherInfo
import com.trintduringer.simpleweather.weather.data.network.RemoteWeatherInfoDataSource
import com.trintduringer.simpleweather.weather.domain.WeatherInfo
import com.trintduringer.simpleweather.weather.domain.WeatherInfoRepository

class DefaultWeatherInfoRepository(
    private val remoteWeatherInfoDataSource: RemoteWeatherInfoDataSource
): WeatherInfoRepository {
    override suspend fun searchWeatherInfoForLocation(query: String): Result<WeatherInfo, DataError.Remote> {
        return remoteWeatherInfoDataSource
            .searchWeatherInfoForLocation(query)
            .map { dto ->
                dto.toWeatherInfo()
            }
    }

}