package com.appsinvo.bigadstv.domain.local.repositories

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appsinvo.bigadstv.data.remote.model.ads.trackAds.requestBody.TrackAdsRequestBody
import kotlinx.coroutines.flow.Flow

interface TracksAdsRepository {

    suspend fun insertTrackAd(trackAdsRequestBody: TrackAdsRequestBody)

    fun getAllUserTrackAds() : Flow<List<TrackAdsRequestBody>?>

    fun getUserTrackAd(advertismentID : String?) : Flow<TrackAdsRequestBody?>?

    suspend fun deleteAllUserTrackAds() : Int?

    suspend fun deleteTrackAd(advertismentID: String?) : Int?


}