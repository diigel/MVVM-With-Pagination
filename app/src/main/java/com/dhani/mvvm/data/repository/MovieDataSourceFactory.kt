package com.dhani.mvvm.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dhani.mvvm.data.api.Routes
import com.dhani.mvvm.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(private val apiService : Routes,
                             private val compositeDisposable: CompositeDisposable) : DataSource.Factory<Int,Movie>() {

    val movieLiveDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService,compositeDisposable)

        movieLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}