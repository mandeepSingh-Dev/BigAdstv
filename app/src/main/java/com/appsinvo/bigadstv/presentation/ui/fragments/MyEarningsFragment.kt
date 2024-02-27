package com.appsinvo.bigadstv.presentation.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.appsinvo.bigadstv.R
import com.appsinvo.bigadstv.base.BaseFragment
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.databinding.FragmentMyEarningsScreenBinding
import com.appsinvo.bigadstv.presentation.ui.adapters.EarningsAdapter
import com.appsinvo.bigadstv.presentation.ui.dialogs.MonthsPopUp
import com.appsinvo.bigadstv.presentation.ui.viewmodels.MyEarningsViewModel
import com.appsinvo.bigadstv.utils.changeDateFormat
import com.appsinvo.bigadstv.utils.formatSecondsToHM
import com.appsinvo.bigadstv.utils.showSnackbar
import com.appsinvo.bigadstv.utils.showToast
import com.appsinvo.bigadstv.utils.toPx
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class MyEarningsFragment : BaseFragment() {

    private var _binding : FragmentMyEarningsScreenBinding? = null
    private val binding : FragmentMyEarningsScreenBinding get() = _binding!!


    private val earningsViewModel : MyEarningsViewModel by viewModels()
    private var monthsPopup: MonthsPopUp? = null



    private val earningsAdapter : EarningsAdapter by lazy {
        EarningsAdapter(){}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.Main).launch {

            val month = Calendar.getInstance().get(Calendar.MONTH)
            earningsViewModel.getEarnings(month = month)

        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMyEarningsScreenBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


       // setUpRecyclerView()

        monthsPopup = MonthsPopUp().createDropDown(requireContext(), onItemClick = { month ->
            val months = resources.getStringArray(R.array.Months)
            val mIndex = months.indexOf(month)

            lifecycleScope.launch {
                val index = mIndex+1
                earningsViewModel.getEarnings(month = index)
            }
            monthsPopup?.dismiss()
        })

        setClickListeners()

        lifecycleScope.launch {
            observeGetEarningsReponse()
        }
    }

    private fun setClickListeners(){
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.monthLayout.setOnClickListener {
            monthsPopup?.showPopUp(binding.monthLayout)
        }
    }


    private fun createTableRow(date : String?, ad : Int?,totalTime : Int?,days : Int?, index : Int) : TableRow{
        val tableRow = TableRow(requireContext())
        val tableRowLayoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT)
        tableRow.layoutParams = tableRowLayoutParams


        tableRow.addView(createTextView(index.toString()))
        tableRow.addView(createTextView(date?.changeDateFormat().toString()))
        tableRow.addView(createTextView(ad.toString()))
        tableRow.addView(createTextView(totalTime?.formatSecondsToHM().toString()))
        tableRow.addView(createTextView(days.toString()))

        return tableRow
    }
    private fun createTextView(text : String): TextView {
        val tv = TextView(requireContext())
        val tvLP = TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT)
        tvLP.weight = 1f
        tvLP.gravity = Gravity.CENTER
        tv.gravity = Gravity.CENTER
        tv.layoutParams = tvLP
        tv.text = text
        tv.setPadding(12f.toPx(requireContext()).toInt(),0,0,0)


        return tv
    }

    private fun setUpRecyclerView(){
        binding.earningsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.earningsRecyclerView.adapter = earningsAdapter


        val list = mutableListOf("1","2","3","4","5","6","7","8","9","10")

        earningsAdapter.submitList(list)
    }


    @SuppressLint("SetTextI18n")
    private suspend fun observeGetEarningsReponse(){
        earningsViewModel.userEarningResponse.collect{networkResult ->
            when(networkResult){
                is NetworkResult.Loading -> {
                    showLoading()
                }
                is NetworkResult.Success -> {
                    hideLoading()

                   val earningsList = networkResult.data?.data?.result
                 val reversedList =   earningsList?.reversed()

                    reversedList?.forEachIndexed { index, result ->
                        val ad = result?.ads ?: 0
                        val date = result?.date ?: ""
                        val days = result?.days?: 0
                        val payout = result?.payout ?: 0
                        val totalTime = result?.total_time ?: 0

                        val rIndex = index + 1
                        binding.tableLayout.addView(createTableRow(date = date,ad = ad, totalTime =  totalTime, days = days, index = rIndex))
                    }

                    binding.totalPayout.text = "INR ${networkResult.data?.data?.payment}"


                }
                is NetworkResult.Error -> {
                    hideLoading()

                    binding.root.showSnackbar(message = networkResult.error.toString())
                    showToast(message = networkResult.error.toString())
                }
            }
        }
    }



}