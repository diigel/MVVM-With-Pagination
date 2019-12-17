package com.dhani.mvvm.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.dhani.mvvm.R
import com.dhani.mvvm.data.api.Routes
import com.dhani.mvvm.data.api.DbClientInterface
import com.dhani.mvvm.data.api.POSTER_BASE_URL
import com.dhani.mvvm.data.repository.NetworkState
import com.dhani.mvvm.data.vo.MovieDetails
import kotlinx.android.synthetic.main.activity_detail.*
import java.text.NumberFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: SingleMarketplaceViewModel
    private lateinit var marketplaceRepository: MarketplaceDetailRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val movie: Int = intent.getIntExtra("movie",0)

        val apiService : Routes = DbClientInterface.getClient()
        marketplaceRepository = MarketplaceDetailRepository(apiService)

        viewModel = getViewModel(movie)

        viewModel.marketplaceDetail.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })
    }

    fun bindUI( it: MovieDetails){
        movie_title.text = it.title
        movie_tagline.text = it.tagline
        movie_release_date.text = it.releaseDate
        movie_rating.text = it.rating.toString()
        movie_runtime.text = it.runtime.toString() + " minutes"
        movie_overview.text = it.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text = formatCurrency.format(it.budget)
        movie_revenue.text = formatCurrency.format(it.revenue)

        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_movie_poster)
    }

    private fun getViewModel(movie : Int): SingleMarketplaceViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMarketplaceViewModel(marketplaceRepository,movie) as T
            }
        })[SingleMarketplaceViewModel::class.java]
    }
}
