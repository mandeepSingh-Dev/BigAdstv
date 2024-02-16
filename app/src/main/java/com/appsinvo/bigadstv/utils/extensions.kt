package com.appsinvo.bigadstv.utils

import android.app.Dialog
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun View.showToast(message : String?, lenght : Int = Toast.LENGTH_SHORT){
    message?.let { Toast.makeText(this.context,message,lenght).show() }

}

fun View.showSnackbar(message : String?, length : Int = Snackbar.LENGTH_SHORT){
    val snackbar = message?.let { Snackbar.make(this, it,length) }
    snackbar?.animationMode = Snackbar.ANIMATION_MODE_SLIDE
    snackbar?.setTextMaxLines(10)
    snackbar?.show()
}

fun View.visibility(visibility : Boolean){
    this.visibility = if(visibility) View.VISIBLE else View.GONE
}

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.inVisible(){
    this.visibility = View.GONE
}

fun Dialog?.setWidthToMatchParent(){
    val params = this?.window?.attributes
    params?.width = WindowManager.LayoutParams.MATCH_PARENT
    this?.window?.attributes = params
}

fun Int.formatSecondsToHMS(): String {
    val seconds = this
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val secs = seconds % 60

    return String.format("%02d:%02d:%02d", hours, minutes, secs)
}

fun Long.formatMillisToDateTime(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
    return dateFormat.format(Date(this))
}

