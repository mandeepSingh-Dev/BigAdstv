package com.appsinvo.bigadstv.domain.local.useCases.tracksAds

import com.appsinvo.bigadstv.domain.local.repositories.TracksAdsRepository
import javax.inject.Inject


class DeleteTrackAdUsecase @Inject constructor(private val tracksAdsRepository: TracksAdsRepository) {

    suspend operator fun invoke(advertismentId : String){
        tracksAdsRepository.deleteTrackAd(advertismentID = advertismentId)
    }

}