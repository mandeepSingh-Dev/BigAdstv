package com.appsinvo.bigadstv.data.remote.model.common.notifications

data class NotificationResponse(
    val `data`: List<NotificationData?>?,
    val message: String?,
    val status_code: Int?
)