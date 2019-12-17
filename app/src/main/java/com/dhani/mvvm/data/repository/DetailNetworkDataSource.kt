package com.dhani.mvvm.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dhani.mvvm.data.api.Routes
import com.dhani.mvvm.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailNetworkDataSource(
    private val apiService: Routes,
    private val compositeDisposable: CompositeDisposable) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadResponse = MutableLiveData<MovieDetails>()
    val downloadResonse: LiveData<MovieDetails>
        get() = _downloadResponse

    fun fetchDetails(movie : Int) {
        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getMovieDetails(movie)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        }, {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("Error",it.message!!)
                        }
                    )
            )
        } catch (e: Exception) {
            Log.e("Error Exception",e.message!!)
        }
    }
}