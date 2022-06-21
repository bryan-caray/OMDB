package com.example.data.local.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.data.LocalDataSource
import com.example.data.local.getString
import com.example.data.local.setString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Implementation of local data source
 */
class LocalDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : LocalDataSource {

    companion object {
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
    }

    override suspend fun saveAccessToken(token: String) {
        dataStore.setString(ACCESS_TOKEN, token)
    }

    override fun getAccessToken(): Flow<String>  {
        return dataStore.getString(ACCESS_TOKEN,"")
    }

}