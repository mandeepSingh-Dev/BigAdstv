package com.appsinvo.bigadstv.presentation.ui.viewmodels

import DefaultPaginator
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.appsinvo.bigadstv.data.local.database.Dao.AdsDao
import com.appsinvo.bigadstv.data.local.database.RoomDatabase.AppDatabase
import com.appsinvo.bigadstv.data.remote.model.ads.getAllAds.response.AllAdsResponse
import com.appsinvo.bigadstv.data.remote.model.ads.trackAds.requestBody.TrackAdsRequestBody
import com.appsinvo.bigadstv.data.remote.model.ads.trackAds.response.TrackAdsResponse
import com.appsinvo.bigadstv.data.remote.model.auth.login.response.LogoutResponse
import com.appsinvo.bigadstv.data.remote.model.realWorldDateTime.RealWorldDateTimeResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.domain.useCases.RealWorldDateTimeUseCase
import com.appsinvo.bigadstv.domain.useCases.ads.AdsAllUseCases
import com.appsinvo.bigadstv.utils.getHourOfDay
import com.appsinvo.bigadstv.utils.get_Date_Of_UTC_Time
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewmodel @Inject constructor(private val adsAllUseCases: AdsAllUseCases, private val realWorldDateTimeUseCase: RealWorldDateTimeUseCase, private val adsDao: AdsDao): ViewModel() {

    private var _allAdsResponse : Channel<NetworkResult<AllAdsResponse>> = Channel<NetworkResult<AllAdsResponse>>()
    val allAdsResponse = _allAdsResponse.receiveAsFlow()

  private var _trackAdsResponse : Channel<NetworkResult<TrackAdsResponse>> = Channel<NetworkResult<TrackAdsResponse>>()
    val trackAdsResponse = _trackAdsResponse.receiveAsFlow()

    var currentPage : Int = 1
    var totalCount : Int = 20


    init {

        viewModelScope.launch {
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

    private lateinit var allAdsPaginator : DefaultPaginator<Int, AllAdsResponse>

    suspend fun getAllAds(page : String? = "1" , limit : String? = null , adType : String?){

        val isPaginating = page != "1"
        allAdsPaginator.loadNextItems(pageNo = page?.toInt() ?: 1,isSearch = false, isPaginating = isPaginating, onRequest = {
            adsAllUseCases.getAllAdsUsecase(page = page.toString(), adType = "")
        })
    }


    suspend fun trackAds(trackAdsRequestBody: TrackAdsRequestBody){
      //  _trackAdsResponse.send(NetworkResult.Loading())

        adsDao.insertTrackAd(trackAdsRequestBody = trackAdsRequestBody)

        val resp = getCurrentWorldDateTime()
        if(resp is NetworkResult.Success){
            val currentHour = resp.data?.datetime?.get_Date_Of_UTC_Time()?.getHourOfDay() ?: 9

            Log.d("currentHour_LoggingStats",currentHour.toString())

            if(currentHour in 9..21){
                val response = adsAllUseCases.trackAdUsecase(trackAdsRequestBody)
            }
        }
      //  _trackAdsResponse.send(response)
    }

    suspend fun getCurrentWorldDateTime(): NetworkResult<RealWorldDateTimeResponse> {
       return realWorldDateTimeUseCase()
    }

}