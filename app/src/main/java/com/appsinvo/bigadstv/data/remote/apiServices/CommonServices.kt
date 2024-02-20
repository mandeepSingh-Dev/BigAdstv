package com.appsinvo.bigadstv.data.remote.apiServices

import com.appsinvo.bigadstv.data.remote.model.common.notifications.NotificationResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.ConstantsRemote
import retrofit2.Response
import retrofit2.http.GET

interface CommonServices {

    @GET(ConstantsRemote.getNotifications)
    suspend fun getNotifications() : Response<NotificationResponse>

}