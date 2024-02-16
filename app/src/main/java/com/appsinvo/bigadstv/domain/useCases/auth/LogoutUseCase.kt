package com.appsinvo.bigadstv.domain.useCases.auth

import com.appsinvo.bigadstv.data.remote.model.auth.login.response.LogoutResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.domain.repositories.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(): NetworkResult<LogoutResponse> {
        return authRepository.logout()
    }
}