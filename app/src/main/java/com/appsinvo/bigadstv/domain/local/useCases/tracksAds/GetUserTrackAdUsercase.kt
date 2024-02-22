package com.appsinvo.bigadstv.domain.local.useCases.tracksAds

import com.appsinvo.bigadstv.data.remote.model.ads.trackAds.requestBody.TrackAdsRequestBody
import com.appsinvo.bigadstv.domain.local.repositories.TracksAdsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetUserTrackAdUsercase @Inject constructor(private val tracksAdsRepository: TracksAdsRepository) {

    suspend operator fun invoke(advertismentId : String): Flow<TrackAdsRequestBody?>? {
        return tracksAdsRepository.getUserTrackAd(advertismentID = advertismentId)
    }

}