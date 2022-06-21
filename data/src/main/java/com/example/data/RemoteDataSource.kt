package com.example.data

import com.example.data.remote.model.Search
import kotlinx.coroutines.flow.Flow

/**
 *  Abstraction for Remote Data Source, this is the contract that remote data layer needs to implement
 */
interface RemoteDataSource {
    suspend fun searchMovies(searchTitle: String, apiKey: String): List<Search>
}