package com.trintduringer.simpleweather

import android.app.Application
import com.trintduringer.simpleweather.di.initKoin
import org.koin.android.ext.koin.androidContext

class SimpleWeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@SimpleWeatherApplication)
        }
    }
}