package com.appsinvo.bigadstv.presentation.ui.dialogs

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager.LayoutParams
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.appsinvo.bigadstv.R
import com.appsinvo.bigadstv.databinding.ButtonsDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ButtonsDialog {

        private var dialog : AlertDialog? = null

        fun createShowDialog(context : Context, onPosClick : () -> Unit, onNegClick : () -> Unit): ButtonsDialog {

            val binding = ButtonsDialogBinding.inflate(LayoutInflater.from(context))

             dialog = MaterialAlertDialogBuilder(context)
//                .setTitle(context.getString(R.string.app_name))
             //   .setMessage(context.getString(R.string.TXT_LBL_Are_you_sure_you_want_to_exit))
                 .setView(binding.root)
//                .setPositiveButton(context.getString(R.string.TXT_LBL_yes)){d,i -> onPosClick()}
//                .setNegativeButton(context.getString(R.string.TXT_LBL_no)){d,i -> onNegClick()}
//                .setCancelable(true)
           //      .setBackground(context.getDrawable(R.drawable.background))
                .create()
            dialog?.show()

            dialog?.setOnShowListener {
                dialog?.window?.setLayout(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT)
            }

            binding.yesButton.setOnClickListener {
                onPosClick()
            }
            binding.noButton.setOnClickListener {
                onNegClick()
            }




            return this
        }

        fun dismissDialog(){
            try {
                //dialog?.dismiss()
                dialog?.cancel()
                dialog = null
            }catch (e:Exception){
                Log.d("fvlmfkmvbf",e.message.toString())
            }
        }
}