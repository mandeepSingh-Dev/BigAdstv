package com.appsinvo.bigadstv.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appsinvo.bigadstv.base.BaseFragment
import com.appsinvo.bigadstv.databinding.FragmentHomeMainScreenBinding
import com.appsinvo.bigadstv.databinding.FragmentMyEarningsScreenBinding
import com.appsinvo.bigadstv.presentation.ui.adapters.EarningsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyEarningsFragment : BaseFragment() {

    private var _binding : FragmentMyEarningsScreenBinding? = null
    private val binding : FragmentMyEarningsScreenBinding get() = _binding!!

    private val earningsAdapter : EarningsAdapter by lazy {
        EarningsAdapter(){}
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMyEarningsScreenBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}