package com.appsinvo.bigadstv.data.remote.remoteRepositories

import android.annotation.SuppressLint
import android.util.Log
import com.appsinvo.bigadstv.data.remote.apiServices.RealWorldTimeApiService

import com.appsinvo.bigadstv.data.remote.model.realWorldDateTime.RealWorldDateTimeResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.data.remote.networkUtils.handleUseCaseException
import com.appsinvo.bigadstv.domain.repositories.RealWorldDateTimeRepository

import javax.inject.Inject

class RealWorldDateTimeRepositoryImpl @Inject constructor(private val realWorldDateTimeApiService: RealWorldTimeApiService) : RealWorldDateTimeRepository {

    @SuppressLint("SuspiciousIndentation")
    override suspend fun getCurrentWorldTime(): NetworkResult<RealWorldDateTimeResponse> {
        return try{
            val response = realWorldDateTimeApiService.getCurrentWorldTime()
               NetworkResult.Success(data = response.body())
        }catch (e:Exception){
            Log.d("fvfjnvjfv",e.message.toString())
            NetworkResult.Error(handleUseCaseException(exception = e))
        }
    }


}