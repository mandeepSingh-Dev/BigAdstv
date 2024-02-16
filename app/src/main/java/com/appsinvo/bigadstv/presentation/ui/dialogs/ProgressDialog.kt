package com.appsinvo.bigadstv.presentation.ui.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.appsinvo.bigadstv.R
import com.appsinvo.bigadstv.databinding.ProgressBarLayoutBinding
import com.appsinvo.bigadstv.utils.setWidthToMatchParent
import com.appsinvo.bigadstv.utils.visibility

import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped

@AndroidEntryPoint
@ActivityScoped
class ProgressDialog : DialogFragment() {

    var _binding : ProgressBarLayoutBinding? = null
    val binding : ProgressBarLayoutBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ProgressBarLayoutBinding.inflate(layoutInflater)
        return binding.root
    }



    @SuppressLint("ResourceType")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog= super.onCreateDialog(savedInstanceState)

        dialog.setCancelable(false)
        return dialog
    }

    fun showMessageView(message:String){

        Log.d("dknvkvd",this.isAdded.toString())
        Log.d("dknvkvd",this.isResumed.toString())
        Log.d("dknvkvd",this.isVisible.toString())


        if(this.isResumed) {
            Log.d("dkndkvdf",this.isResumed.toString())
            binding.uploadingText.visibility(message.isNotEmpty())
            binding.uploadingText.text = message.toString()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            dialog?.window?.setBackgroundBlurRadius(80)
        }else{
            dialog?.window?.setDimAmount(0.4f)
        }

        dialog?.window?.decorView?.setBackgroundResource(R.drawable.dialog_margin_background)

        dialog?.setWidthToMatchParent()
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

    }

}