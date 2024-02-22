package com.appsinvo.bigadstv.presentation.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.appsinvo.bigadstv.R
import com.appsinvo.bigadstv.base.BaseFragment
import com.appsinvo.bigadstv.data.local.database.Dao.AdsDao
import com.appsinvo.bigadstv.databinding.FragmentSplashBinding
import com.appsinvo.bigadstv.presentation.ui.viewmodels.AuthViewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SplashFragment : BaseFragment() {

    var _binding : FragmentSplashBinding? = null
    val binding : FragmentSplashBinding get() = _binding!!

    val authViewmodel : AuthViewmodel by viewModels()

    @Inject
    lateinit var adsDao : AdsDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val logoUri = Uri.parse("android.resource://" + requireContext().packageName + "/" + R.raw.bigads_tv_animation_logo)

      binding.videoView.setVideoURI(logoUri)

        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(binding.videoView)
        mediaController.setMediaPlayer(binding.videoView)
        binding.videoView.start()
       binding.videoView.setOnCompletionListener {

           lifecycleScope.launch {
               val isLogin = authViewmodel.isLogin()
               if(isLogin){

//                   navigateToHomeFragment()
                   navigateToHomeMainScreenFragment()
               }else{
                  navigateToLoginFragment()
               }
           }
        }
    }

    private fun navigateToLoginFragment(){
        val loginFragmentDirections = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
        findNavController().navigate(loginFragmentDirections)
    }
    private fun navigateToHomeFragment(){
        val splashFragmentDirections = SplashFragmentDirections.actionSplashFragmentToHomeFragment2()
        findNavController().navigate(splashFragmentDirections)
    }
    private fun navigateToHomeMainScreenFragment(){

        val splashFragmentDirections = SplashFragmentDirections.actionSplashFragmentToHomeMainFragment()
        findNavController().navigate(splashFragmentDirections)
    }
}