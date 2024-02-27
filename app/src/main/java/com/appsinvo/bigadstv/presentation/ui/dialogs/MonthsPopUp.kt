package com.appsinvo.bigadstv.presentation.ui.dialogs

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.appsinvo.bigadstv.R
import com.appsinvo.bigadstv.databinding.PopUpLayoutBinding
import com.appsinvo.bigadstv.presentation.ui.adapters.SimpleItemAdapter

class MonthsPopUp  {

    private var popupWindow : PopupWindow? = null
    private var popUpLayoutBinding : PopUpLayoutBinding? = null

    @SuppressLint("UseCompatLoadingForDrawables", "ClickableViewAccessibility")
    fun  createDropDown(context: Context, onItemClick : (String) -> Unit): MonthsPopUp {

        popUpLayoutBinding = PopUpLayoutBinding.inflate(LayoutInflater.from(context))

        popupWindow = PopupWindow(popUpLayoutBinding?.root, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)
        // Set background for touch outside to dismiss
//        popupWindow?.setBackgroundDrawable(context.resources.getDrawable(R.color.lightGrey,null))
        // Set animation if needed
        popupWindow?.animationStyle = android.R.style.Animation_Dialog

        val months = context.resources.getStringArray(R.array.Months).toList()

        val adapter = SimpleItemAdapter(onClick = {month ->
            onItemClick(month)
        })
        popUpLayoutBinding?.itemsRecyclerView?.adapter = adapter

        adapter.submitList(months)


        popUpLayoutBinding?.root?.setOnClickListener {

         //   onItemClick(popUpLayoutBinding?.monthTextView?.text.toString())
        }
        return this
    }


    fun showPopUp(anchorView: View){
        try {
             popupWindow?.width = anchorView.width
            popupWindow?.showAsDropDown(anchorView,0,20)
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

}