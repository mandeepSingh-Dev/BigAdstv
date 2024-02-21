package com.appsinvo.bigadstv.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appsinvo.bigadstv.base.BaseFragment
import com.appsinvo.bigadstv.databinding.FragmentHomeMainScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeMainFragment : BaseFragment() {

    private var _binding : FragmentHomeMainScreenBinding? = null
    private val binding : FragmentHomeMainScreenBinding get() = _binding!!

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



    }

}