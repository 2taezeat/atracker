package com.example.atracker.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.atracker.R
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
        ), parent.context)
        context = parent.context

        return viewHolder

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as HomeDetailViewHolder
        val homeDetailItem = getItem(position) as HomeDetailItem
        holder.bind(homeDetailItem)
    }

    inner class HomeDetailViewHolder(
        val homeDetailItemBinding: HomeDetailItemBinding, val context : Context
    ) : RecyclerView.ViewHolder(homeDetailItemBinding.root) {




        fun bind(homeDetailItem: HomeDetailItem) {

            homeDetailItemBinding.homeDetailItemProgressType.text = homeDetailItem.progressName
            homeDetailItemBinding.homeDetailItemTotalReviewBody.text = homeDetailItem.totalReviewBody


            when (homeDetailItem.progressType) {
                0 -> {
                    homeDetailItemBinding.homeDetailItemCircleView.background = ContextCompat.getDrawable(context, R.drawable.circle_type_1)
                    homeDetailItemBinding.homeDetailItemVerticalView.background = ContextCompat.getDrawable(context, R.drawable.progress_type_1)
                }
                1 -> {
                    homeDetailItemBinding.homeDetailItemCircleView.background = ContextCompat.getDrawable(context, R.drawable.circle_type_2)
                    homeDetailItemBinding.homeDetailItemVerticalView.background = ContextCompat.getDrawable(context, R.drawable.progress_type_2)
                }
                2 -> {
                    homeDetailItemBinding.homeDetailItemCircleView.background = ContextCompat.getDrawable(context, R.drawable.circle_type_3)
                    homeDetailItemBinding.homeDetailItemVerticalView.background = ContextCompat.getDrawable(context, R.drawable.progress_type_3)
                }
                3 -> {
                    homeDetailItemBinding.homeDetailItemCircleView.background = ContextCompat.getDrawable(context, R.drawable.circle_type_4)
                    homeDetailItemBinding.homeDetailItemVerticalView.background = ContextCompat.getDrawable(context, R.drawable.progress_type_4)
                }
                4 -> {
                    homeDetailItemBinding.homeDetailItemCircleView.background = ContextCompat.getDrawable(context, R.drawable.circle_type_5)
                    homeDetailItemBinding.homeDetailItemVerticalView.background = ContextCompat.getDrawable(context, R.drawable.progress_type_5)
                }
                5 -> {
                    homeDetailItemBinding.homeDetailItemCircleView.background = ContextCompat.getDrawable(context, R.drawable.circle_type_6)
                    homeDetailItemBinding.homeDetailItemVerticalView.background = ContextCompat.getDrawable(context, R.drawable.progress_type_6)
                }
                6 -> {
                    homeDetailItemBinding.homeDetailItemCircleView.background = ContextCompat.getDrawable(context, R.drawable.circle_type_7)
                    homeDetailItemBinding.homeDetailItemVerticalView.background = ContextCompat.getDrawable(context, R.drawable.progress_type_7)
                }

            }




        }

    }
}