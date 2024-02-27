package com.appsinvo.bigadstv.data.remote.remoteRepositories

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import androidx.navigation.Navigation
import com.appsinvo.bigadstv.data.remote.apiServices.AdsService
import com.appsinvo.bigadstv.data.remote.model.ads.getAllAds.response.AllAdsResponse
import com.appsinvo.bigadstv.data.remote.model.ads.getUserEarnings.response.UserEarningResponse
import com.appsinvo.bigadstv.data.remote.model.ads.trackAds.requestBody.TrackAdsRequestBody
import com.appsinvo.bigadstv.data.remote.model.ads.trackAds.response.TrackAdsResponse
import com.appsinvo.bigadstv.data.remote.model.auth.login.response.LogoutResponse
import com.appsinvo.bigadstv.data.remote.model.common.error.apiResponse1.ApiErrorResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.data.remote.networkUtils.handleUseCaseException
import com.appsinvo.bigadstv.domain.data.repositories.AdsRepository
import com.appsinvo.bigadstv.presentation.ui.activities.MainActivity
import com.appsinvo.bigadstv.utils.Constants
import com.appsinvo.bigadstv.utils.GsonHelper.Companion.fromJson
import com.bumptech.glide.load.data.HttpUrlFetcher
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.http.HTTP
import java.net.HttpURLConnection
import javax.inject.Inject

class AdsRepositoryImpl @Inject constructor(@ApplicationContext private val context : Context, private val adsService: AdsService) : AdsRepository {

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

                    if(errorBody?.status_code ==0 && networkException.code() == HttpURLConnection.HTTP_FORBIDDEN)
                    {
                        //IF user access is denied then land app to the MainActivity(Login screen)
                        val intent = Intent(context, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra(Constants.IS_USER_ACCESS_DENIED_OR_TOKEN_EXPIRED,true)
                        context.startActivity(intent)

                        NetworkResult.Error(handleUseCaseException(Exception(errorBody.message)))
                    }else{
                        NetworkResult.Error(handleUseCaseException(networkException))
                    }

                }
            }
        } catch (e: Exception) {
            Log.d("kbmfkvmf",e.message.toString() + " kjnfjvfvv")
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

                    if(errorBody?.status_code ==0 && networkException.code() == HttpURLConnection.HTTP_FORBIDDEN)
                    {
                        //IF user access is denied then land app to the MainActivity(Login screen)
                        val intent = Intent(context, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra(Constants.IS_USER_ACCESS_DENIED_OR_TOKEN_EXPIRED,true)
                        context.startActivity(intent)
                        NetworkResult.Error(handleUseCaseException(Exception(errorBody.message)))
                    }else{
                        NetworkResult.Error(handleUseCaseException(networkException))
                    }

                }
            }
        } catch (e: Exception) {
            Log.d("kbmfkvmf",e.message.toString() + " kjnfjvfvv")
            NetworkResult.Error(handleUseCaseException(exception = e))
        }
    }

    override suspend fun getUserEarning(page: String?, limit: String?, month : Int?, ): NetworkResult<UserEarningResponse> {
        return try {
            val response = adsService.getUserEarnings(page = page,limit = limit,month = month)
            if (response.isSuccessful) {
                NetworkResult.Success(data = response.body())
            } else {
                val errorBody = response.errorBody()?.string()?.fromJson<ApiErrorResponse>()
                if (response.code() == 400 && errorBody?.status_code == 0) {
                    NetworkResult.Error(handleUseCaseException(java.lang.Exception(errorBody.message)))
                } else {
                    val networkException = retrofit2.HttpException(response)
                    Log.d("gbjkgnknmbg",(networkException.code() == HttpURLConnection.HTTP_FORBIDDEN).toString())

                    if(errorBody?.status_code ==0 && networkException.code() == HttpURLConnection.HTTP_FORBIDDEN)
                    {

                        //IF user access is denied then land app to the MainActivity(Login screen)
                        val intent = Intent(context, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra(Constants.IS_USER_ACCESS_DENIED_OR_TOKEN_EXPIRED,true)
                        context.startActivity(intent)

                        NetworkResult.Error(handleUseCaseException(Exception(errorBody.message)))
                    }else{
                        NetworkResult.Error(handleUseCaseException(networkException))
                    }

                }
            }
        } catch (e: Exception) {
            Log.d("kbmfkvmf",e.message.toString() + " kjnfjvfvv")
            NetworkResult.Error(handleUseCaseException(exception = e))
        }
    }


}