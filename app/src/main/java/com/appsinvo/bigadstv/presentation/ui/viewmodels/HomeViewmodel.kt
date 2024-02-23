package com.appsinvo.bigadstv.presentation.ui.viewmodels

import DefaultPaginator
import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsinvo.bigadstv.data.local.database.Dao.AdsDao
import com.appsinvo.bigadstv.data.remote.model.ads.getAllAds.response.AllAdsResponse
import com.appsinvo.bigadstv.data.remote.model.ads.trackAds.requestBody.TrackAdsRequestBody
import com.appsinvo.bigadstv.data.remote.model.ads.trackAds.response.TrackAdsResponse
import com.appsinvo.bigadstv.data.remote.model.realWorldDateTime.RealWorldDateTimeResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.domain.data.useCases.RealWorldDateTimeUseCase
import com.appsinvo.bigadstv.domain.data.useCases.ads.AdsAllUseCases
import com.appsinvo.bigadstv.utils.getHourOfDay
import com.appsinvo.bigadstv.utils.get_Date_Of_UTC_Time
import com.appsinvo.bigadstv.utils.isBetweenRange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class HomeViewmodel @Inject constructor(private val adsAllUseCases: AdsAllUseCases, private val realWorldDateTimeUseCase: RealWorldDateTimeUseCase, private val adsDao: AdsDao): ViewModel() {

    private var _allAdsResponse : Channel<NetworkResult<AllAdsResponse>> = Channel<NetworkResult<AllAdsResponse>>()
    val allAdsResponse = _allAdsResponse.receiveAsFlow()

  private var _trackAdsResponse : Channel<NetworkResult<TrackAdsResponse>> = Channel<NetworkResult<TrackAdsResponse>>()
    val trackAdsResponse = _trackAdsResponse.receiveAsFlow()

    var currentPage : Int = 1
    var totalCount : Int = 20

    lateinit var allAdsPaginator : DefaultPaginator<Int, AllAdsResponse>


    init {

      viewModelScope.launch {
        Log.d("fvfkvfn","init")
            allAdsPaginator = DefaultPaginator<Int, AllAdsResponse>(
                onLoadUpdated = {isLoading, isSearch ->
                    if(isLoading){
                        _allAdsResponse.send(NetworkResult.Loading())
                    }
                },
                onSuccess = {item, newKey, isSearch ->
                    totalCount = item.data?.total_count ?: 0
                    _allAdsResponse.send(NetworkResult.Success(data = item))
                },
                onError = {err ->
                    _allAdsResponse.send(NetworkResult.Error(error = err))
                }
            )
          getAllAds(page = currentPage.toString(), adType = "")

      }


    }


      @SuppressLint("SuspiciousIndentation")
      suspend fun getAllAds(page : String? = "1", limit : String? = null, adType : String?){
          Log.d("fkvmvkfmvf","getAllAds")

        val isPaginating = page != "1"
             allAdsPaginator.loadNextItems(
                 pageNo = page?.toInt() ?: 1,
                 isSearch = false,
                 isPaginating = isPaginating,
                 onRequest = {
                     Log.d("fkvmvkfmvf","onRequest")
                   val response =  adsAllUseCases.getAllAdsUsecase(page = page.toString(), adType = "")
                     response
                 })

    }


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

}