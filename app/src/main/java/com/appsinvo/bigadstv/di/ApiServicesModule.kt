package com.appsinvo.bigadstv.di

import com.appsinvo.bigadstv.data.remote.apiServices.AdsService
import com.appsinvo.bigadstv.data.remote.apiServices.AuthService
import com.appsinvo.bigadstv.data.remote.apiServices.CommonServices
import com.appsinvo.bigadstv.data.remote.apiServices.RealWorldTimeApiService
import com.appsinvo.bigadstv.data.remote.model.ads.getAllAds.response.AllAdsResponseData
import com.appsinvo.bigadstv.data.remote.remoteRepositories.AdsRepositoryImpl
import com.appsinvo.bigadstv.data.remote.remoteRepositories.AuthRepositoryImpl
import com.appsinvo.bigadstv.data.remote.remoteRepositories.CommonRepositoryImpl
import com.appsinvo.bigadstv.data.remote.remoteRepositories.RealWorldDateTimeRepositoryImpl
import com.appsinvo.bigadstv.domain.repositories.AdsRepository
import com.appsinvo.bigadstv.domain.repositories.AuthRepository
import com.appsinvo.bigadstv.domain.repositories.CommonRepository
import com.appsinvo.bigadstv.domain.repositories.RealWorldDateTimeRepository
import com.appsinvo.bigadstv.domain.useCases.ads.AdsAllUseCases
import com.appsinvo.bigadstv.domain.useCases.ads.GetAllAdsUsecase
import com.appsinvo.bigadstv.domain.useCases.ads.TrackAdUsecase
import com.appsinvo.bigadstv.domain.useCases.auth.AuthAllUseCases
import com.appsinvo.bigadstv.domain.useCases.auth.LoginUseCase
import com.appsinvo.bigadstv.domain.useCases.auth.LogoutUseCase
import com.appsinvo.bigadstv.domain.useCases.common.AllCommonUseCases
import com.appsinvo.bigadstv.domain.useCases.common.GetNotificationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
object ApiServicesModule {


    //Providing Services
    @Provides
    fun provideAuthService(retrofit: Retrofit) : AuthService = retrofit.create(AuthService::class.java)

    @Provides
    fun provideAdsService(retrofit: Retrofit) : AdsService = retrofit.create(AdsService::class.java)

    @Provides
    fun provideCommonService(retrofit: Retrofit) : CommonServices = retrofit.create(CommonServices::class.java)

    @Provides
    fun provideRealTimeApiService(@Named(Qualifiers.REAL_WORLD_DATE_TIME_RETROFIT_INSTANCE) retrofit : Retrofit) : RealWorldTimeApiService = retrofit.create(RealWorldTimeApiService::class.java)


    //Providing Repositories

    @Provides
    fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl) : AuthRepository = authRepositoryImpl

    @Provides
    fun provideAdsRepository(adsRepositoryImpl : AdsRepositoryImpl) : AdsRepository = adsRepositoryImpl

    @Provides
    fun provideCommonRepository(commonRepositoryImpl: CommonRepositoryImpl) : CommonRepository = commonRepositoryImpl

    @Provides
    fun provideRealWorldDateTimeRepository(realWorldDateTimeRepositoryImpl: RealWorldDateTimeRepositoryImpl) : RealWorldDateTimeRepository = realWorldDateTimeRepositoryImpl


    //Providing UseCases
    @Provides
    fun provideAuthAllUsecases(loginUseCase: LoginUseCase, logoutUseCase: LogoutUseCase) = AuthAllUseCases(loginUseCase = loginUseCase, logoutUseCase = logoutUseCase)
    //Providing UseCases
    @Provides
    fun provideAllAdsUsecases(getAllAdsUsecase: GetAllAdsUsecase, trackAdsUsecase: TrackAdUsecase) : AdsAllUseCases = AdsAllUseCases(getAllAdsUsecase = getAllAdsUsecase, trackAdUsecase = trackAdsUsecase)

    @Provides
    fun provideAllCommonUsecase(getNotificationUseCase : GetNotificationUseCase) = AllCommonUseCases(getNotificationUseCase)
}