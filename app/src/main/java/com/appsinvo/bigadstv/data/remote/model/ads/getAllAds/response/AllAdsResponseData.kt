package com.appsinvo.bigadstv.data.remote.model.ads.getAllAds.response

data class AllAdsResponseData(
    val `data`: List<AdsData>?,
    val limit: Int?,
    val page: Int?,
    val total_count: Int?,
    val userName: String?,
    val userAddr: String?,
    val userImg: String?,
)