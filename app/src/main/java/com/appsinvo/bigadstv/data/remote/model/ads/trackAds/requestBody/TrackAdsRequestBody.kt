package com.appsinvo.bigadstv.data.remote.model.ads.trackAds.requestBody

import androidx.room.Dao
import androidx.room.Entity
import com.appsinvo.bigadstv.data.local.database.dbUtils.ConstantsDatabase


@Entity(tableName = ConstantsDatabase.TRACK_AD_REQUEST_BODY, primaryKeys = ["advertisementId"])
data class TrackAdsRequestBody(
    val advertisementId: String,
    val endTime: String?,
    val id: String?,
    val startTime: String?,
    val watchTime: Int?
)