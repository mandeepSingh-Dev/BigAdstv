package com.appsinvo.bigadstv.data.remote.networkUtils.interceptors

import com.appsinvo.bigadstv.data.local.datastore.AppDatastore
import com.appsinvo.bigadstv.data.local.datastore.ConstantsDatastore
import com.appsinvo.bigadstv.data.remote.model.auth.login.response.LoginResponseData
import com.appsinvo.bigadstv.utils.GsonHelper.Companion.fromJson
import android.util.Log

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptors @Inject constructor(private val appDatastore: AppDatastore) : Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {

        val newRequest = chain.request().newBuilder()

        runBlocking{

            appDatastore.readValue(ConstantsDatastore.USER_DATA,"").first().let {
                val user = it.fromJson<LoginResponseData>()

                //If user token is not empty then we will paas token to header otherwise return from try block
                try {
                    val token = user.token
                    if (token?.isEmpty() == true || token?.isBlank() == true || token == null)
                        return@runBlocking

                    Log.d("okhttp", "TOKEN $token")

                    newRequest.addHeader("token", token)
                    //  newRequest.addHeader("Authorization", token.bearerToken())

                }catch (e:Exception){ }
            }
        }

        return chain.proceed(newRequest.build())
    }
}

