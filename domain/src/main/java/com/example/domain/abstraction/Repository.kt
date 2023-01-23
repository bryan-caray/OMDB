package com.example.domain.abstraction

import com.example.domain.Result
import com.example.domain.model.Movie
import kotlinx.coroutines.flow.Flow


/**
 *  Abstraction for Repository, this is the contract that data layer needs to implement
 */
interface Repository {
    fun searchMovies(searchTitle: String): Flow<List<Movie>>
    fun searchMoviesNew(searchTitle: String): Flow<Result>
}