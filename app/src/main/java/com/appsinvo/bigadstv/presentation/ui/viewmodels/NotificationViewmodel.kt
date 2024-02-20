package com.appsinvo.bigadstv.presentation.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsinvo.bigadstv.data.remote.model.common.notifications.NotificationResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.domain.useCases.common.AllCommonUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewmodel @Inject constructor(private val allCommonUseCases: AllCommonUseCases) : ViewModel() {

    private val _notificationResponse : Channel<NetworkResult<NotificationResponse>> = Channel()
    val notificationResponse = _notificationResponse.receiveAsFlow()


    init {
        viewModelScope.launch {
            getNotification()
        }
    }

    suspend fun getNotification(){
        _notificationResponse.send(NetworkResult.Loading())
        val response = allCommonUseCases.getNotificationUseCase()
        _notificationResponse.send(response)
    }

}