package com.dhani.mvvm.data.repository

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED,
    ENDOFPAGE
}

class NetworkState(val status: Status, val msg: String) {

    companion object {
        val LOADED: NetworkState
        val LOADING: NetworkState
        val ERROR: NetworkState
        val ENDOFPAGE: NetworkState

        init {
            LOADED = NetworkState(Status.SUCCESS, "Sucses")
            LOADING = NetworkState(Status.RUNNING, "Running")
            ERROR = NetworkState(Status.FAILED, "Something went wrong !")
            ENDOFPAGE = NetworkState(Status.ENDOFPAGE, "No more data")
        }
    }
}