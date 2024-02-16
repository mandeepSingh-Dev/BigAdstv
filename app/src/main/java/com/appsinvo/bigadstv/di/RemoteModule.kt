package com.appsinvo.bigadstv.di

import android.content.Context
import com.appsinvo.bigadstv.data.local.datastore.AppDatastore
import com.appsinvo.bigadstv.data.remote.networkUtils.ConstantsRemote
import com.appsinvo.bigadstv.data.remote.networkUtils.ConstantsRemote.READ_TIME
import com.appsinvo.bigadstv.data.remote.networkUtils.ConstantsRemote.WRITE_TIME
import com.appsinvo.bigadstv.data.remote.networkUtils.interceptors.AuthInterceptors
import com.appsinvo.bigadstv.data.remote.networkUtils.interceptors.ErrorInterceptors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    fun provideErrorInterceptors(@ApplicationContext context: Context) : ErrorInterceptors = ErrorInterceptors(context)

    @Provides
    fun provideAuthInterceptors(appDatastore: AppDatastore) : AuthInterceptors = AuthInterceptors(appDatastore = appDatastore)


    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, authorizationInterceptor: AuthInterceptors, errorInterceptors: ErrorInterceptors): OkHttpClient {
        return OkHttpClient.Builder()
            .writeTimeout(WRITE_TIME, TimeUnit.SECONDS)
            .readTimeout(READ_TIME, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authorizationInterceptor)
            .addInterceptor(errorInterceptors)
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit = Retrofit
        .Builder()
        .baseUrl(ConstantsRemote.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}