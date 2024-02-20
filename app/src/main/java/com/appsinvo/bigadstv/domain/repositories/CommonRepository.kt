package com.appsinvo.bigadstv.domain.repositories

import com.appsinvo.bigadstv.data.remote.model.auth.login.requestBody.LoginRequestBody
import com.appsinvo.bigadstv.data.remote.model.auth.login.response.LoginResponse
import com.appsinvo.bigadstv.data.remote.model.common.notifications.NotificationResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult

interface CommonRepository {
    suspend fun getNotifications() : NetworkResult<NotificationResponse>
}