package com.appsinvo.bigadstv.presentation.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.appsinvo.bigadstv.data.local.database.Dao.AdsDao
import com.appsinvo.bigadstv.data.remote.model.ads.trackAds.requestBody.TrackAdsRequestBody
import com.appsinvo.bigadstv.data.remote.model.ads.trackAds.response.TrackAdsResponse
import com.appsinvo.bigadstv.data.remote.model.realWorldDateTime.RealWorldDateTimeResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.domain.data.useCases.RealWorldDateTimeUseCase
import com.appsinvo.bigadstv.domain.data.useCases.ads.AdsAllUseCases
import com.appsinvo.bigadstv.domain.local.useCases.tracksAds.AllTrackAdsUsecases
import com.appsinvo.bigadstv.utils.getHourOfDay
import com.appsinvo.bigadstv.utils.get_Date_Of_UTC_Time
import com.appsinvo.bigadstv.utils.isBetweenRange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class PlayerViewmodel @Inject constructor(private val adsAllUseCases: AdsAllUseCases, private val realWorldDateTimeUseCase: RealWorldDateTimeUseCase, private val allTrackAdsUsecases: AllTrackAdsUsecases) : ViewModel(){

    private var _trackAdsResponse : Channel<NetworkResult<TrackAdsResponse>> = Channel<NetworkResult<TrackAdsResponse>>()
    val trackAdsResponse = _trackAdsResponse.receiveAsFlow()


    suspend fun trackAds(trackAdsRequestBody: TrackAdsRequestBody){

        //Getting current world realtime Data-time from WorldApi.
        val currentTimeResponse = getCurrentWorldDateTime()

        //If World RealTIME Current Api is SUCCESS.
        val isHourInRange = if(currentTimeResponse is NetworkResult.Success){

            val currentHour = currentTimeResponse.data?.datetime?.get_Date_Of_UTC_Time()?.getHourOfDay() ?: 9
            val isHourInRange = currentHour.isBetweenRange(startHr = 6, endHr = 21)
            isHourInRange
        }
        //In case WorldApi response gets FAILED to get world time then get System current local time and hit userAd track api with local time.
        else if(currentTimeResponse is NetworkResult.Error){
            val currentHour = Date().getHourOfDay()
            val isHourInRange = currentHour.isBetweenRange(startHr = 6, endHr = 21)

            isHourInRange
        }else {
            false
        }

        if(isHourInRange == true){
            val response = adsAllUseCases.trackAdUsecase(trackAdsRequestBody)
        }
        //  _trackAdsResponse.send(response)
    }
    suspend fun getCurrentWorldDateTime(): NetworkResult<RealWorldDateTimeResponse> {
        val response = realWorldDateTimeUseCase()
        Log.d("vlkvnkfjvn",response.toString())
        return  response
    }

    suspend fun insertTrackAd(trackAdsRequestBody: TrackAdsRequestBody){
        allTrackAdsUsecases.insertTrackAdUsecase(trackAdsRequestBody = trackAdsRequestBody)
    }
   suspend fun getAllUserTrackAdsUsecase(){
        allTrackAdsUsecases.getAllUserTrackAdsUsecase()
    }
   suspend fun getUserTrackAdUsecase(advertismentId : String): Flow<TrackAdsRequestBody?>? {
        return allTrackAdsUsecases.getUserTrackAdUsecase(advertismentId = advertismentId)
    }
   suspend fun deleteAllUserTrackAdsUsecase(){
        allTrackAdsUsecases.deleteAllUserTrackAdsUsecase()
    }
    suspend fun deleteTrackAdUsecase(advertismentId: String){
        allTrackAdsUsecases.deleteTrackAdUsecase(advertismentId = advertismentId)
    }

}