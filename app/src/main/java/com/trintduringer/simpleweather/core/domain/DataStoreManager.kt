package com.trintduringer.simpleweather.core.domain

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val USER_SAVED_WEATHER_LOCATION = "saved_weather_location"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SAVED_WEATHER_LOCATION)

class DataStoreManager(private val context: Context) {

    companion object {
        val LOCATION = stringPreferencesKey("location")
    }

    val location: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[stringPreferencesKey("location")] ?: ""
        }

    suspend fun saveNewLocation(newLocation: String) {
        Log.d("DataStoreManager: saveNewLocation", "newLocation saved $newLocation")
        context.dataStore.edit { preferences ->
            preferences[LOCATION] = newLocation
        }
    }

    suspend fun clearDataStore() = context.dataStore.edit {
        it.clear()
    }

}