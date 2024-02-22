package com.appsinvo.bigadstv.presentation.ui.fragments.HomeMainScreenFragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.appsinvo.bigadstv.R
import com.appsinvo.bigadstv.base.BaseFragment
import com.appsinvo.bigadstv.data.remote.model.ads.getAllAds.response.AdsData
import com.appsinvo.bigadstv.data.remote.model.ads.getAllAds.response.AllAdsResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.databinding.FragmentAdsBinding
import com.appsinvo.bigadstv.presentation.ui.adapters.AdsAdapter
import com.appsinvo.bigadstv.presentation.ui.dialogs.SettingsPopup
import com.appsinvo.bigadstv.presentation.ui.fragments.HomeMainFragmentDirections
import com.appsinvo.bigadstv.presentation.ui.viewmodels.AuthViewmodel
import com.appsinvo.bigadstv.presentation.ui.viewmodels.HomeViewmodel
import com.appsinvo.bigadstv.utils.ViewBottomScrollListener
import com.appsinvo.bigadstv.utils.inVisible
import com.appsinvo.bigadstv.utils.showSnackbar
import com.appsinvo.bigadstv.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@SuppressLint("ResourceType", "SuspiciousIndentation")
@AndroidEntryPoint
class AdsFragment : BaseFragment() {

    var _binding : FragmentAdsBinding? = null
    val binding : FragmentAdsBinding get() = _binding!!

    private val authViewmodel : AuthViewmodel by viewModels()
    private val homeViewmodel : HomeViewmodel by viewModels()

    private var navControllerParent : NavController? = null

    private val adsAdapter : AdsAdapter by lazy {
        AdsAdapter(
            onItemClick = {adsData ->

            val mlist = adsAdapter.currentList.toMutableList()

            val index = adsAdapter.currentList.indexOf(adsData)

            val modifiedList = mutableListOf<AdsData>()

            for(i in index until mlist.size){
                modifiedList.add(mlist[i])
            }
            for(i in 0 until index){
                modifiedList.add(mlist[i])
            }

            val action =
                HomeMainFragmentDirections.actionHomeMainFragmentToPlayerFragment(modifiedList.toTypedArray())
                navControllerParent?.navigate(action)

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstaUitlnceState: Bundle?,
    ): View? {
        _binding = FragmentAdsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navControllerParent =  Navigation.findNavController(requireActivity(), R.id.my_nav_host_fragment)

        lifecycleScope.launch {
            observeGetAllAdsApiResponse()
        }
        setUpAdsRecyclerView()
    }

    private fun setUpAdsRecyclerView(){
        binding.adsRecyclerView.adapter = adsAdapter

        binding.nestedScrollView.setOnScrollChangeListener(ViewBottomScrollListener{

            lifecycleScope.launch {
                if(adsAdapter.currentList.size < homeViewmodel.totalCount){
                    homeViewmodel.currentPage += 1
                    homeViewmodel.getAllAds(page = homeViewmodel.currentPage.toString(), adType = "")
                }
            }
        })
    }


    var firstAD : AdsData? = null
    var secondAD : AdsData? = null
    var thirdAD : AdsData? = null
    private suspend fun observeGetAllAdsApiResponse(){
        homeViewmodel.allAdsResponse.collect{networkResult ->
            when(networkResult){
                is NetworkResult.Loading -> {
                    if(homeViewmodel.currentPage == 1) {
                        showLoading()
                          if(adsAdapter.currentList.isEmpty()){
                              binding.shimmerLayout.startShimmer()
                          }else{
                              showLoading()
                          }
                    }
                    else {
                        if(adsAdapter.currentList.isNotEmpty()) {
                            binding.pagintationProgressBar.visible()
                        }
                    }
                }
                is NetworkResult.Success ->{
                    hideLoading()
                        binding.shimmerLayout.stopShimmer()
                        binding.shimmerLayout.inVisible()
                        setData(networkResult)
                }
                is NetworkResult.Error -> {
                    hideLoading()
                    binding.pagintationProgressBar.inVisible()
                    binding.root.showSnackbar(message = networkResult.error.toString())
                }
            }
        }
    }


    private fun setData(networkResult: NetworkResult.Success<AllAdsResponse>) {
        val page = networkResult.data?.data?.page
        val adsDataList =  networkResult.data?.data?.data?.toMutableList()

        //If page 1 then just submit the list else add list in adapter's current list.
        if(page == 1){
            val uList = adsDataList?.map {
                it.userName = networkResult.data.data.userName
                it.userLocation = networkResult.data.data.userAddr
                it.userImg = networkResult.data.data.userImg

                it
            }

            val newListt = uList?.toMutableList()
            uList?.forEach {
                newListt?.add(it)
            }



            val listSize = uList?.size ?: 0
            Log.d("fkbmbkfnvf",listSize.toString())
            if(listSize > 3)
            {
                firstAD = uList?.get(0)
                secondAD = uList?.get(1)
                thirdAD = uList?.get(2)
            }

            val newList =  uList?.mapIndexed { index, adsData ->
                if(index == 0 || index == 1 || index == 2){
                    null
                }else{
                    adsData
                }
            }

            val mList = newList?.toMutableList()
            mList?.removeIf { it == null }

            //If user update the list which means pageNo. is 1 then first pass null list to submitList so that list can be clear in adapter
            adsAdapter.submitList(null)
            adsAdapter.submitList(uList)

        }
        else{
            adsDataList?.let { addListToCurrentList(it) }
        }
    }
    private fun addListToCurrentList(adsDataList : List<AdsData>){
        var adapterList = adsAdapter.currentList.toMutableList()

        adapterList.addAll(adsDataList)

        //For now assigning custom field so that adapter can differentiate items and notify
         adapterList =  adapterList.mapIndexed { index, adsData ->
            adsData.category = index.toString()
          adsData
        }.toMutableList()

        adsAdapter.submitList(adapterList) {
            binding.pagintationProgressBar.inVisible()
            binding.nestedScrollView.post {
                 binding.nestedScrollView.smoothScrollTo(0,binding.nestedScrollView.scrollY + 50)
              }
        }
    }
}