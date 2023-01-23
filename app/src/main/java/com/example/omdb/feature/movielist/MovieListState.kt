package com.example.omdb.feature.movielist

import com.example.omdb.feature.model.MovieDisplay

sealed class MovieListState {
}

sealed class ViewEffect {
    data class ShowMovie(val movies: ArrayList<MovieDisplay>) : ViewEffect()
    data class ShowError(val error: String) : ViewEffect()
    object ShowLoading : ViewEffect()

}

sealed class Event {
    data class SearchMovie(val input: String) : Event()
}