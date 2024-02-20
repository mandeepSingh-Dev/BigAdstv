package com.appsinvo.bigadstv.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.leanback.widget.Presenter
import androidx.leanback.widget.PresenterSelector
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.appsinvo.bigadstv.R
import com.appsinvo.bigadstv.base.BaseFragment
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.databinding.FragmentNotificationBinding
import com.appsinvo.bigadstv.presentation.ui.adapters.NotificationsAdapter
import com.appsinvo.bigadstv.presentation.ui.viewmodels.NotificationViewmodel
import com.appsinvo.bigadstv.utils.inVisible
import com.appsinvo.bigadstv.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class NotificationFragment : BaseFragment() {

    private var _binding : FragmentNotificationBinding? = null
    private val binding : FragmentNotificationBinding get() = _binding!!

    private val notificationViewmodel : NotificationViewmodel by viewModels()

    private val notificationsAdapter : NotificationsAdapter by lazy {
        NotificationsAdapter(onClick = {})
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
        setUpRecyclerView()

        lifecycleScope.launch {
            observeGetNotificationApiResponse()
        }
    }

    private fun setClickListener(){
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setUpRecyclerView(){
        binding.notificationRecyclerView.adapter = notificationsAdapter
    }

    private suspend fun observeGetNotificationApiResponse(){
        notificationViewmodel.notificationResponse.collect{networkResult ->

            when(networkResult){
                is NetworkResult.Loading -> {
                    if(notificationsAdapter.currentList.isEmpty())
                    binding.shimmerLayout.startShimmer()
                    else
                        showLoading()
                }
                is NetworkResult.Success -> {
                    hideLoading()
                    notificationsAdapter.submitList(networkResult.data?.data){
                        binding.shimmerLayout.stopShimmer()
                        binding.shimmerLayout.inVisible()
                    }

                }
                is NetworkResult.Error -> {
                    hideLoading()
                    binding.root.showSnackbar(message = networkResult.error.toString())
                }
            }

        }
    }
}