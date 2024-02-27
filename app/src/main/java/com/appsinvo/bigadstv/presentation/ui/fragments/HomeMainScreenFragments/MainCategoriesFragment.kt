package com.appsinvo.bigadstv.presentation.ui.fragments.HomeMainScreenFragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.appsinvo.bigadstv.R
import com.appsinvo.bigadstv.base.BaseFragment
import com.appsinvo.bigadstv.databinding.FragmentMainCategoriesBinding
import com.appsinvo.bigadstv.utils.AdTypes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainCategoriesFragment : BaseFragment() {

    private var _binding : FragmentMainCategoriesBinding? = null
    private val binding : FragmentMainCategoriesBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainCategoriesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.adsCategoryCardViewId.setOnClickListener {
            val action = MainCategoriesFragmentDirections.actionMainCategoriesFragmentToAdsFragment(AdTypes.ADS)
            findNavController().navigate(action)
        }
        binding.newsCategoryCardViewId.setOnClickListener {
            val action = MainCategoriesFragmentDirections.actionMainCategoriesFragmentToAdsFragment(AdTypes.NEWS)
            findNavController().navigate(action)
        }
        binding.reelsCategoryCardViewId.setOnClickListener {
            val action = MainCategoriesFragmentDirections.actionMainCategoriesFragmentToAdsFragment(AdTypes.REELS)
            findNavController().navigate(action)
        }
        binding.popupsCategoryCardViewId.setOnClickListener {
            val action = MainCategoriesFragmentDirections.actionMainCategoriesFragmentToAdsFragment(AdTypes.POPUPS)
            findNavController().navigate(action)
             }


    }
}