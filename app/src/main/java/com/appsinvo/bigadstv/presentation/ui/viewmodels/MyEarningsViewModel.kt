package com.appsinvo.bigadstv.presentation.ui.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.appsinvo.bigadstv.data.remote.model.ads.getUserEarnings.response.UserEarningResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.domain.data.useCases.ads.AdsAllUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class MyEarningsViewModel @Inject constructor(private val addAllAdsUsecase : AdsAllUseCases) : ViewModel() {


    private val _userEarningResponse : Channel<NetworkResult<UserEarningResponse>> = Channel<NetworkResult<UserEarningResponse>>()
    val userEarningResponse : Flow<NetworkResult<UserEarningResponse>> = _userEarningResponse.receiveAsFlow()

    @SuppressLint("SuspiciousIndentation")
    suspend fun getEarnings(page: String? = null, limit: String? =null, month: Int? = null){
        _userEarningResponse.send(NetworkResult.Loading())
      val response =  addAllAdsUsecase.getUserEarningsUseCase(page = page, limit = limit, month = month)
        _userEarningResponse.send(response)
    }

}