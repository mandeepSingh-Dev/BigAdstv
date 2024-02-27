package com.appsinvo.bigadstv.domain.data.useCases.ads

import com.appsinvo.bigadstv.data.remote.model.ads.getAllAds.response.AllAdsResponse
import com.appsinvo.bigadstv.data.remote.model.ads.getUserEarnings.response.UserEarningResponse
import com.appsinvo.bigadstv.data.remote.model.ads.trackAds.requestBody.TrackAdsRequestBody
import com.appsinvo.bigadstv.data.remote.model.ads.trackAds.response.TrackAdsResponse
import com.appsinvo.bigadstv.data.remote.model.auth.login.response.LogoutResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.domain.data.repositories.AdsRepository
import com.appsinvo.bigadstv.domain.data.repositories.AuthRepository
import javax.inject.Inject



class GetAllAdsUsecase @Inject constructor(private val adsRepository : AdsRepository) {

    suspend operator fun invoke(page : String? = null, limit : String? = null, adType : String?): NetworkResult<AllAdsResponse> {
        return adsRepository.getAllAds(page = page, limit = limit, adType = adType)
    }
}

