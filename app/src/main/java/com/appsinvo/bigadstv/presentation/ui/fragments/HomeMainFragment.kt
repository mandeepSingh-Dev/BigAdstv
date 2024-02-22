package com.appsinvo.bigadstv.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.appsinvo.bigadstv.R
import com.appsinvo.bigadstv.base.BaseFragment
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.databinding.FragmentHomeMainScreenBinding
import com.appsinvo.bigadstv.presentation.ui.dialogs.SettingsPopup
import com.appsinvo.bigadstv.presentation.ui.viewmodels.AuthViewmodel
import com.appsinvo.bigadstv.presentation.ui.viewmodels.HomeViewmodel
import dagger.hilt.android.AndroidEntryPoint
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

        settingsPopup = SettingsPopup().createDropDown(requireContext(), onItemClick = { action ->
            performSettingsWindowAction(action = action)
        })

        lifecycleScope.launch {
            observeLogoutApiResponse()
        }


        setOnClickListener()

    }



    private fun setOnClickListener(){
          binding.settingItem.setOnClickListener {
             //  binding.settingItem.requestChildFocus(settingsPopup.)
               settingsPopup?.showPopUp(binding.settingItem)
           }
           binding.notificationIcon.setOnClickListener {
               findNavController().navigate(R.id.notificationFragment)
           }
    }

    private fun performSettingsWindowAction(action : String){
        when(action){
            SettingsPopup.SettingsPopupItemAction.ABOUT_CITI_PRIME_BROADCASTING.toString() -> {}
            SettingsPopup.SettingsPopupItemAction.UPDATE.toString() -> {

                lifecycleScope.launch {
                    homeViewmodel.currentPage = 1
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



}