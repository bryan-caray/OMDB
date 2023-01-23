package com.example.data

import com.example.data.remote.model.Search
import com.example.domain.Constant
import com.example.domain.NetworkResult
import com.example.domain.Result
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



    /**
     *  Implement Network Data Results
     */
    override fun searchMoviesNew(searchTitle: String): Flow<Result> = flow {
        emit(Result.Loading)
        when (val result = remoteDataSource.searchMoviesNew(searchTitle, Constant.API_KEY)) {
            is NetworkResult.Failed -> {
                emit(Result.Failed(errorCode = result.errorCode, errorMesg = result.errorMesg))
            }
            NetworkResult.Unknown -> {
                emit(
                    Result.Failed(
                        errorCode = 0,
                        errorMesg = "Unknown Error"
                    )
                )
            }
            is NetworkResult.Success<*> -> {
                val movieList = arrayListOf<Movie>()
                var list = result.data as ArrayList<Search>
                for (item in list) {
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
                emit(Result.Success(data = movieList))
            }
        }

    }
}