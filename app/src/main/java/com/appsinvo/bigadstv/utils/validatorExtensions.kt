package com.appsinvo.bigadstv.utils

import android.content.Context
import android.util.Patterns
import com.appsinvo.bigadstv.R

fun String.isEmailValid(context : Context) : String? {

    return if( this == null || this == "null" || this.isNullOrEmpty()){
        context.getString(R.string.TXT_LBL_please_enter_email)
    }else{
        val isEmailCorrect = Patterns.EMAIL_ADDRESS.matcher(this).matches()
        if(!isEmailCorrect){
            context.getString(R.string.TXT_LBL_please_enter_valid_email)
        }else{
            null
        }

    }
}
