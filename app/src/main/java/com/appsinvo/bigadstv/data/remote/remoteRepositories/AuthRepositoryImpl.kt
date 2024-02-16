package com.appsinvo.bigadstv.data.remote.remoteRepositories

import android.util.Log
import com.appsinvo.bigadstv.data.local.datastore.AppDatastore
import com.appsinvo.bigadstv.data.local.datastore.ConstantsDatastore
import com.appsinvo.bigadstv.data.remote.apiServices.AuthService
import com.appsinvo.bigadstv.data.remote.model.auth.login.requestBody.LoginRequestBody
import com.appsinvo.bigadstv.data.remote.model.auth.login.response.LoginResponse
import com.appsinvo.bigadstv.data.remote.model.auth.login.response.LogoutResponse
import com.appsinvo.bigadstv.data.remote.model.error.apiResponse1.ApiErrorResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.data.remote.networkUtils.handleUseCaseException
import com.appsinvo.bigadstv.domain.repositories.AuthRepository
import com.appsinvo.bigadstv.utils.GsonHelper
import com.appsinvo.bigadstv.utils.GsonHelper.Companion.fromJson
import com.google.gson.GsonBuilder
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authService: AuthService, private val appDataStore : AppDatastore) : AuthRepository{
    override suspend fun login(loginRequestBody: LoginRequestBody): NetworkResult<LoginResponse> {
      return try{

            val response = authService.login(loginRequestBody = loginRequestBody)
            if(response.isSuccessful){

                val loginResponse = response.body()

                try {
                    appDataStore.write(ConstantsDatastore.USER_DATA, GsonHelper.toJson(loginResponse?.data))
                    NetworkResult.Success(data = loginResponse)
                }catch (e:Exception){
                    NetworkResult.Error(error = handleUseCaseException(exception = e))
                }
            }
            else{
                val errorBody = response.errorBody()?.string()?.fromJson<ApiErrorResponse>()

                if(response.code() == 400 && errorBody?.status_code == 0){
                    NetworkResult.Error(handleUseCaseException(java.lang.Exception(errorBody.message)))
                }else{
                    val networkException = retrofit2.HttpException(response)
                    NetworkResult.Error(handleUseCaseException(networkException))
                }
            }

        }catch (e:Exception){
            Log.d("fvfjnvjfv",e.message.toString())
          NetworkResult.Error(handleUseCaseException(exception = e))
        }
    }
    override suspend fun logout(): NetworkResult<LogoutResponse> {
      return try{

            val response = authService.logout()
            if(response.isSuccessful){

                try {
                    appDataStore.clearAllData()
                    NetworkResult.Success(data = response.body())
                }catch (e:Exception){
                    NetworkResult.Error(error = handleUseCaseException(exception = e))
                }
            }
            else{
                val errorBody = response.errorBody()?.string()?.fromJson<ApiErrorResponse>()
                if(response.code() == 400 && errorBody?.status_code == 0){
                    NetworkResult.Error(handleUseCaseException(java.lang.Exception(errorBody.message)))
                }else{
                    val networkException = retrofit2.HttpException(response)
                    NetworkResult.Error(handleUseCaseException(networkException))
                }
            }

        }catch (e:Exception){
            Log.d("fvfjnvjfv",e.message.toString())
          NetworkResult.Error(handleUseCaseException(exception = e))
        }
    }


}