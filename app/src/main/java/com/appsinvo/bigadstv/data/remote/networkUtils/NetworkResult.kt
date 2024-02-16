package com.appsinvo.bigadstv.data.remote.networkUtils

sealed class  NetworkResult<T> (message : String?, data : T?, error : String?) {

    class Loading<T> : NetworkResult<T>(null,null,null)
    data class Success<T>(val message : String? = null,val data: T?) : NetworkResult<T>(message = message, data = data, error = null)
    data class Error<T>(val error : String? = null, val data: T? = null) : NetworkResult<T>(message = null, data = data, error = error)
}