package com.dhani.mvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.dhani.mvvm.R
import com.dhani.mvvm.data.api.ClientInterface
import com.dhani.mvvm.data.api.Routes
import com.dhani.mvvm.data.repository.NetworkState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    lateinit var movieRepository: MoviePageListRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService : Routes = ClientInterface.getClient()

        movieRepository = MoviePageListRepository(apiService)

        viewModel = getViewModel()

        val movieAdapter = MovieListAdapter(this)

        val gridLayoutManager = GridLayoutManager(this,3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewTypes =  movieAdapter.getItemViewType(position)
                return if (viewTypes == movieAdapter.MOVIE_VIEW_TYPE) 1 else 3
            }
        }

        rv_movie_list.layoutManager = gridLayoutManager
        rv_movie_list.setHasFixedSize(true)
        rv_movie_list.adapter = movieAdapter

        viewModel.moviePageList.observe(this, Observer {
            movieAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar_popular.visibility = if (viewModel.listEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_popular.visibility = if (viewModel.listEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listEmpty())
                movieAdapter.setNetworkState(it)
        })

    }

    private fun getViewModel(): MainActivityViewModel{
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(movieRepository) as T
            }
        })[MainActivityViewModel::class.java]
    }
}
