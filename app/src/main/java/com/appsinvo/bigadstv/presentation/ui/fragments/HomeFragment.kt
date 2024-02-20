package com.appsinvo.bigadstv.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.appsinvo.bigadstv.R
import com.appsinvo.bigadstv.base.BaseFragment
import com.appsinvo.bigadstv.data.remote.model.ads.getAllAds.response.AdsData
import com.appsinvo.bigadstv.data.remote.model.ads.getAllAds.response.AllAdsResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.databinding.FragmentHomeBinding
import com.appsinvo.bigadstv.presentation.ui.adapters.AdsAdapter
import com.appsinvo.bigadstv.presentation.ui.adapters.loadThumbnailFromGlide
import com.appsinvo.bigadstv.presentation.ui.dialogs.SettingsPopup
import com.appsinvo.bigadstv.presentation.ui.viewmodels.AuthViewmodel
import com.appsinvo.bigadstv.presentation.ui.viewmodels.HomeViewmodel
import com.appsinvo.bigadstv.utils.ViewBottomScrollListener
import com.appsinvo.bigadstv.utils.inVisible
import com.appsinvo.bigadstv.utils.showSnackbar
import com.appsinvo.bigadstv.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    var _binding : FragmentHomeBinding? = null
    val binding : FragmentHomeBinding get() = _binding!!

    private val authViewmodel : AuthViewmodel by viewModels()
    private val homeViewmodel : HomeViewmodel by viewModels()

     private var settingsPopup: SettingsPopup? = null



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

            val action = HomeFragmentDirections.actionHomeFragmentToPlayerFragment(modifiedList.toTypedArray())
            findNavController().navigate(action)
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstaUitlnceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingsPopup = SettingsPopup().createDropDown(requireContext(), onItemClick = { action ->
            performSettingsWindowAction(action = action)
        })

        setClickListeners()


        if(firstAD != null){
            binding.firstAdTextView.text = (firstAD?.category ?: "").replaceFirstChar { it.uppercase() }
            binding.secondAdTextView.text = (secondAD?.category ?: "").replaceFirstChar { it.uppercase() }
            binding.thirdADTextView.text = (thirdAD?.category ?: "").replaceFirstChar { it.uppercase() }

            binding.firstAdImageview.loadThumbnailFromGlide(firstAD?.filePath)
            binding.secondAdImageview.loadThumbnailFromGlide(secondAD?.filePath)
            binding.thirdADImageview.loadThumbnailFromGlide(thirdAD?.filePath)
        }



        lifecycleScope.launch {
            observeLogoutApiResponse()
        }

        lifecycleScope.launch {
            observeGetAllAdsApiResponse()
        }

        setUpAdsRecyclerView()
    }

    private fun setClickListeners(){
        binding.settingItem.setOnClickListener {
          //  binding.settingItem.requestChildFocus(settingsPopup.)

            settingsPopup?.showPopUp(binding.settingItem)


        }
        binding.notificationIcon.setOnClickListener {
            findNavController().navigate(R.id.notificationFragment)
        }

        binding.firstAdPlayBtn.setOnClickListener {
            firstAD?.let {
                val mlist = adsAdapter.currentList.toMutableList()
                mlist.add(0,firstAD)
                mlist.add(1,secondAD)
                mlist.add(2,thirdAD)
                val action =   HomeFragmentDirections.actionHomeFragmentToPlayerFragment(mlist.toTypedArray())
                findNavController().navigate(action)
            }
        }

        binding.secondAdPlaybtn.setOnClickListener {
            secondAD?.let {

                val mlist = adsAdapter.currentList.toMutableList()
                mlist.add(0,secondAD)
                mlist.add(1,thirdAD)
                mlist.add(firstAD)

                val action =   HomeFragmentDirections.actionHomeFragmentToPlayerFragment(mlist.toTypedArray())
                findNavController().navigate(action)
            }
        }
        binding.thirdAdPlayBtn.setOnClickListener {
            thirdAD?.let {


                val mlist = adsAdapter.currentList.toMutableList()
                mlist.add(0,thirdAD)
                mlist.add(firstAD)
                mlist.add(secondAD)

                val action =   HomeFragmentDirections.actionHomeFragmentToPlayerFragment(mlist.toTypedArray())
                findNavController().navigate(action)
            }
        }

        binding.automobilesLinearLayout.setOnClickListener {
            firstAD?.let {

                val mlist = adsAdapter.currentList.toMutableList()
                mlist.add(0,firstAD)
                mlist.add(1,secondAD)
                mlist.add(2,thirdAD)

                val action =   HomeFragmentDirections.actionHomeFragmentToPlayerFragment(mlist.toTypedArray())
                findNavController().navigate(action)
            }
        }
        binding.accessoriesLinearLayout.setOnClickListener {
            secondAD?.let {
                val mlist = adsAdapter.currentList.toMutableList()
                mlist.add(0,secondAD)
                mlist.add(1,thirdAD)
                mlist.add(firstAD)


                val action =   HomeFragmentDirections.actionHomeFragmentToPlayerFragment(mlist.toTypedArray())
                findNavController().navigate(action)
            }
        }
        binding.reelsNewsPopupLinearLayout.setOnClickListener {
            thirdAD?.let {

                val mlist = adsAdapter.currentList.toMutableList()
                mlist.add(0,thirdAD)
                mlist.add(firstAD)
                mlist.add(secondAD)

                val action =   HomeFragmentDirections.actionHomeFragmentToPlayerFragment(mlist.toTypedArray())
                findNavController().navigate(action)
            }
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

    var firstAD : AdsData? = null
    var secondAD : AdsData? = null
    var thirdAD : AdsData? = null
    private suspend fun observeGetAllAdsApiResponse(){
        homeViewmodel.allAdsResponse.collect{networkResult ->
            when(networkResult){
                is NetworkResult.Loading -> {
                    if(homeViewmodel.currentPage == 1) {
                        if(adsAdapter.currentList.isEmpty()){
                            binding.shimmerLayout.startShimmer()
                        }else{
                            showLoading()
                        }
                    }
                    else {
                        binding.pagintationProgressBar.visible()
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

    private fun navigateToLoginFragment(){
        val navOptions= NavOptions.Builder().setPopUpTo(R.id.nav_graph,true).build()
        findNavController().navigate(R.id.loginFragment,null,navOptions)
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

            binding.firstAdTextView.text = (firstAD?.category ?: "").replaceFirstChar { it.uppercase() }
            binding.secondAdTextView.text = (secondAD?.category ?: "").replaceFirstChar { it.uppercase() }
            binding.thirdADTextView.text = (thirdAD?.category ?: "").replaceFirstChar { it.uppercase() }

            binding.firstAdImageview.loadThumbnailFromGlide(firstAD?.filePath)
            binding.secondAdImageview.loadThumbnailFromGlide(secondAD?.filePath)
            binding.thirdADImageview.loadThumbnailFromGlide(thirdAD?.filePath)

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