package com.example.data.remote

import com.example.data.remote.model.SearchResponse
import com.example.data.remote.model.TokenResponse
import retrofit2.Response
import retrofit2.http.*


/**
 *  Service used for API call
 */
interface OmdbService {

    @GET("?type=movie")
    suspend fun searchMovie(
        @Query(value = "s") searchTitle: String,
        @Query(value = "apiKey") apiKey: String,
        @Query(value = "page") page: Int? = 1
    ): SearchResponse

    @GET("?type=movie")
    suspend fun searchMovieNew(
        @Query(value = "s") searchTitle: String,
        @Query(value = "apiKey") apiKey: String,
        @Query(value = "page") page: Int? = 1
    ): Response<SearchResponse>

}