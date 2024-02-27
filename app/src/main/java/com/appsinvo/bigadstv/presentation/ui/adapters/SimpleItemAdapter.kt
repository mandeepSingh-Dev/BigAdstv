package com.appsinvo.bigadstv.presentation.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.appsinvo.bigadstv.databinding.PopupItemBinding
import com.appsinvo.bigadstv.utils.inVisible
import com.appsinvo.bigadstv.utils.visible

class SimpleItemAdapter(val onClick : (String) -> Unit) : ListAdapter<String, SimpleItemAdapter.ItemViewholder>(DiffUtil) {

    inner class ItemViewholder(val binding : PopupItemBinding) : ViewHolder(binding.root){
        fun bind(month : String){
            binding.monthTextView.text = month

            binding.root.setOnClickListener {
                onClick(month)
            }

        /*     if(currentList.size-1 == absoluteAdapterPosition){
                binding.dividerView.inVisible()
            }else{
                binding.dividerView.visible()
            }*/
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        val binding = PopupItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewholder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    object DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<String>(){
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}