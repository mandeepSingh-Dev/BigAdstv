package com.appsinvo.bigadstv.data.remote.remoteRepositories

import android.util.Log
import com.appsinvo.bigadstv.data.remote.apiServices.AdsService
import com.appsinvo.bigadstv.data.remote.model.ads.getAllAds.response.AllAdsResponse
import com.appsinvo.bigadstv.data.remote.model.ads.trackAds.requestBody.TrackAdsRequestBody
import com.appsinvo.bigadstv.data.remote.model.ads.trackAds.response.TrackAdsResponse
import com.appsinvo.bigadstv.data.remote.model.auth.login.response.LogoutResponse
import com.appsinvo.bigadstv.data.remote.model.common.error.apiResponse1.ApiErrorResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.data.remote.networkUtils.handleUseCaseException
import com.appsinvo.bigadstv.domain.repositories.AdsRepository
import com.appsinvo.bigadstv.utils.GsonHelper.Companion.fromJson
import retrofit2.Response
import javax.inject.Inject

class AdsRepositoryImpl @Inject constructor(private val adsService: AdsService) : AdsRepository {

    override suspend fun getAllAds(page: String?, limit: String?, adType: String?, ): NetworkResult<AllAdsResponse> {
        return try {
            val response = adsService.getAllAds(page = page, limit = limit, adType = adType)
            if (response.isSuccessful) {
                NetworkResult.Success(data = response.body())
            } else {
                val errorBody = response.errorBody()?.string()?.fromJson<ApiErrorResponse>()
                if (response.code() == 400 && errorBody?.status_code == 0) {
                    NetworkResult.Error(handleUseCaseException(java.lang.Exception(errorBody.message)))
                } else {
                    val networkException = retrofit2.HttpException(response)
                    NetworkResult.Error(handleUseCaseException(networkException))
                }
            }
        } catch (e: Exception) {
            NetworkResult.Error(handleUseCaseException(exception = e))
        }
    }
  override suspend fun trackAd(trackAdsRequestBody: TrackAdsRequestBody ): NetworkResult<TrackAdsResponse> {
        return try {
            val response = adsService.trackAd(trackAdsRequestBody)
            if (response.isSuccessful) {
                NetworkResult.Success(data = response.body())
            } else {
                val errorBody = response.errorBody()?.string()?.fromJson<ApiErrorResponse>()
                if (response.code() == 400 && errorBody?.status_code == 0) {
                    NetworkResult.Error(handleUseCaseException(java.lang.Exception(errorBody.message)))
                } else {
                    val networkException = retrofit2.HttpException(response)
                    NetworkResult.Error(handleUseCaseException(networkException))
                }
            }
        } catch (e: Exception) {
            NetworkResult.Error(handleUseCaseException(exception = e))
        }
    }

}