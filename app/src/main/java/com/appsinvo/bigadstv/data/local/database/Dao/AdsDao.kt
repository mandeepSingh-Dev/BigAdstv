package com.appsinvo.bigadstv.data.local.database.Dao

import androidx.media3.extractor.mp4.Track
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appsinvo.bigadstv.data.remote.model.ads.trackAds.requestBody.TrackAdsRequestBody
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

@Dao
interface AdsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackAd(trackAdsRequestBody: TrackAdsRequestBody)

    @Query("SELECT * FROM `Track Ads Request Body`")
    fun getAllUserTrackAds() : Flow<List<TrackAdsRequestBody>?>

    @Query("SELECT * FROM `Track Ads Request Body` WHERE advertisementId=:advertismentID")
    fun getUserTrackAd(advertismentID : String?) : Flow<TrackAdsRequestBody?>?

    @Query("DELETE FROM `Track Ads Request Body`")
    suspend fun deleteAllUserTrackAds() : Int

    @Query("DELETE FROM `Track Ads Request Body` WHERE advertisementId=:advertismentID")
    suspend fun deleteTrackAd(advertismentID: String?) : Int

}