package com.example.omdb.feature.movielist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.Result
import com.example.domain.abstraction.PostExecutionThread
import com.example.domain.model.Movie
import com.example.domain.useCase.SearchMovies
import com.example.domain.useCase.SearchMoviesNew
import com.example.omdb.feature.model.MovieDisplay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val postExecutionThread: PostExecutionThread,
    private val searchMovies: SearchMovies,
    private val searchMoviesNew: SearchMoviesNew
) : ViewModel() {


    val setViewEffect = MutableSharedFlow<ViewEffect>()
    val viewEffect: SharedFlow<ViewEffect>
        get() = setViewEffect.asSharedFlow()


    private val exceptionHandler = CoroutineExceptionHandler { _, error ->
        Log.e("error", "" + error.printStackTrace())
    }
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


    /*
    * Passing the data to MutableStateFlow
    * */
    fun searchNew(input: String) {
        if (input.isNotEmpty()) {
            viewModelScope.launch(postExecutionThread.io + exceptionHandler) {
                searchMoviesNew(input).collect {
                    when (it) {
                        is Result.Failed -> {
                            Log.e("error", "" + it.errorMesg)
                        }
                        Result.Loading -> {
                            Log.e("Loading", "")
                        }
                        is Result.Success<*> -> {
                            val movies = arrayListOf<MovieDisplay>()
                            (it.data as ArrayList<Movie>).forEachIndexed { index, movie ->
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
    }

    /*
  * Using state and events to process request and to show view
  * */
    fun event(event: Event, vararg string: String) {
        viewModelScope.launch {
            when (event) {
                is Event.SearchMovie -> {
                    if (event.input.isNotEmpty()) {
                        viewModelScope.launch(postExecutionThread.io + exceptionHandler) {
                            searchMoviesNew(event.input).collect {
                                when (it) {
                                    is Result.Failed -> {
                                        setViewEffect.emit(ViewEffect.ShowError("" + it.errorMesg))
                                    }
                                    Result.Loading -> {
                                        Log.e("Loading", "")
                                    }
                                    is Result.Success<*> -> {
                                        val movies = arrayListOf<MovieDisplay>()
                                        (it.data as ArrayList<Movie>).forEachIndexed { index, movie ->
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
                                        setViewEffect.emit(ViewEffect.ShowMovie(movies))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}