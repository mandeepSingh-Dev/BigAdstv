package com.appsinvo.bigadstv.base

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.appsinvo.bigadstv.presentation.ui.dialogs.ProgressDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var loadingDialog : ProgressDialog

    fun showLoading(message:String = "") {

        try {
            if (loadingDialog.isAdded) {
                loadingDialog.dismiss()
                loadingDialog.show(parentFragmentManager, "")
            } else {
                loadingDialog.show(parentFragmentManager, "")
            }
        }catch (e:Exception){
            Log.d("dvnkvd",e.message.toString())
        }

    }

    fun hideLoading(){

        // if(loadingDialog.isAdded) {
        try {

            loadingDialog.dismiss()
            // }else{

        }catch (e:Exception){
            //  showToastDebug("Loading Dialog is not attached or added in Fragment context (NOT INITIALIZED WITH FRAGMENT.")
        }
    }


    fun showToastDebug(message:String = ""){
       /*  if(BuildConfig.DEBUG && message.isNotEmpty()) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        } */
    }
    fun showToast(message:String = ""){
        if(message.isNotEmpty())
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        try {
            loadingDialog.dismiss()
        }catch (e:Exception){
            Log.d("dvkdmre33232vdvd",e.message.toString())
        }
    }


}