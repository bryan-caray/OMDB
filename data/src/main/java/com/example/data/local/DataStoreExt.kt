package com.example.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * DataStore extensions for convenient setting and getting of values
 */
val Context.preferences: DataStore<Preferences> by preferencesDataStore(name = "preferences")

suspend fun DataStore<Preferences>.setString(key: String, value: String) {
    edit { preferences ->
        val prefKey = stringPreferencesKey(key)
        preferences[prefKey] = value
    }
}

fun DataStore<Preferences>.getString(key: String, default: String = "") : Flow<String> {
    val prefKey = stringPreferencesKey(key)
    return data
        .map { preferences ->
            preferences[prefKey] ?: default
        }
}

suspend fun DataStore<Preferences>.setInt(key: String, value: Int) {
    edit { preferences ->
        val prefKey = intPreferencesKey(key)
        preferences[prefKey] = value
    }
}

fun DataStore<Preferences>.getInt(key: String, default: Int = 0) : Flow<Int> {
    val prefKey = intPreferencesKey(key)
    return data
        .map { preferences ->
            preferences[prefKey] ?: default
        }
}

suspend fun DataStore<Preferences>.setBoolean(key: String, value: Boolean) {
    edit { preferences ->
        val prefKey = booleanPreferencesKey(key)
        preferences[prefKey] = value
    }
}

fun DataStore<Preferences>.getBoolean(key: String, default: Boolean = false) : Flow<Boolean> {
    val prefKey = booleanPreferencesKey(key)
    return data
        .map { preferences ->
            preferences[prefKey] ?: default
        }
}