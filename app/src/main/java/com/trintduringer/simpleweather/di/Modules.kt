package com.trintduringer.simpleweather.di

import com.trintduringer.simpleweather.core.data.HttpClientFactory
import com.trintduringer.simpleweather.weather.data.network.KtorRemoteWeatherInfoDataSource
import com.trintduringer.simpleweather.weather.data.network.RemoteWeatherInfoDataSource
import com.trintduringer.simpleweather.weather.data.repository.DefaultWeatherInfoRepository
import com.trintduringer.simpleweather.weather.domain.WeatherInfoRepository
import com.trintduringer.simpleweather.weather.presentation.location_search.LocationSearchViewModel
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    single<HttpClientEngine> { OkHttp.create() }
    single { HttpClientFactory.create(get()) }

    singleOf(::KtorRemoteWeatherInfoDataSource).bind<RemoteWeatherInfoDataSource>()
    singleOf(::DefaultWeatherInfoRepository).bind<WeatherInfoRepository>()

    viewModelOf(::LocationSearchViewModel)
}