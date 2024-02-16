package com.appsinvo.bigadstv.data.remote.model.auth.logout.response

data class LogoutResponse(
    val `data`: LogoutResponseData?,
    val message: String?,
    val status_code: Int?
)