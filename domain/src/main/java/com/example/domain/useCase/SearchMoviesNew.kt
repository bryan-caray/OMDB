package com.example.domain.useCase

import com.example.domain.BaseUseCase
import com.example.domain.Result
import com.example.domain.abstraction.PostExecutionThread
import com.example.domain.abstraction.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMoviesNew @Inject constructor(
    postExecutionThread: PostExecutionThread,
    private val repository: Repository
 ) : BaseUseCase<String, Result>(postExecutionThread.io) {

    override fun execute(param: String?): Flow<Result> {
        return repository.searchMoviesNew(param!!)
    }
}