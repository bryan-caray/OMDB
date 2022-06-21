package com.example.omdb.feature.movielist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.abstraction.PostExecutionThread
import com.example.domain.useCase.SearchMovies
import com.example.omdb.feature.model.MovieDisplay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val postExecutionThread: PostExecutionThread,
    private val searchMovies: SearchMovies
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }
    private val _pagingDataList = MutableStateFlow(arrayListOf<MovieDisplay>())
    val movies: StateFlow<List<MovieDisplay>> get() = _pagingDataList

    fun search(input: String) {
        if (input.isNotEmpty()) {
            viewModelScope.launch(postExecutionThread.io + exceptionHandler) {
                searchMovies(input).collect {
                    val movies = arrayListOf<MovieDisplay>()
                    it.forEachIndexed { index, movie ->
                        with(movie) {
                            movies.add(
                                MovieDisplay(
                                    id = index,
                                    name = title,
                                    thumbnail = poster,
                                    genre = type,
                                    year = year
                                )
                            )
                        }
                    }
                    _pagingDataList.value = movies

                }
            }
        }
    }
}