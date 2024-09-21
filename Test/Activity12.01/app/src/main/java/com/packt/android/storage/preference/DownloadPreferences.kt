package com.packt.android.storage.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "download_preferences")

private val KEY_NO_RESULTS = intPreferencesKey("key_no_results")

class DownloadPreferences(private val context: Context) {

    val resultNumberFlow: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[KEY_NO_RESULTS] ?: 10
        }

    suspend fun saveResultNumber(resultNo: Int) {
        context.dataStore.edit { preferences ->
            preferences[KEY_NO_RESULTS] = resultNo
        }
    }
}