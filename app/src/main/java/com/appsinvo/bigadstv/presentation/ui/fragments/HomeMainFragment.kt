package com.appsinvo.bigadstv.presentation.ui.fragments

import android.content.ContentResolver.MimeTypeInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.appsinvo.bigadstv.R
import com.appsinvo.bigadstv.base.BaseFragment
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.databinding.FragmentHomeMainScreenBinding
import com.appsinvo.bigadstv.presentation.ui.dialogs.SettingsPopup
import com.appsinvo.bigadstv.presentation.ui.viewmodels.AuthViewmodel
import com.appsinvo.bigadstv.presentation.ui.viewmodels.HomeViewmodel
import com.appsinvo.bigadstv.utils.inVisible
import com.appsinvo.bigadstv.utils.showSnackbar
import com.appsinvo.bigadstv.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeMainFragment : BaseFragment() {

    private var _binding : FragmentHomeMainScreenBinding? = null
    private val binding : FragmentHomeMainScreenBinding get() = _binding!!

    private var settingsPopup: SettingsPopup? = null

    private val authViewmodel : AuthViewmodel by viewModels()

    private val homeViewmodel : HomeViewmodel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeMainScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Log.d("fblmfkvmf",homeViewmodel.toString())

        settingsPopup = SettingsPopup().createDropDown(requireContext(), onItemClick = { action ->
            performSettingsPopupWindowAction(action = action)
        })

        lifecycleScope.launch {
            observeLogoutApiResponse()
        }

      /*   lifecycleScope.launch {
            observeGetAllAdsApiResponse()
        }
         */
        val navController = Navigation.findNavController(requireActivity(), R.id.main_home_nav_host_fragment)
        navController.addOnDestinationChangedListener(onDestinationChangeListener)


        setOnClickListener()

        lifecycleScope.launch {

            delay(4000)
            homeViewmodel.currentPage = 2
            homeViewmodel.totalCount = 0

            Log.d("Fvlmfkmvf","delalllgffd")
            homeViewmodel.getAllAds(page = homeViewmodel.currentPage.toString(), adType = "")

        }

    }

    private suspend fun observeGetAllAdsApiResponse(){
        homeViewmodel.allAdsResponse.collect{networkResult ->
            when(networkResult){
                is NetworkResult.Loading -> {
                        showLoading()

                }
                is NetworkResult.Success ->{
                    hideLoading()

                }
                is NetworkResult.Error -> {
                    hideLoading()
                    binding.root.showSnackbar(message = networkResult.error.toString())
                }
            }
        }
    }


    private val onDestinationChangeListener = NavController.OnDestinationChangedListener { controller, destination, arguments ->
        if(destination.id == R.id.adsFragment){
            settingsPopup?.showUpdateButton()
        }else{
            settingsPopup?.hideUpdateButton()
        }
    }

    private fun setOnClickListener(){
          binding.settingItem.setOnClickListener {
              settingsPopup?.showPopUp(binding.settingItem)
           }
           binding.notificationIcon.setOnClickListener {
               navigateToNotificationFragment()
           }
    }

    var lifecycleJOB : Job? = null
    private fun performSettingsPopupWindowAction(action : String){
        when(action){
            SettingsPopup.SettingsPopupItemAction.ABOUT_CITI_PRIME_BROADCASTING.toString() -> {}
            SettingsPopup.SettingsPopupItemAction.MY_EARNINGS.toString() -> {

                navigateToMyEarningFragment()
            }
            SettingsPopup.SettingsPopupItemAction.UPDATE.toString() -> {

            CoroutineScope(Dispatchers.IO).launch {
                    homeViewmodel.currentPage = 2
                    homeViewmodel.totalCount = 0

                    homeViewmodel.getAllAds(page = homeViewmodel.currentPage.toString(), adType = "")
                }
            }
            SettingsPopup.SettingsPopupItemAction.LOGOUT.toString() -> {
                lifecycleScope.launch {
                    authViewmodel.logout()
                }
            }
        }
    }

    private suspend fun observeLogoutApiResponse(){
        authViewmodel.logoutResponse.collect{networkResult ->
            when(networkResult){
                is NetworkResult.Loading -> {
                    showLoading()
                }
                is NetworkResult.Success ->{
                    hideLoading()
                    navigateToLoginFragment()
                }

                is NetworkResult.Error -> {
                    hideLoading()
                }
            }
        }
    }

    private fun navigateToLoginFragment(){
        val navOptions= NavOptions.Builder().setPopUpTo(R.id.nav_graph,true).build()
        findNavController().navigate(R.id.loginFragment,null,navOptions)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("Dvvkfmvf","onDestroyView")
      //  findNavController().removeOnDestinationChangedListener(onDestinationChangeListener)
    }


    private fun navigateToMyEarningFragment(){
        findNavController().navigate(R.id.myEarningsFragment)

    }
   private fun navigateToNotificationFragment(){
       findNavController().navigate(R.id.notificationFragment)
    }


}