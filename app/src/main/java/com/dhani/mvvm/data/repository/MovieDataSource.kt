package com.dhani.mvvm.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.dhani.mvvm.data.api.FIRST_PAGE
import com.dhani.mvvm.data.api.Routes
import com.dhani.mvvm.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource(private val apiService : Routes,
                      private val compositeDisposable: CompositeDisposable) : PageKeyedDataSource<Int, Movie>(){

    private var page = FIRST_PAGE

    val networkState : MutableLiveData<NetworkState> = MutableLiveData()
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
       networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getPopularMovie(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.movies,null,page+1)
                        networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("Error Data Source",it.message!!)
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getPopularMovie(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if (it.page >= params.key ){
                            callback.onResult(it.movies,params.key+1)
                            networkState.postValue(NetworkState.LOADED)
                        }else{
                            networkState.postValue(NetworkState.ENDOFPAGE)
                        }
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("Error Data Source",it.message!!)
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}