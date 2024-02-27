package com.appsinvo.bigadstv.data.remote.remoteRepositories

import android.content.Context
import android.content.Intent
import android.util.Log
import com.appsinvo.bigadstv.data.local.datastore.AppDatastore
import com.appsinvo.bigadstv.data.local.datastore.ConstantsDatastore
import com.appsinvo.bigadstv.data.remote.apiServices.AuthService
import com.appsinvo.bigadstv.data.remote.model.auth.login.requestBody.LoginRequestBody
import com.appsinvo.bigadstv.data.remote.model.auth.login.response.LoginResponse
import com.appsinvo.bigadstv.data.remote.model.auth.login.response.LogoutResponse
import com.appsinvo.bigadstv.data.remote.model.common.error.apiResponse1.ApiErrorResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.data.remote.networkUtils.handleUseCaseException
import com.appsinvo.bigadstv.domain.data.repositories.AuthRepository
import com.appsinvo.bigadstv.presentation.ui.activities.MainActivity
import com.appsinvo.bigadstv.utils.Constants
import com.appsinvo.bigadstv.utils.GsonHelper
import com.appsinvo.bigadstv.utils.GsonHelper.Companion.fromJson
import com.google.gson.GsonBuilder
import dagger.hilt.android.qualifiers.ApplicationContext
import java.net.HttpURLConnection
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(@ApplicationContext val context: Context, private val authService: AuthService, private val appDataStore : AppDatastore) :
    AuthRepository {
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

        }catch (e:Exception){
            Log.d("fvfjnvjfv",e.message.toString())
          NetworkResult.Error(handleUseCaseException(exception = e))
        }
    }


}