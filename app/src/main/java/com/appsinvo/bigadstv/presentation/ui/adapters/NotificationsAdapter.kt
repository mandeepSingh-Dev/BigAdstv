package com.appsinvo.bigadstv.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.appsinvo.bigadstv.data.remote.model.common.notifications.NotificationData
import com.appsinvo.bigadstv.databinding.NotificationItemBinding

class NotificationsAdapter(val onClick : (NotificationData) -> Unit) : ListAdapter<NotificationData, NotificationsAdapter.NotificationViewholder>(diffUtils) {


    inner class NotificationViewholder(val binding : NotificationItemBinding) : ViewHolder(binding.root){
        fun bind(notificationData: NotificationData){
            binding.notificationTitle.text = notificationData.title
            binding.root.setOnClickListener {
                onClick(notificationData)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewholder {
        val binding = NotificationItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NotificationViewholder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewholder, position: Int) {

        val item = getItem(position)
        holder.bind(item)
    }


    object diffUtils : DiffUtil.ItemCallback<NotificationData>(){
        override fun areContentsTheSame(
            oldItem: NotificationData,
            newItem: NotificationData
        ): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(
            oldItem: NotificationData,
            newItem: NotificationData
        ): Boolean {
            return  oldItem == newItem
        }
    }

}