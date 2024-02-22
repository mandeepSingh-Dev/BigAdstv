package com.appsinvo.bigadstv.domain.data.repositories

import com.appsinvo.bigadstv.data.remote.model.auth.login.requestBody.LoginRequestBody
import com.appsinvo.bigadstv.data.remote.model.auth.login.response.LoginResponse
import com.appsinvo.bigadstv.data.remote.model.auth.login.response.LogoutResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult

interface AuthRepository {

    suspend fun login(loginRequestBody: LoginRequestBody) : NetworkResult<LoginResponse>
    suspend fun logout() : NetworkResult<LogoutResponse>
}