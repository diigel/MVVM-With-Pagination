package com.dhani.mvvm.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dhani.mvvm.data.api.POST_PER_PAGE
import com.dhani.mvvm.data.api.Routes
import com.dhani.mvvm.data.repository.MovieDataSource
import com.dhani.mvvm.data.repository.MovieDataSourceFactory
import com.dhani.mvvm.data.repository.NetworkState
import com.dhani.mvvm.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviePageListRepository(private val apiService : Routes) {

    lateinit var moviePageList: LiveData<PagedList<Movie>>
    lateinit var movieDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable):LiveData<PagedList<Movie>>{
        movieDataSourceFactory = MovieDataSourceFactory(apiService,compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePageList = LivePagedListBuilder(movieDataSourceFactory,config).build()

        return moviePageList
    }

    fun getNetworkSate():LiveData<NetworkState>{
        return Transformations.switchMap<MovieDataSource,NetworkState>(
            movieDataSourceFactory.movieLiveDataSource, MovieDataSource::networkState
        )
    }
}
