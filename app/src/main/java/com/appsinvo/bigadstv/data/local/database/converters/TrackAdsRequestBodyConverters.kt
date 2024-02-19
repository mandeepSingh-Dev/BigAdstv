package com.appsinvo.bigadstv.data.local.database.converters

import androidx.room.TypeConverter
import com.appsinvo.bigadstv.data.remote.model.ads.trackAds.requestBody.TrackAdsRequestBody
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TrackAdsRequestBodyConverters {
    @TypeConverter
    fun fromTrackAdsRequestBody(trackAdsRequestBody: TrackAdsRequestBody): String {
        val gson = Gson()
        return gson.toJson(trackAdsRequestBody)
    }

    @TypeConverter
    fun toTrackAdsRequestBody(trackAdsRequestBodyString: String): TrackAdsRequestBody {
        val gson = Gson()
        val type = object : TypeToken<TrackAdsRequestBody>() {}.type
        return gson.fromJson(trackAdsRequestBodyString, type)
    }



}
