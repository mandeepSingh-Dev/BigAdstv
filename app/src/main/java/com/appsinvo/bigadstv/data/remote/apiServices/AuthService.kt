package com.appsinvo.bigadstv.data.remote.apiServices

import com.appsinvo.bigadstv.data.remote.model.auth.login.requestBody.LoginRequestBody
import com.appsinvo.bigadstv.data.remote.model.auth.login.response.LoginResponse
import com.appsinvo.bigadstv.data.remote.model.auth.login.response.LogoutResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.ConstantsRemote
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthService {

    @POST(ConstantsRemote.login)
    suspend fun login(@Body loginRequestBody: LoginRequestBody) : Response<LoginResponse>

    @PATCH(ConstantsRemote.logout)
    suspend fun logout() : Response<LogoutResponse>



}