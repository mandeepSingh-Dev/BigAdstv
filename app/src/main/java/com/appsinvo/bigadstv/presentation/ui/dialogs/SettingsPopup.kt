package com.appsinvo.bigadstv.presentation.ui.dialogs


import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.appcompat.R.*
import com.appsinvo.bigadstv.databinding.SettingsPopupLayoutBinding
import com.appsinvo.bigadstv.utils.inVisible
import com.appsinvo.bigadstv.utils.visible


class SettingsPopup  {

    private var popupWindow : PopupWindow? = null
    private var settingsPopupLayoutBinding : SettingsPopupLayoutBinding? = null

    @SuppressLint("UseCompatLoadingForDrawables", "ClickableViewAccessibility")
    fun  createDropDown(context: Context, onItemClick : (String) -> Unit): SettingsPopup {

         settingsPopupLayoutBinding = SettingsPopupLayoutBinding.inflate(LayoutInflater.from(context))

        popupWindow = PopupWindow(settingsPopupLayoutBinding?.root, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)



        // Set background for touch outside to dismiss
//        popupWindow?.setBackgroundDrawable(context.resources.getDrawable(R.color.lightGrey,null))
        // Set animation if needed
        popupWindow?.animationStyle = android.R.style.Animation_Dialog



        settingsPopupLayoutBinding?.logout?.setOnClickListener {
            onItemClick(SettingsPopupItemAction.LOGOUT.toString())
            dismiss()

        }
        settingsPopupLayoutBinding?.update?.setOnClickListener {
            onItemClick(SettingsPopupItemAction.UPDATE.toString())
            dismiss()
        }
        settingsPopupLayoutBinding?.aboutCitiBroadcast?.setOnClickListener {
            onItemClick(SettingsPopupItemAction.ABOUT_CITI_PRIME_BROADCASTING.toString())
            dismiss()
        }
        settingsPopupLayoutBinding?.myEarnings?.setOnClickListener {
            onItemClick(SettingsPopupItemAction.MY_EARNINGS.toString())
            dismiss()
        }



        return this
    }

    fun hideUpdateButton(){
        settingsPopupLayoutBinding?.update?.inVisible()
    }
    fun showUpdateButton(){
        settingsPopupLayoutBinding?.update?.visible()
    }



    fun showPopUp(anchorView: View){
        try {
            // popupWindow?.width = anchorView.width
            popupWindow?.showAsDropDown(anchorView)
        }catch (e:Exception){
            Log.d("DROPDOWN_ERROR",e.message.toString())
        }
    }

    fun isShowing() : Boolean?{
        return popupWindow?.isShowing
    }

    fun dismiss() {
        try {
            // popupWindow = null
            popupWindow?.dismiss()
        } catch (e: Exception) {
            Log.d("DROPDOWN_ERROR", e.message.toString())
        }
    }


    enum class SettingsPopupItemAction(){
        ABOUT_CITI_PRIME_BROADCASTING,
        MY_EARNINGS,
        UPDATE,
        LOGOUT
    }



}