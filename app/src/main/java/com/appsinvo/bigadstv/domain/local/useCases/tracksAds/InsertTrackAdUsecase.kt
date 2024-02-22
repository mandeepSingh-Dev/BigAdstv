package com.appsinvo.bigadstv.domain.local.useCases.tracksAds

import com.appsinvo.bigadstv.data.remote.model.ads.trackAds.requestBody.TrackAdsRequestBody
import com.appsinvo.bigadstv.domain.local.repositories.TracksAdsRepository
import javax.inject.Inject

class InsertTrackAdUsecase @Inject constructor(private val tracksAdsRepository: TracksAdsRepository) {

    suspend operator fun invoke(trackAdsRequestBody: TrackAdsRequestBody){
        tracksAdsRepository.insertTrackAd(trackAdsRequestBody = trackAdsRequestBody)
    }

}