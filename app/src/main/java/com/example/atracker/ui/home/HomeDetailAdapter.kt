package com.example.atracker.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.atracker.databinding.HomeDetailItemBinding
import com.example.atracker.model.dto.HomeDetailItem

class HomeDetailAdapter() :
    androidx.recyclerview.widget.ListAdapter<HomeDetailItem, RecyclerView.ViewHolder>(
        HomeDetailDiffCallback()) {

    var context: Context? = null


    class HomeDetailDiffCallback : DiffUtil.ItemCallback<HomeDetailItem>() {
        override fun areItemsTheSame(
            oldItem: HomeDetailItem,
            newItem: HomeDetailItem,
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(
            oldItem: HomeDetailItem,
            newItem: HomeDetailItem,
        ): Boolean {
            return oldItem == newItem
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeDetailAdapter.HomeDetailViewHolder {
        val viewHolder = HomeDetailViewHolder(HomeDetailItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
        context = parent.context

        return viewHolder

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as HomeDetailViewHolder
        val homeDetailItem = getItem(position) as HomeDetailItem
        holder.bind(homeDetailItem)
    }

    inner class HomeDetailViewHolder(
        val homeDetailItemBinding: HomeDetailItemBinding,
    ) : RecyclerView.ViewHolder(homeDetailItemBinding.root) {


        fun bind(homeDetailItem: HomeDetailItem) {

            homeDetailItemBinding.homeDetailItemProgressType.text = homeDetailItem.progressType
            homeDetailItemBinding.homeDetailItemTotalReviewBody.text = homeDetailItem.totalReviewBody

        }

    }
}