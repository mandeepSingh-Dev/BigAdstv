package com.appsinvo.bigadstv.data.remote.remoteRepositories

import android.content.Context
import android.content.Intent
import com.appsinvo.bigadstv.data.remote.apiServices.CommonServices
import com.appsinvo.bigadstv.data.remote.model.ads.getAllAds.response.AllAdsResponse
import com.appsinvo.bigadstv.data.remote.model.common.error.apiResponse1.ApiErrorResponse
import com.appsinvo.bigadstv.data.remote.model.common.notifications.NotificationResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.data.remote.networkUtils.handleUseCaseException
import com.appsinvo.bigadstv.domain.data.repositories.CommonRepository
import com.appsinvo.bigadstv.presentation.ui.activities.MainActivity
import com.appsinvo.bigadstv.utils.Constants
import com.appsinvo.bigadstv.utils.GsonHelper.Companion.fromJson
import dagger.hilt.android.qualifiers.ApplicationContext
import java.net.HttpURLConnection
import javax.inject.Inject

class CommonRepositoryImpl @Inject constructor(@ApplicationContext val context: Context, private val commonServices: CommonServices) :
    CommonRepository {

    override suspend fun getNotifications(): NetworkResult<NotificationResponse> {
            return try {
                val response = commonServices.getNotifications()
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
                NetworkResult.Error(handleUseCaseException(exception = e))
            }
        }

}