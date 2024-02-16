package com.appsinvo.bigadstv.utils

import com.google.gson.Gson

class GsonHelper {

    companion object {
        inline fun <reified T> String.fromJson(): T = Gson().fromJson(this, T::class.java)

        fun <T> toJson(data: T): String = Gson().toJson(data)

    }
}