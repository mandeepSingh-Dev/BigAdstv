package com.appsinvo.bigadstv.domain.data.useCases.ads

import com.appsinvo.bigadstv.data.remote.model.ads.trackAds.requestBody.TrackAdsRequestBody
import com.appsinvo.bigadstv.data.remote.model.ads.trackAds.response.TrackAdsResponse
import com.appsinvo.bigadstv.data.remote.model.common.notifications.NotificationResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.domain.data.repositories.AdsRepository
import com.appsinvo.bigadstv.domain.data.repositories.CommonRepository
import javax.inject.Inject

class TrackAdUsecase @Inject constructor(private val adsRepository : AdsRepository) {
    
    suspend operator fun invoke(trackAdsRequestBody : TrackAdsRequestBody): NetworkResult<TrackAdsResponse> {
        return adsRepository.trackAd(trackAdsRequestBody = trackAdsRequestBody)
    }
}
