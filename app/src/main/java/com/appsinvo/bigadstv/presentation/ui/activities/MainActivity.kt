package com.appsinvo.bigadstv.presentation.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
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

        Log.d("gblgkbnkgb ",(254/resources.displayMetrics.density).toString())

    }

    override fun onBackPressed() {
        buttonsDialog = ButtonsDialog().createShowDialog(this,
            onPosClick = {
                buttonsDialog?.dismissDialog()
                super.onBackPressed()
            },
            onNegClick = {
                buttonsDialog?.dismissDialog()
            }
        )
    }
}