package com.appsinvo.bigadstv.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.appsinvo.bigadstv.R
import com.appsinvo.bigadstv.base.BaseFragment
import com.appsinvo.bigadstv.databinding.FragmentMainCategoriesBinding
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
            findNavController().navigate(R.id.adsFragment)
        }
        binding.newsCategoryCardViewId.setOnClickListener {
            findNavController().navigate(R.id.adsFragment)
        }
        binding.reelsCategoryCardViewId.setOnClickListener {
            findNavController().navigate(R.id.adsFragment)
        }
        binding.popupsCategoryCardViewId.setOnClickListener {
            findNavController().navigate(R.id.adsFragment)
        }
    }
}