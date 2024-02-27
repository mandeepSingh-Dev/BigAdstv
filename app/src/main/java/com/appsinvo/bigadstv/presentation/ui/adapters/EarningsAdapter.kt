package com.appsinvo.bigadstv.presentation.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Constraints
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.appsinvo.bigadstv.R
import com.appsinvo.bigadstv.databinding.EarningsItemLayoutBinding
import com.appsinvo.bigadstv.databinding.NotificationItemBinding
import com.appsinvo.bigadstv.utils.inVisible
import com.appsinvo.bigadstv.utils.visible

class EarningsAdapter(val onClick : (String) -> Unit) : ListAdapter<String, EarningsAdapter.EarningsViewholder>(diffUtils) {


    inner class EarningsViewholder(val binding : EarningsItemLayoutBinding) : ViewHolder(binding.root){
        fun bind(item: String){
            binding.serialNumberValue.text = item

            binding.serialnumber.text = "S.No."
            binding.Days.text = "Days"
            binding.date.text = "Date"
            binding.totalTime.text = "Total Time"
            binding.Ads.text = "Ads"

            Log.d("flbmbknbg",item.toString())

       /*      if(absoluteAdapterPosition == 0){

                val lp = ConstraintLayout.LayoutParams(0,0)
                lp.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                lp.bottomToTop = binding.dividerView.id
                lp.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                lp.endToStart = binding.date.id

       val lp2 = ConstraintLayout.LayoutParams(0,0)
                lp2.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                lp2.bottomToTop = binding.dividerView.id
                lp2.leftToRight = binding.serialnumber.id
                lp2.rightToLeft = binding.Ads.id

       val lp3 = ConstraintLayout.LayoutParams(0,0)
                lp3.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                lp3.bottomToTop = binding.dividerView.id
                lp3.leftToRight = binding.date.id
                lp3.rightToLeft = binding.totalTime.id

       val lp4 = ConstraintLayout.LayoutParams(0,0)
                lp4.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                lp4.bottomToTop = binding.dividerView.id
                lp3.leftToRight = binding.Ads.id
                lp3.rightToLeft = binding.Days.id

                val lp5 = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,ConstraintLayout.LayoutParams.MATCH_CONSTRAINT)
                lp5.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                lp5.bottomToTop = binding.dividerView.id
                lp3.leftToRight = binding.totalTime.id
                lp3.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID

                binding.serialnumber.layoutParams =  lp
                binding.date.layoutParams =lp2
                 *//*    binding.Ads.layoutParams = lp3
                   binding.totalTime.layoutParams =  lp4
                   binding.Days.layoutParams = lp5 *//*
                binding.dividerView.visible()
            }
            else{
                binding.serialnumber.layoutParams.height = -3
                binding.Days.layoutParams.height = -3
                binding.date.layoutParams.height = -3
                binding.totalTime.layoutParams.height = -3
                binding.Ads.layoutParams.height = -3
                binding.dividerView.inVisible()
            } */

            binding.root.setOnClickListener {
                onClick(item)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EarningsViewholder {
        val binding = EarningsItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return EarningsViewholder(binding)
    }

    override fun onBindViewHolder(holder: EarningsViewholder, position: Int) {

        val item = getItem(position)
        holder.bind(item)
    }


    object diffUtils : DiffUtil.ItemCallback<String>(){
        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return  oldItem == newItem
        }
    }

}