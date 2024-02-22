package com.appsinvo.bigadstv.presentation.ui.fragments

import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.appsinvo.bigadstv.R
import com.appsinvo.bigadstv.base.BaseFragment
import com.appsinvo.bigadstv.data.remote.model.auth.login.requestBody.LoginRequestBody
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.databinding.FragmentLoginBinding
import com.appsinvo.bigadstv.presentation.ui.dialogs.ProgressDialog
import com.appsinvo.bigadstv.presentation.ui.viewmodels.AuthViewmodel
import com.appsinvo.bigadstv.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : BaseFragment() {


    private var _binding : FragmentLoginBinding? = null
    private val binding : FragmentLoginBinding get() = _binding!!

    private val authViewmodel : AuthViewmodel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListener()

        lifecycleScope.launch {
            observeLoginApiResponse()
        }

        binding.passwordTxtInputEdtTxt.endIconMode

     //   binding.loginButton.setBackgroundDrawable(resources.getDrawable(R.drawable.button_gradient,null))

    }

    private fun setClickListener(){
        binding.loginButton.setOnClickListener {
            lifecycleScope.launch {
                val email = binding.emailEdtTxt.text.toString().trim()
                val password = binding.passwordEdtTxt.text.toString().trim()
                val loginRequestBody = LoginRequestBody(email = email, password = password)

                authViewmodel.login(loginRequestBody = loginRequestBody)
            }
        }
    }


    private suspend fun observeLoginApiResponse(){
        authViewmodel.loginResponse.collect{networkResult ->
            when(networkResult){
                is NetworkResult.Loading -> {
                    showLoading()
                }
                is NetworkResult.Success -> {
                    hideLoading()
                    navigateToHomeMainScreenFragment()
                }
                is NetworkResult.Error -> {
                    hideLoading()
                    binding.root.showSnackbar(message = networkResult.error.toString())
                }
            }
        }
    }


    fun navigateToHomeScreen(){
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        findNavController().navigate(action)
    }
    private fun navigateToHomeMainScreenFragment(){

        val splashFragmentDirections = SplashFragmentDirections.actionSplashFragmentToHomeMainFragment()
        findNavController().navigate(splashFragmentDirections)
    }

}

