package com.appsinvo.bigadstv.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.appsinvo.bigadstv.R
import com.appsinvo.bigadstv.data.remote.model.ads.getAllAds.response.AdsData
import com.appsinvo.bigadstv.databinding.AdsLayoutItemBinding
import com.bumptech.glide.Glide

import com.bumptech.glide.request.RequestOptions


class AdsAdapter(val onItemClick : (AdsData) -> Unit) : ListAdapter<AdsData, AdsAdapter.AdsViewHolder>(DiffUtils) {

    inner class AdsViewHolder(val binding : AdsLayoutItemBinding) : ViewHolder(binding.root)
    {

        fun bind(adsData: AdsData)
        {
            binding.categoryTextView.text = (adsData.category ?: "").replaceFirstChar { it.uppercase() }


/*
            val thumb = (layoutPosition * 1000).toLong()
            val options = RequestOptions().frame(thumb)
            Glide.with(binding.root.context).load(adsData.filePath).apply(options).into(binding.adsImageView) */

            binding.adsImageView.loadThumbnailFromGlide(adsData.filePath)

            binding.playAdBtn.setOnClickListener {
                onItemClick(adsData)
            }
            binding.root.setOnClickListener {
                onItemClick(adsData)
            }

          /*   binding.root.setOnFocusChangeListener { v, hasFocus ->
                if(hasFocus){
                    val animationUtils = AnimationUtils.loadAnimation(binding.root.context,R.anim.scale_in_tv)
                    binding.root.startAnimation(animationUtils)
                    animationUtils.fillAfter = true
                }else{
                    val animationUtils = AnimationUtils.loadAnimation(binding.root.context,R.anim.scale_out_tv)
                    binding.root.startAnimation(animationUtils)
                    animationUtils.fillAfter = true
                }
            } */
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsViewHolder {
        val binding = AdsLayoutItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AdsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    private object DiffUtils : DiffUtil.ItemCallback<AdsData>(){
        override fun areContentsTheSame(oldItem: AdsData, newItem: AdsData): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: AdsData, newItem: AdsData): Boolean {
            return oldItem == newItem
        }
    }




}

fun ImageView.loadThumbnailFromGlide(url : String?){
    val thumb = 1000.toLong()
    val options = RequestOptions().frame(thumb).encodeQuality(20)
    Glide.with(this.context).load(url)/* .apply(options) */.placeholder(R.drawable.placeholder2).into(this)
}