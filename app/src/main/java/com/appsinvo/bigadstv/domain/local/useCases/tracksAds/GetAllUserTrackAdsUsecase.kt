package com.appsinvo.bigadstv.domain.local.useCases.tracksAds

import com.appsinvo.bigadstv.domain.local.repositories.TracksAdsRepository
import javax.inject.Inject


class GetAllUserTrackAdsUsecase @Inject constructor(private val tracksAdsRepository: TracksAdsRepository) {

    suspend operator fun invoke(){
        tracksAdsRepository.getAllUserTrackAds()
    }

}