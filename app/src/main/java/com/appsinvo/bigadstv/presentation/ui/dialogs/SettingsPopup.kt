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


class SettingsPopup  {

    private var popupWindow : PopupWindow? = null

    @SuppressLint("UseCompatLoadingForDrawables", "ClickableViewAccessibility")
    fun  createDropDown(context: Context, onItemClick : (String) -> Unit): SettingsPopup {

        val settingsPopupLayoutBinding = SettingsPopupLayoutBinding.inflate(LayoutInflater.from(context))

        popupWindow = PopupWindow(settingsPopupLayoutBinding.root, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)

        settingsPopupLayoutBinding.aboutCitiBroadcast.setOnFocusChangeListener { v, hasFocus ->
            Log.d("Dvlvmkfvf",v.measuredState.toString())
            v.drawableState.forEach {
                Log.d("Fvlfmnvkfnvf",it.toString())
            }

        }
        settingsPopupLayoutBinding.aboutCitiBroadcast.setOnFocusChangeListener { v, hasFocus ->
            Log.d("Dvlvmkfvf",v.measuredState.toString())
            v.drawableState.forEach {
                Log.d("Fvlfmnvkfnvf",it.toString())
            }

        }
        settingsPopupLayoutBinding.aboutCitiBroadcast.setOnFocusChangeListener { v, hasFocus ->
            Log.d("Dvlvmkfvf",v.measuredState.toString())
            v.drawableState.forEach {
                Log.d("Fvlfmnvkfnvf",it.toString())
            }

        }

        settingsPopupLayoutBinding.update.setOnHoverListener { v, event ->

            Log.d("mvkmvf","update Hovered")
            return@setOnHoverListener true
        }
   settingsPopupLayoutBinding.logout.setOnHoverListener { v, event ->

            Log.d("mvkmvf","login Hovered")
            return@setOnHoverListener true
        }
   settingsPopupLayoutBinding.aboutCitiBroadcast.setOnHoverListener { v, event ->

            Log.d("mvkmvf","about Hovered")
            return@setOnHoverListener true
        }

        // Set background for touch outside to dismiss
//        popupWindow?.setBackgroundDrawable(context.resources.getDrawable(R.color.lightGrey,null))
        // Set animation if needed
        popupWindow?.animationStyle = android.R.style.Animation_Dialog



        settingsPopupLayoutBinding.logout.setOnClickListener {
            onItemClick(SettingsPopupItemAction.LOGOUT.toString())
            dismiss()

        }
        settingsPopupLayoutBinding.update.setOnClickListener {
            onItemClick(SettingsPopupItemAction.UPDATE.toString())
            dismiss()
        }
        settingsPopupLayoutBinding.aboutCitiBroadcast.setOnClickListener {
            onItemClick(SettingsPopupItemAction.ABOUT_CITI_PRIME_BROADCASTING.toString())
            dismiss()
        }

        settingsPopupLayoutBinding.aboutCitiBroadcast.setOnTouchListener { v, event ->

            Log.d("flvjfkvnf",event.action.toString())

            return@setOnTouchListener true
        }

        settingsPopupLayoutBinding.aboutCitiBroadcast.setOnHoverListener { v, event ->
            Log.d("flvjkfnvf",event.action.toString() + " about")
            return@setOnHoverListener true
        }
  settingsPopupLayoutBinding.update.setOnHoverListener { v, event ->
            Log.d("flvjkfnvf",event.action.toString() + " update")
            return@setOnHoverListener true
        }
  settingsPopupLayoutBinding.logout.setOnHoverListener { v, event ->
            Log.d("flvjkfnvf",event.action.toString() + " logout")
            return@setOnHoverListener true
        }



        return this
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
        UPDATE,
        LOGOUT
    }



}