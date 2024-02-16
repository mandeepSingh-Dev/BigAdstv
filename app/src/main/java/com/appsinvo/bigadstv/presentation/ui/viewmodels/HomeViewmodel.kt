package com.appsinvo.bigadstv.presentation.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsinvo.bigadstv.data.remote.model.ads.getAllAds.response.AllAdsResponse
import com.appsinvo.bigadstv.data.remote.model.ads.trackAds.requestBody.TrackAdsRequestBody
import com.appsinvo.bigadstv.data.remote.model.ads.trackAds.response.TrackAdsResponse
import com.appsinvo.bigadstv.data.remote.model.auth.login.response.LogoutResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.domain.useCases.ads.AdsAllUseCases
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewmodel @Inject constructor(private val adsAllUseCases: AdsAllUseCases): ViewModel() {

    private var _allAdsResponse : Channel<NetworkResult<AllAdsResponse>> = Channel<NetworkResult<AllAdsResponse>>()
    val allAdsResponse = _allAdsResponse.receiveAsFlow()

  private var _trackAdsResponse : Channel<NetworkResult<TrackAdsResponse>> = Channel<NetworkResult<TrackAdsResponse>>()
    val trackAdsResponse = _trackAdsResponse.receiveAsFlow()




    suspend fun getAllAds(page : String? = "1" , limit : String? = null , adType : String?){
        _allAdsResponse.send(NetworkResult.Loading())
        val response = adsAllUseCases.getAllAdsUsecase(page = page, limit = limit, adType = adType)
        _allAdsResponse.send(response)
    }


    suspend fun trackAds(trackAdsRequestBody: TrackAdsRequestBody){
      //  _trackAdsResponse.send(NetworkResult.Loading())
        val response = adsAllUseCases.trackAdUsecase(trackAdsRequestBody)
      //  _trackAdsResponse.send(response)
    }


}