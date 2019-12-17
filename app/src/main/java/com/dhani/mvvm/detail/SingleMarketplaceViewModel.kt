package com.dhani.mvvm.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dhani.mvvm.data.repository.NetworkState
import com.dhani.mvvm.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class SingleMarketplaceViewModel(private val marketplaceRepository : MarketplaceDetailRepository,movie : Int) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val marketplaceDetail: LiveData<MovieDetails> by lazy {
        marketplaceRepository.fetchDetails(compositeDisposable,movie)
    }

    val networkState: LiveData<NetworkState> by lazy {
        marketplaceRepository.detailNetworkState()
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}