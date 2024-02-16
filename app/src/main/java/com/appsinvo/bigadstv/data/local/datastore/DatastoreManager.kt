package com.appsinvo.bigadstv.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.appsinvo.bigadstv.data.local.datastore.ConstantsDatastore.PREF_DATASTORE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

class DatastoreManager @Inject constructor(@ApplicationContext val context: Context) : AppDatastore{

    private val Context.dataStore by preferencesDataStore(PREF_DATASTORE_NAME)
    override suspend fun <T> write(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { pref ->
            pref[key] = value
        }
    }

    override suspend fun <T> readValue(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
       val result = context.dataStore.data.catch { exception ->
            if(exception is IOException) {
                emit(emptyPreferences())
            }
        }.map {
            it[key] ?: defaultValue
        }
        return result
    }

    override suspend fun clearAllData() {
        context.dataStore.edit { pref ->
            pref.clear()
        }
    }


}
