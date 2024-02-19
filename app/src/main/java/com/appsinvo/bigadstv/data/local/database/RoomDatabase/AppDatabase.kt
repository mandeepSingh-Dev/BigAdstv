package com.appsinvo.bigadstv.data.local.database.RoomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.appsinvo.bigadstv.data.local.database.Dao.AdsDao
import com.appsinvo.bigadstv.data.local.database.converters.TrackAdsRequestBodyConverters
import com.appsinvo.bigadstv.data.remote.model.ads.trackAds.requestBody.TrackAdsRequestBody

@Database(entities = [TrackAdsRequestBody::class], version = 1, exportSchema = true)
@TypeConverters(TrackAdsRequestBodyConverters::class)
abstract class AppDatabase : RoomDatabase(){

    abstract fun getAdsDao() : AdsDao
}