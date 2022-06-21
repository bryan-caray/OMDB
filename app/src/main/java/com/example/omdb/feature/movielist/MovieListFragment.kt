package com.example.omdb.feature.movielist

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.core.extension.hideKeyboard
import com.example.core.extension.observe
import com.example.core.extension.showIf
import com.example.core.extension.viewBinding
import com.example.omdb.R
import com.example.omdb.databinding.FragmentMovieListBinding
import com.example.omdb.feature.model.MovieDisplay
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieListFragment : Fragment(R.layout.fragment_movie_list),
    MovieListAdapter.OnMovieItemClickListener {

    private val _binding by viewBinding(FragmentMovieListBinding::bind)
    private val _viewModel by viewModels<MovieListViewModel>()

    @Inject
    lateinit var adapter: MovieListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        with(_binding) {
            adapter.onMovieItemClickListener = this@MovieListFragment
            rvMovieList.adapter = adapter
            btnSearchCancel.setOnClickListener {
                with(etSearch) {
                    text.clear()
                    clearFocus()
                }
                hideKeyboard()
            }
            etSearch.addTextChangedListener {
                btnSearchCancel showIf it?.isNotEmpty()
                empty.container showIf it?.isEmpty()
                _viewModel.search(it.toString())
            }
        }
        with(_viewModel) {
            movies.observe(this@MovieListFragment) {
                _binding.empty.container showIf it.isEmpty()
                adapter.submitList(it)
            }
        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onMovieClick(movie: MovieDisplay) {
     //   findNavController().navigate(MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(movie.id))
    }

    override fun onFavoriteClick(movie: MovieDisplay) {
        //_viewModel.toggleFavoriteMovie(movie)
    }

    companion object {
        const val VISITED_DATE_TIME_PATTERN = "MMM d, HH:mm a"
    }
}