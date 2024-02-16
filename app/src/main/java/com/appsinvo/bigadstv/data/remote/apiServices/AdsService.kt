package com.appsinvo.bigadstv.data.remote.apiServices

import com.appsinvo.bigadstv.data.remote.model.ads.getAllAds.response.AllAdsResponse
import com.appsinvo.bigadstv.data.remote.model.ads.trackAds.requestBody.TrackAdsRequestBody
import com.appsinvo.bigadstv.data.remote.model.ads.trackAds.response.TrackAdsResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.ConstantsRemote
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AdsService {

    @GET(ConstantsRemote.getAllAds)
    suspend fun getAllAds(
        @Query(ConstantsRemote.page) page:String? = "1",
        @Query(ConstantsRemote.limit) limit:String? = null,
        @Query(ConstantsRemote.adType) adType:String?=null
    ) : Response<AllAdsResponse>


    @POST(ConstantsRemote.userAdTrack)
    suspend fun trackAd(
        @Body trackAdsRequestBody: TrackAdsRequestBody
    ) : Response<TrackAdsResponse>


}