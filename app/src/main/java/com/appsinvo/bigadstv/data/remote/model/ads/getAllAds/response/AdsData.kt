package com.appsinvo.bigadstv.data.remote.model.ads.getAllAds.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class AdsData(
    val adType: Int?,
    val advertisementId: String?,
    val category: String?,
    val cost: Int?,
    val endDate: String?,
    val endTime: String?,
    val filePath: String?,
    val position: Int?,
    val startDate: String?,
    val startTime: String?,
    val title: String?,
    val userId: String?,
    var userName: String?,
    var userLocation: String?,
    var userImg: String?,
    var startAdPlayerPos : Int?
): Parcelable