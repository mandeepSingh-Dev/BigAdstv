package com.appsinvo.bigadstv.domain.repositories

import com.appsinvo.bigadstv.data.remote.model.ads.getAllAds.response.AllAdsResponse
import com.appsinvo.bigadstv.data.remote.model.ads.trackAds.requestBody.TrackAdsRequestBody
import com.appsinvo.bigadstv.data.remote.model.ads.trackAds.response.TrackAdsResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.ConstantsRemote
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AdsRepository {

    suspend fun getAllAds(page : String? = null, limit : String? = null, adType : String? = null) : NetworkResult<AllAdsResponse>
   suspend fun trackAd(trackAdsRequestBody: TrackAdsRequestBody) : NetworkResult<TrackAdsResponse>

}