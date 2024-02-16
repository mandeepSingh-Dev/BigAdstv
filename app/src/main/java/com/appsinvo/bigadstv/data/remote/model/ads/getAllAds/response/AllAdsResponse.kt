package com.appsinvo.bigadstv.data.remote.model.ads.getAllAds.response

data class AllAdsResponse(
    val `data`: AllAdsResponseData?,
    val message: String?,
    val status_code: Int?
)