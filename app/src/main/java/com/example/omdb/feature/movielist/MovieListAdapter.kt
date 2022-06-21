package com.example.omdb.feature.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.core.utility.DefaultDiffUtil
import com.example.omdb.R
import com.example.omdb.databinding.ItemMovieBinding
import com.example.omdb.feature.model.MovieDisplay
import javax.inject.Inject
/**
 * Adapter for movie list
 */
class MovieListAdapter @Inject constructor() : ListAdapter<MovieDisplay, MovieListAdapter.MovieViewHolder>(
    DefaultDiffUtil()
) {

    var onMovieItemClickListener : OnMovieItemClickListener? = null

    interface OnMovieItemClickListener {
        fun onMovieClick(movie: MovieDisplay)
        fun onFavoriteClick(movie: MovieDisplay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class MovieViewHolder(private val _binding: ItemMovieBinding) : RecyclerView.ViewHolder(_binding.root) {
        fun bind(position: Int) {
            getItem(position).let { movie ->
                with(_binding) {
                    val context = root.context
                    txtMovieName.text = movie.name
                    txtGenre.text = movie.genre

                    txtDescription.text = movie.year
                    imgThumbnail.load(movie.thumbnail) {
                        crossfade(true)
                        error(R.drawable.ic_baseline_broken_image_24)
                    }

                    root.setOnClickListener {
                        onMovieItemClickListener?.onMovieClick(movie)
                    }
                }
            }
        }
    }
}