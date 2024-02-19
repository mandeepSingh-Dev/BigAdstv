package com.appsinvo.bigadstv.utils

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ViewBottomScrollListener(val onBottom : () -> Unit) : View.OnScrollChangeListener{
    override fun onScrollChange(
        v: View?,
        scrollX: Int,
        scrollY: Int,
        oldScrollX: Int,
        oldScrollY: Int
    ) {
        Log.d("fkvmkfmvf",scrollY.toString())
        if((v?.canScrollVertically(RecyclerView.VERTICAL)) == false){
            onBottom()
        }
    }

}