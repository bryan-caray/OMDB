package com.example.data.remote.source

import com.example.data.RemoteDataSource
import com.example.data.remote.OmdbService
import com.example.data.remote.model.Search
import javax.inject.Inject


/**
 *  Implementation of remote data source
 */
class RemoteDataSourceImpl @Inject constructor(
    private val service: OmdbService
) : RemoteDataSource {


    override suspend fun searchMovies(searchTitle: String, apiKey: String): List<Search> {
        return service.searchMovie(searchTitle = searchTitle,apiKey = apiKey).search

    }


}