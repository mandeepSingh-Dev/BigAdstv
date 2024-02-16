package com.appsinvo.bigadstv.di

import com.appsinvo.bigadstv.data.remote.apiServices.AdsService
import com.appsinvo.bigadstv.data.remote.apiServices.AuthService
import com.appsinvo.bigadstv.data.remote.model.ads.getAllAds.response.AllAdsResponseData
import com.appsinvo.bigadstv.data.remote.remoteRepositories.AdsRepositoryImpl
import com.appsinvo.bigadstv.data.remote.remoteRepositories.AuthRepositoryImpl
import com.appsinvo.bigadstv.domain.repositories.AdsRepository
import com.appsinvo.bigadstv.domain.repositories.AuthRepository
import com.appsinvo.bigadstv.domain.useCases.ads.AdsAllUseCases
import com.appsinvo.bigadstv.domain.useCases.ads.GetAllAdsUsecase
import com.appsinvo.bigadstv.domain.useCases.ads.TrackAdUsecase
import com.appsinvo.bigadstv.domain.useCases.auth.AuthAllUseCases
import com.appsinvo.bigadstv.domain.useCases.auth.LoginUseCase
import com.appsinvo.bigadstv.domain.useCases.auth.LogoutUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
object ApiServicesModule {


    //Providing Services
    @Provides
    fun provideAuthService(retrofit: Retrofit) : AuthService = retrofit.create(AuthService::class.java)

    @Provides
    fun provideAdsService(retrofit: Retrofit) : AdsService = retrofit.create(AdsService::class.java)


    //Providing Repositories

    @Provides
    fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl) : AuthRepository = authRepositoryImpl

    @Provides
    fun provideAdsRepository(adsRepositoryImpl : AdsRepositoryImpl) : AdsRepository = adsRepositoryImpl


    //Providing UseCases
    @Provides
    fun provideAuthAllUsecases(loginUseCase: LoginUseCase, logoutUseCase: LogoutUseCase) = AuthAllUseCases(loginUseCase = loginUseCase, logoutUseCase = logoutUseCase)
    //Providing UseCases
    @Provides
    fun provideAllAdsUsecases(getAllAdsUsecase: GetAllAdsUsecase, trackAdsUsecase: TrackAdUsecase) : AdsAllUseCases = AdsAllUseCases(getAllAdsUsecase = getAllAdsUsecase, trackAdUsecase = trackAdsUsecase)

}