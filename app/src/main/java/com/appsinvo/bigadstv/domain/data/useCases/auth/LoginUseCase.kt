package com.appsinvo.bigadstv.domain.data.useCases.auth

import android.annotation.SuppressLint
import android.content.Context
import com.appsinvo.bigadstv.data.remote.model.auth.login.requestBody.LoginRequestBody
import com.appsinvo.bigadstv.data.remote.model.auth.login.response.LoginResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.domain.data.repositories.AuthRepository
import com.appsinvo.bigadstv.utils.isEmailValid
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {

    @ApplicationContext
    @Inject
    lateinit var context : Context

     @SuppressLint("SuspiciousIndentation")
     suspend operator fun invoke(loginRequestBody: LoginRequestBody): NetworkResult<LoginResponse> {
      val isValid = loginRequestBody.email?.isEmailValid(context = context)

            return if(!isValid.isNullOrEmpty() || isValid == "null"){
                NetworkResult.Error(error = isValid)
            }else{
                authRepository.login(loginRequestBody = loginRequestBody)
            }
    }
}