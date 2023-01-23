package com.example.data.remote.source

import com.example.data.RemoteDataSource
import com.example.data.remote.OmdbService
import com.example.data.remote.model.Search
import com.example.data.remote.model.error.SearchResponseError
import com.example.data.util.doIfFailed
import com.example.data.util.doIfSuccess
import com.example.data.util.safeApiCall
import com.example.domain.NetworkResult

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import javax.inject.Inject


/**
 *  Implementation of remote data source
 */
class RemoteDataSourceImpl @Inject constructor(
    private val service: OmdbService
) : RemoteDataSource {


    override suspend fun searchMovies(searchTitle: String, apiKey: String): List<Search> {
        return service.searchMovie(searchTitle = searchTitle,apiKey = apiKey).search!!

    }

    override suspend fun searchMoviesNew(searchTitle: String, apiKey: String): NetworkResult {
        val response = safeApiCall {
            service.searchMovieNew(searchTitle = searchTitle,apiKey = apiKey)
        }
        response?.doIfSuccess { data ->
            if(data.errorMessage != ""){
                return NetworkResult.Failed(errorCode = 500, errorMesg = data.errorMessage.toString())
            }
            return NetworkResult.Success(data = data.search!!)
        }
        response.doIfFailed { errorCode, errorMsg ->
            var status = errorCode
            var title = errorMsg
            if (response != null) {
                val error = GsonBuilder().create().fromJson(
                    response.errorBody()?.string(),
                    SearchResponseError::class.java
                )
                status = 500
                title = error.Error
            }
            return NetworkResult.Failed(errorCode = status, errorMesg = title)
        }
        return NetworkResult.Unknown
    }


}