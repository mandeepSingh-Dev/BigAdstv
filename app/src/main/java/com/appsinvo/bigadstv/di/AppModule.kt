package com.appsinvo.bigadstv.di

import android.content.Context
import androidx.room.Room
import com.appsinvo.bigadstv.data.local.database.Dao.AdsDao
import com.appsinvo.bigadstv.data.local.database.RoomDatabase.AppDatabase
import com.appsinvo.bigadstv.data.local.database.dbUtils.ConstantsDatabase
import com.appsinvo.bigadstv.data.local.database.repository.TrackAdsRepositoryImpl
import com.appsinvo.bigadstv.data.local.datastore.AppDatastore
import com.appsinvo.bigadstv.data.local.datastore.DatastoreManager
import com.appsinvo.bigadstv.domain.local.repositories.TracksAdsRepository
import com.appsinvo.bigadstv.domain.local.useCases.tracksAds.AllTrackAdsUsecases
import com.appsinvo.bigadstv.domain.local.useCases.tracksAds.DeleteAllUserTrackAdsUsecase
import com.appsinvo.bigadstv.domain.local.useCases.tracksAds.DeleteTrackAdUsecase
import com.appsinvo.bigadstv.domain.local.useCases.tracksAds.GetAllUserTrackAdsUsecase
import com.appsinvo.bigadstv.domain.local.useCases.tracksAds.GetUserTrackAdUsercase
import com.appsinvo.bigadstv.domain.local.useCases.tracksAds.InsertTrackAdUsecase
import com.appsinvo.bigadstv.presentation.ui.dialogs.ProgressDialog
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDataStore(dataStoreManager : DatastoreManager) : AppDatastore = dataStoreManager

    @Provides
    @Singleton
    fun provideRoomInstance(@ApplicationContext context: Context)  = Room.databaseBuilder(context,AppDatabase::class.java,ConstantsDatabase.APP_DATABASE_NAME)
        .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideAdsDao(appDatabase: AppDatabase) : AdsDao = appDatabase.getAdsDao()

    @Provides
    @Singleton
    fun provideTrackAdsRepository(trackAdsRepositoryImpl: TrackAdsRepositoryImpl) : TracksAdsRepository = trackAdsRepositoryImpl


    @Provides
    fun providesTrackAdsUsecases(
        deleteAllUserTrackAdsUsecase: DeleteAllUserTrackAdsUsecase,
        deleteTrackAdUsecase: DeleteTrackAdUsecase,
        getAllUserTrackAdsUsecase: GetAllUserTrackAdsUsecase,
        insertTrackAdUsecase: InsertTrackAdUsecase,
        getUserTrackAdUsercase: GetUserTrackAdUsercase,
    ) : AllTrackAdsUsecases = AllTrackAdsUsecases(deleteAllUserTrackAdsUsecase = deleteAllUserTrackAdsUsecase, deleteTrackAdUsecase = deleteTrackAdUsecase, getAllUserTrackAdsUsecase = getAllUserTrackAdsUsecase, insertTrackAdUsecase =  insertTrackAdUsecase, getUserTrackAdUsecase = getUserTrackAdUsercase)


    @Provides
    fun provideProgressDialog() = ProgressDialog()



}