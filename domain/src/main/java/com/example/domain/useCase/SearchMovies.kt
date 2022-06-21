package com.example.domain.useCase

import com.example.domain.BaseUseCase
import com.example.domain.abstraction.PostExecutionThread
import com.example.domain.abstraction.Repository
import com.example.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMovies @Inject constructor(
    postExecutionThread: PostExecutionThread,
    private val repository: Repository
) : BaseUseCase<String, List<Movie>>(postExecutionThread.io) {

    /**
     * Gets Access Token in Reddit
     */
    override fun execute(param: String?): Flow<List<Movie>> {
        return repository.searchMovies(param!!)
    }

}