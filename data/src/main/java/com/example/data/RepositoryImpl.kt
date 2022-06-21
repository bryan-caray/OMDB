package com.example.data

import com.appvno.domain.Constant
import com.example.domain.abstraction.Repository
import com.example.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 *  Implementation of repository bridges all the data sources
 */
class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : Repository {

    override fun searchMovies(searchTitle: String): Flow<List<Movie>> = flow {
        val movieList = arrayListOf<Movie>()
        for (item in remoteDataSource.searchMovies(searchTitle, Constant.API_KEY)) {
            with(item) {
                movieList.add(
                    Movie(
                        imdbID = imdbID,
                        poster = poster,
                        title = title,
                        type = type,
                        year = year
                    )
                )
            }
        }

        emit(movieList)
    }
}