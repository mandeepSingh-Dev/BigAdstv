package com.appsinvo.bigadstv.presentation.ui.activities

import android.content.pm.PackageManager
import android.os.Bundle
import com.appsinvo.bigadstv.base.BaseActivity
import com.appsinvo.bigadstv.databinding.ActivityMainBinding
import com.appsinvo.bigadstv.presentation.ui.dialogs.ButtonsDialog

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    var binding : ActivityMainBinding? = null
    val _binding : ActivityMainBinding get() = binding!!

    private var buttonsDialog : ButtonsDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        //App restricted for every device except Android TV.
      //  restrictAppOnMobile()
    }

    private fun restrictAppOnMobile(){
        val isTV = packageManager.hasSystemFeature(PackageManager.FEATURE_LEANBACK)
        if(!isTV){
            showToast(message = "This app is intended to be used on Android TV devices.")
            finish()
        }
    }

/*     override fun onBackPressed() {

        buttonsDialog = ButtonsDialog().createShowDialog(this,
            onPosClick = {
                buttonsDialog?.dismissDialog()
                super.onBackPressed()
            },
            onNegClick = {
                buttonsDialog?.dismissDialog()
            }
        )
    } */
}