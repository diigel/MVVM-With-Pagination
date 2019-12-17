package com.dhani.mvvm.detail

import androidx.lifecycle.LiveData
import com.dhani.mvvm.data.api.Routes
import com.dhani.mvvm.data.repository.DetailNetworkDataSource
import com.dhani.mvvm.data.repository.NetworkState
import com.dhani.mvvm.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MarketplaceDetailRepository (private val apiService: Routes) {

    private lateinit var detailNetworkDataSource: DetailNetworkDataSource

    fun fetchDetails(compositeDisposable: CompositeDisposable,movie:Int ) : LiveData<MovieDetails>{
        detailNetworkDataSource = DetailNetworkDataSource(apiService,compositeDisposable)
        detailNetworkDataSource.fetchDetails(movie)

        return detailNetworkDataSource.downloadResonse
    }

    fun detailNetworkState() : LiveData<NetworkState>{
        return detailNetworkDataSource.networkState
    }
}