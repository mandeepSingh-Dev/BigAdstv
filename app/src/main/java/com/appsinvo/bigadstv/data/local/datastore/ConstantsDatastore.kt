package com.appsinvo.bigadstv.data.local.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object ConstantsDatastore {
    const val PREF_DATASTORE_NAME = "bigAds_pref_datastore"

    const val USER = "user"

    val USER_DATA = stringPreferencesKey(USER)
}