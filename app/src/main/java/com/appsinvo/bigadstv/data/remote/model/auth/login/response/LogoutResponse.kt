package com.appsinvo.bigadstv.data.remote.model.auth.login.response

data class LogoutResponse(
    val `data`: LoginResponseData?,
    val message: String?,
    val status_code: Int?
)