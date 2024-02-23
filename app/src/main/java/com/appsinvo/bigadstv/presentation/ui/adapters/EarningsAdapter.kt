package com.appsinvo.bigadstv.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.appsinvo.bigadstv.databinding.EarningsItemLayoutBinding
import com.appsinvo.bigadstv.databinding.NotificationItemBinding

class EarningsAdapter(val onClick : (String) -> Unit) : ListAdapter<String, EarningsAdapter.EarningsViewholder>(diffUtils) {


    inner class EarningsViewholder(val binding : EarningsItemLayoutBinding) : ViewHolder(binding.root){
        fun bind(item: String){
            binding.serialNumberValue.text = item
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