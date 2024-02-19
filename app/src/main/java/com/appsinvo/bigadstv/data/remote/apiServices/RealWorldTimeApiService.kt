package com.appsinvo.bigadstv.data.remote.apiServices

import com.appsinvo.bigadstv.data.remote.model.realWorldDateTime.RealWorldDateTimeResponse
import retrofit2.Response
import retrofit2.http.GET


interface RealWorldTimeApiService{

    @GET("ip")
    suspend fun getCurrentWorldTime() : Response<RealWorldDateTimeResponse>

}