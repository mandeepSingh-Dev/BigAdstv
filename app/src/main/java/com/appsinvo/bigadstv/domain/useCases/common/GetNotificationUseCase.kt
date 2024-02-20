package com.appsinvo.bigadstv.domain.useCases.common

import com.appsinvo.bigadstv.data.remote.model.common.notifications.NotificationResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.domain.repositories.CommonRepository
import javax.inject.Inject

class GetNotificationUseCase @Inject constructor(private val commonRepository: CommonRepository) {
    
    suspend operator fun invoke(): NetworkResult<NotificationResponse> {
        return commonRepository.getNotifications()
    }
}