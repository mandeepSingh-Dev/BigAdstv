package com.appsinvo.bigadstv.di

import com.appsinvo.bigadstv.data.local.datastore.AppDatastore
import com.appsinvo.bigadstv.data.local.datastore.DatastoreManager
import com.appsinvo.bigadstv.presentation.ui.dialogs.ProgressDialog
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDataStore(dataStoreManager : DatastoreManager) : AppDatastore = dataStoreManager

    @Provides
    fun provideProgressDialog() = ProgressDialog()



}