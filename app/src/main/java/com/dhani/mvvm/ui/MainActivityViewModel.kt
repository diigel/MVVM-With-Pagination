package com.dhani.mvvm.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dhani.mvvm.data.repository.NetworkState
import com.dhani.mvvm.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(private val movieRepository: MoviePageListRepository) : ViewModel(){

    private val compositeDisposable = CompositeDisposable()

    val moviePageList : LiveData<PagedList<Movie>> by lazy {
        movieRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val networkState : LiveData<NetworkState> by lazy {
        movieRepository.getNetworkSate()
    }

    fun listEmpty() :Boolean{
        return moviePageList.value?.isEmpty() ?:true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}