package com.appsinvo.bigadstv.data.local.datastore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface AppDatastore {


    suspend fun <T> write(key : Preferences.Key<T>, value : T)

    suspend fun <T> readValue(key : Preferences.Key<T>,defaultValue: T) : Flow<T>

    suspend fun clearAllData()



}