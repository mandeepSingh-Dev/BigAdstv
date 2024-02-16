package com.appsinvo.bigadstv.presentation.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.appsinvo.bigadstv.data.local.datastore.AppDatastore
import com.appsinvo.bigadstv.data.local.datastore.ConstantsDatastore
import com.appsinvo.bigadstv.data.remote.model.auth.login.requestBody.LoginRequestBody
import com.appsinvo.bigadstv.data.remote.model.auth.login.response.LoginResponse
import com.appsinvo.bigadstv.data.remote.model.auth.login.response.LoginResponseData
import com.appsinvo.bigadstv.data.remote.model.auth.login.response.LogoutResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.domain.useCases.auth.AuthAllUseCases
import com.appsinvo.bigadstv.utils.GsonHelper
import com.appsinvo.bigadstv.utils.GsonHelper.Companion.fromJson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewmodel @Inject constructor(val authAllUseCases: AuthAllUseCases, val appDataStore : AppDatastore): ViewModel() {

    private var _loginResponse : Channel<NetworkResult<LoginResponse>> = Channel<NetworkResult<LoginResponse>>()
    val loginResponse = _loginResponse.receiveAsFlow()

   private var _logoutResponse : Channel<NetworkResult<LogoutResponse>> = Channel<NetworkResult<LogoutResponse>>()
    val logoutResponse = _logoutResponse.receiveAsFlow()




    suspend fun isLogin() : Boolean{
        val userDetailsJson = appDataStore.readValue(ConstantsDatastore.USER_DATA,"").first()

        return userDetailsJson?.isNotEmpty() == true
    }

    suspend fun getUserDetails() : LoginResponseData?{
        return if(isLogin()) {
            val userDetailsJson = appDataStore.readValue(ConstantsDatastore.USER_DATA, "")?.first()
            userDetailsJson?.fromJson<LoginResponseData>()
        }else{
            null
        }
    }


    suspend fun login(loginRequestBody: LoginRequestBody){
        _loginResponse.send(NetworkResult.Loading())
        val response = authAllUseCases.loginUseCase(loginRequestBody = loginRequestBody)
        _loginResponse.send(response)
    }



    suspend fun logout(){
        _logoutResponse.send(NetworkResult.Loading())
        val response = authAllUseCases.logoutUseCase()
        _logoutResponse.send(response)
    }


}