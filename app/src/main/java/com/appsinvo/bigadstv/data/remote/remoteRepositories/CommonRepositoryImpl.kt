package com.appsinvo.bigadstv.data.remote.remoteRepositories

import com.appsinvo.bigadstv.data.remote.apiServices.CommonServices
import com.appsinvo.bigadstv.data.remote.model.ads.getAllAds.response.AllAdsResponse
import com.appsinvo.bigadstv.data.remote.model.common.error.apiResponse1.ApiErrorResponse
import com.appsinvo.bigadstv.data.remote.model.common.notifications.NotificationResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.data.remote.networkUtils.handleUseCaseException
import com.appsinvo.bigadstv.domain.repositories.CommonRepository
import com.appsinvo.bigadstv.utils.GsonHelper.Companion.fromJson
import javax.inject.Inject

class CommonRepositoryImpl @Inject constructor(private val commonServices: CommonServices) : CommonRepository {

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
                        NetworkResult.Error(handleUseCaseException(networkException))
                    }
                }
            } catch (e: Exception) {
                NetworkResult.Error(handleUseCaseException(exception = e))
            }
        }

}