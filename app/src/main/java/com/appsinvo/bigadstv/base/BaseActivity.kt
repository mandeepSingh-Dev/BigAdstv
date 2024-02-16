package com.appsinvo.bigadstv.base

import android.graphics.Insets
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.appsinvo.bigadstv.presentation.ui.dialogs.ProgressDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {


    @Inject
    lateinit var loadingDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()
    }


    private fun setFullScreen(){


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        }

        //To disable display CutOuts
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }

        //To hide status and navigation bars.
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        window.decorView.setOnApplyWindowInsetsListener { view, windowInsets ->

            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())

            view.onApplyWindowInsets(windowInsets)
        }
    }

    fun showLoading() {

        if(loadingDialog.isAdded) {
            loadingDialog.dismiss()
            loadingDialog.show(supportFragmentManager,"")
        }else{
            loadingDialog.show(supportFragmentManager,"")
        }

    }

    fun hideLoading(){

        if(loadingDialog.isAdded) {
            loadingDialog.dismiss()
        }else{
            showToastDebug("Loading Dialog is not attached or added in Fragment context (NOT INITIALIZED WITH FRAGMENT.")
        }
    }


    fun showToastDebug(message:String = ""){
      /*   if(BuildConfig.DEBUG && message.isNotEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        } */
    }
    fun showToast(message:String = ""){
        if(message.isNotEmpty())
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}