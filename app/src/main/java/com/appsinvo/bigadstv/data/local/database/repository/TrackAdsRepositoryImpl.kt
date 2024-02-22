package com.appsinvo.bigadstv.data.local.database.repository

import com.appsinvo.bigadstv.data.local.database.Dao.AdsDao
import com.appsinvo.bigadstv.data.remote.model.ads.trackAds.requestBody.TrackAdsRequestBody
import com.appsinvo.bigadstv.domain.local.repositories.TracksAdsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TrackAdsRepositoryImpl @Inject constructor(private val adsDao: AdsDao) : TracksAdsRepository {
    override suspend fun insertTrackAd(trackAdsRequestBody: TrackAdsRequestBody) {
        try{
            adsDao.insertTrackAd(trackAdsRequestBody = trackAdsRequestBody)
        }catch (e:Exception){

        }

    }

    override fun getAllUserTrackAds(): Flow<List<TrackAdsRequestBody>?> {
            val result = adsDao.getAllUserTrackAds().catch {
                emit(emptyList())
            }.map {
                it
            }
        return result
    }

    override fun getUserTrackAd(advertismentID: String?): Flow<TrackAdsRequestBody?>? {
        val result = adsDao.getUserTrackAd(advertismentID = advertismentID)?.catch {
            emit(null)
        }?.map {
            it
        }
        return result
    }

    override suspend fun deleteAllUserTrackAds(): Int? {
        return try{
            adsDao.deleteAllUserTrackAds()
        }catch (e:Exception){
            null
        }
    }

    override suspend fun deleteTrackAd(advertismentID: String?): Int? {
        return try{
            adsDao.deleteTrackAd(advertismentID = advertismentID)
        }catch (e:Exception){
            null
        }
    }


}