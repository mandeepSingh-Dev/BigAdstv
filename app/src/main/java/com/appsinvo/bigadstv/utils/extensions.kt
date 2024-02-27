package com.appsinvo.bigadstv.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

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
fun Int.formatSecondsToHM(): String {
    val seconds = this
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val secs = seconds % 60

    return String.format("%01d:%02d", hours, minutes)
}

fun Long.formatMillisToDateTime(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
    return dateFormat.format(Date(this))
}

fun String.get_Formatted_UTC_Time(fromFormat : String = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX", toFormat : String = "HH:mm:ss"): String? {

    val isoFormat = SimpleDateFormat(fromFormat, Locale.getDefault())
    isoFormat.timeZone = TimeZone.getTimeZone("UTC")

   return try {
     /* source : "2024-02-19T17:10:27.492343+05:30"  EXAMPLE */

        val date = isoFormat.parse(this)
        val timeFormat = SimpleDateFormat(toFormat, Locale.getDefault())
        timeFormat.timeZone = TimeZone.getDefault() // Use local timezone
        timeFormat.format(date)
    } catch (e: Exception) {
        e.printStackTrace()
        null // Handle parsing error
    }
}




fun String.get_Date_Of_UTC_Time(format : String = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX"): Date? {

    val isoFormat = SimpleDateFormat(format, Locale.getDefault())
    isoFormat.timeZone = TimeZone.getTimeZone("UTC")
    return try {
        /* source : "2024-02-19T17:10:27.492343+05:30"  EXAMPLE */
        val date = isoFormat.parse(this)
        date
    } catch (e: Exception) {
        e.printStackTrace()
        null // Handle parsing error
    }
}

fun Date.getHourOfDay(): Int {
    val cal = Calendar.getInstance()
    cal.timeInMillis = this.time
    return  cal.get(Calendar.HOUR_OF_DAY)
}

fun Int.isBetweenRange(startHr : Int, endHr : Int): Boolean? {
    return try {
        val currentHour = this
         currentHour in startHr..endHr
    } catch (e: Exception) {
        null
    }
}

fun Float.toPx(context: Context): Float {
    return this * context.resources.displayMetrics.density
}
fun Float.toDp(context: Context): Float {
    return this / context.resources.displayMetrics.density
}

@SuppressLint("SimpleDateFormat")
fun String.changeDateFormat(): String {

    if(this.isNullOrEmpty()) return ""

    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    val time = simpleDateFormat.parse(this)

    val outputSDF = SimpleDateFormat("d MMM,YYYY")
    val format = outputSDF.format(time)
    Log.d("fblmkbng",format.toString())

    return format
}





