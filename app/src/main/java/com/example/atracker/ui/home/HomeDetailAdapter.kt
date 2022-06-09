package com.example.atracker.ui.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.atracker.R
import com.example.atracker.databinding.HomeDetailQnaItemBinding
import com.example.atracker.databinding.HomeDetailReviewItemBinding
import com.example.atracker.model.dto.HomeDetailItem

class HomeDetailAdapter :
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

    override fun getItemViewType(position: Int): Int {
        //return super.getItemViewType(position)
        return currentList[position].itemType.ordinal
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("test12344", "${viewType}")

        context = parent.context

        when (viewType) {
            0 -> { // Qna
              return HomeDetailQnaViewHolder(HomeDetailQnaItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), parent.context)
            }
            1 -> { // Review
                return HomeDetailReviewViewHolder(HomeDetailReviewItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), parent.context)
            }
            else -> {
                return HomeDetailReviewViewHolder(HomeDetailReviewItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), parent.context)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (currentList[position].itemType.ordinal) {
            0 -> { // Qna
                holder as HomeDetailQnaViewHolder
                val homeDetailItem = getItem(position) as HomeDetailItem
                holder.bind(homeDetailItem)

            }
            1 -> { // Review
                holder as HomeDetailReviewViewHolder
                val homeDetailItem = getItem(position) as HomeDetailItem
                holder.bind(homeDetailItem)
            }
        }

//        holder as HomeDetailReviewViewHolder
//        val homeDetailItem = getItem(position) as HomeDetailItem
//        holder.bind(homeDetailItem)

    }

    inner class HomeDetailReviewViewHolder(
        val homeDetailReviewItemBinding: HomeDetailReviewItemBinding, val context : Context
    ) : RecyclerView.ViewHolder(homeDetailReviewItemBinding.root) {


        fun bind(homeDetailItem: HomeDetailItem) {
            homeDetailReviewItemBinding.homeDetailItemProgressType.text = homeDetailItem.progressName
            homeDetailReviewItemBinding.homeDetailItemTotalReviewBody.text = homeDetailItem.totalReviewBody

            when (homeDetailItem.progressType) {
                0 -> {
                    homeDetailReviewItemBinding.homeDetailItemCircleView.background = ContextCompat.getDrawable(context, R.drawable.circle_type_1)
                    homeDetailReviewItemBinding.homeDetailItemVerticalView.background = ContextCompat.getDrawable(context, R.drawable.progress_type_1)
                }
                1 -> {
                    homeDetailReviewItemBinding.homeDetailItemCircleView.background = ContextCompat.getDrawable(context, R.drawable.circle_type_2)
                    homeDetailReviewItemBinding.homeDetailItemVerticalView.background = ContextCompat.getDrawable(context, R.drawable.progress_type_2)
                }
                2 -> {
                    homeDetailReviewItemBinding.homeDetailItemCircleView.background = ContextCompat.getDrawable(context, R.drawable.circle_type_3)
                    homeDetailReviewItemBinding.homeDetailItemVerticalView.background = ContextCompat.getDrawable(context, R.drawable.progress_type_3)
                }
                3 -> {
                    homeDetailReviewItemBinding.homeDetailItemCircleView.background = ContextCompat.getDrawable(context, R.drawable.circle_type_4)
                    homeDetailReviewItemBinding.homeDetailItemVerticalView.background = ContextCompat.getDrawable(context, R.drawable.progress_type_4)
                }
                4 -> {
                    homeDetailReviewItemBinding.homeDetailItemCircleView.background = ContextCompat.getDrawable(context, R.drawable.circle_type_5)
                    homeDetailReviewItemBinding.homeDetailItemVerticalView.background = ContextCompat.getDrawable(context, R.drawable.progress_type_5)
                }
                5 -> {
                    homeDetailReviewItemBinding.homeDetailItemCircleView.background = ContextCompat.getDrawable(context, R.drawable.circle_type_6)
                    homeDetailReviewItemBinding.homeDetailItemVerticalView.background = ContextCompat.getDrawable(context, R.drawable.progress_type_6)
                }
                6 -> {
                    homeDetailReviewItemBinding.homeDetailItemCircleView.background = ContextCompat.getDrawable(context, R.drawable.circle_type_7)
                    homeDetailReviewItemBinding.homeDetailItemVerticalView.background = ContextCompat.getDrawable(context, R.drawable.progress_type_7)
                }
            }
        }
    }


    inner class HomeDetailQnaViewHolder(
        val homeDetailQnaItemBinding: HomeDetailQnaItemBinding, val context : Context
    ) : RecyclerView.ViewHolder(homeDetailQnaItemBinding.root) {


        fun bind(homeDetailItem: HomeDetailItem) {
            homeDetailQnaItemBinding.homeDetailItemQuestion.text = homeDetailItem.progressName
            homeDetailQnaItemBinding.homeDetailItemAnswer.text = homeDetailItem.totalReviewBody

            when (homeDetailItem.progressType) {
                0 -> {
                    homeDetailQnaItemBinding.homeDetailItemVerticalView.background = ContextCompat.getDrawable(context, R.drawable.progress_type_1)
                }
                1 -> {
                    homeDetailQnaItemBinding.homeDetailItemVerticalView.background = ContextCompat.getDrawable(context, R.drawable.progress_type_2)
                }
                2 -> {
                    homeDetailQnaItemBinding.homeDetailItemVerticalView.background = ContextCompat.getDrawable(context, R.drawable.progress_type_3)
                }
                3 -> {
                    homeDetailQnaItemBinding.homeDetailItemVerticalView.background = ContextCompat.getDrawable(context, R.drawable.progress_type_4)
                }
                4 -> {
                    homeDetailQnaItemBinding.homeDetailItemVerticalView.background = ContextCompat.getDrawable(context, R.drawable.progress_type_5)
                }
                5 -> {
                    homeDetailQnaItemBinding.homeDetailItemVerticalView.background = ContextCompat.getDrawable(context, R.drawable.progress_type_6)
                }
                6 -> {
                    homeDetailQnaItemBinding.homeDetailItemVerticalView.background = ContextCompat.getDrawable(context, R.drawable.progress_type_7)
                }
            }
        }
    }

}