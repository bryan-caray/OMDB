package com.example.data

import kotlinx.coroutines.flow.Flow

/**
 *  Abstraction for Local Data Source, this is the contract that local data layer needs to implement
 */
interface LocalDataSource {
    suspend fun saveAccessToken(token: String)
    fun getAccessToken(): Flow<String>
}