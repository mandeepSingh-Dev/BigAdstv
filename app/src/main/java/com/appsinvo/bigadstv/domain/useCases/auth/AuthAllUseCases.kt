package com.appsinvo.bigadstv.domain.useCases.auth

data class AuthAllUseCases(
    val loginUseCase: LoginUseCase,
    val logoutUseCase: LogoutUseCase
)
