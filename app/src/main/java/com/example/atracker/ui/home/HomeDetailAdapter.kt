package com.example.atracker.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.atracker.R
import com.example.atracker.databinding.HomeDetailFreeItemBinding
import com.example.atracker.databinding.HomeDetailNotdefinedItemBinding
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
        context = parent.context

        when (viewType) {
            0 -> { // Review, OVERALL
                return HomeDetailReviewViewHolder(HomeDetailReviewItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), parent.context)
            }
            1 -> { // Qna
                return HomeDetailQnaViewHolder(HomeDetailQnaItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), parent.context)
            }
            2 -> { // Free, 2
                return HomeDetailFreeViewHolder(HomeDetailFreeItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), parent.context)
            }
            else -> { // 3, NOT_DEFINED
//                return HomeDetailFreeViewHolder(HomeDetailFreeItemBinding.inflate(
//                    LayoutInflater.from(parent.context), parent, false
//                ), parent.context)
                return HomeDetailNotDefinedViewHolder(HomeDetailNotdefinedItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), parent.context)

            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (currentList[position].itemType.ordinal) {
            0 -> { // Review, , OVERALL
                holder as HomeDetailReviewViewHolder
                val homeDetailItem = getItem(position) as HomeDetailItem
                holder.bind(homeDetailItem)
            }
            1 -> { // Qna
                holder as HomeDetailQnaViewHolder
                val homeDetailItem = getItem(position) as HomeDetailItem
                holder.bind(homeDetailItem)
            }
            2 -> { // Free
                holder as HomeDetailFreeViewHolder
                val homeDetailItem = getItem(position) as HomeDetailItem
                holder.bind(homeDetailItem)
            }
            3 -> { // NOT_DEFINED
                holder as HomeDetailNotDefinedViewHolder
                val homeDetailItem = getItem(position) as HomeDetailItem
                holder.bind(homeDetailItem)
            }
        }
    }

    inner class HomeDetailReviewViewHolder(
        val homeDetailReviewItemBinding: HomeDetailReviewItemBinding, val context: Context
    ) : RecyclerView.ViewHolder(homeDetailReviewItemBinding.root) {

        fun bind(homeDetailItem: HomeDetailItem) {
            if (bindingAdapterPosition < currentList.size - 1 && currentList[bindingAdapterPosition].progressName != currentList[bindingAdapterPosition + 1].progressName) {
                setBottomViewColor(homeDetailReviewItemBinding.homeDetailItemReviewBottomView, homeDetailItem, context)
            }

            if (bindingAdapterPosition == currentList.size - 1) {
                setBottomViewColor(homeDetailReviewItemBinding.homeDetailItemReviewBottomView, homeDetailItem, context)
            }


            homeDetailReviewItemBinding.homeDetailItemProgressType.text = homeDetailItem.progressName
            homeDetailReviewItemBinding.homeDetailItemTotalReviewBody.text = homeDetailItem.totalReviewBody

            when (homeDetailItem.progressType) {
                0 -> homeDetailReviewItemBinding.homeDetailItemCircleView.background = ContextCompat.getDrawable(context, R.drawable.circle_type_1)
                1 -> homeDetailReviewItemBinding.homeDetailItemCircleView.background = ContextCompat.getDrawable(context, R.drawable.circle_type_2)
                2 -> homeDetailReviewItemBinding.homeDetailItemCircleView.background = ContextCompat.getDrawable(context, R.drawable.circle_type_3)
                3 -> homeDetailReviewItemBinding.homeDetailItemCircleView.background = ContextCompat.getDrawable(context, R.drawable.circle_type_4)
                4 -> homeDetailReviewItemBinding.homeDetailItemCircleView.background = ContextCompat.getDrawable(context, R.drawable.circle_type_5)
                5 -> homeDetailReviewItemBinding.homeDetailItemCircleView.background = ContextCompat.getDrawable(context, R.drawable.circle_type_6)
                6 -> homeDetailReviewItemBinding.homeDetailItemCircleView.background = ContextCompat.getDrawable(context, R.drawable.circle_type_7)
            }
        }
    }


    inner class HomeDetailQnaViewHolder(
        val homeDetailQnaItemBinding: HomeDetailQnaItemBinding, val context: Context
    ) : RecyclerView.ViewHolder(homeDetailQnaItemBinding.root) {

        fun bind(homeDetailItem: HomeDetailItem) {
            homeDetailQnaItemBinding.homeDetailItemQuestion.text = homeDetailItem.questionBody
            homeDetailQnaItemBinding.homeDetailItemAnswer.text = homeDetailItem.answerBody
            homeDetailQnaItemBinding.homeDetailItemQnaReview.text = homeDetailItem.qnaReviewBody

            if (bindingAdapterPosition < currentList.size - 1 && currentList[bindingAdapterPosition].progressName != currentList[bindingAdapterPosition + 1].progressName) {
                setBottomViewColor(homeDetailQnaItemBinding.homeDetailItemQnaBottomView, homeDetailItem, context)
            }

            if (bindingAdapterPosition == currentList.size - 1) {
                setBottomViewColor(homeDetailQnaItemBinding.homeDetailItemQnaBottomView, homeDetailItem, context)
            }

        }
    }


    inner class HomeDetailFreeViewHolder(
        val homeDetailFreeItemBinding: HomeDetailFreeItemBinding, val context: Context
    ) : RecyclerView.ViewHolder(homeDetailFreeItemBinding.root) {


        fun bind(homeDetailItem: HomeDetailItem) {
            homeDetailFreeItemBinding.homeDetailItemFreeTitle.text = homeDetailItem.freeTitle
            homeDetailFreeItemBinding.homeDetailItemTotalFreeBody.text = homeDetailItem.freeBody

            if (bindingAdapterPosition < currentList.size - 1 && currentList[bindingAdapterPosition].progressName != currentList[bindingAdapterPosition + 1].progressName) {
                setBottomViewColor(homeDetailFreeItemBinding.homeDetailItemFreeBottomView, homeDetailItem, context)
            }

            if (bindingAdapterPosition == currentList.size - 1) {
                setBottomViewColor(homeDetailFreeItemBinding.homeDetailItemFreeBottomView, homeDetailItem, context)
            }
        }
    }

    inner class HomeDetailNotDefinedViewHolder(
        val homeDetailNotdefinedItemBinding: HomeDetailNotdefinedItemBinding, val context: Context
    ) : RecyclerView.ViewHolder(homeDetailNotdefinedItemBinding.root) {

        fun bind(homeDetailItem: HomeDetailItem) {

        }
    }


    fun setBottomViewColor(bottomView : View, homeDetailItem: HomeDetailItem, context: Context) {
        when (homeDetailItem.progressType) {
            0 -> bottomView.background = ContextCompat.getDrawable(context, R.drawable.progress_type_1)
            1 -> bottomView.background = ContextCompat.getDrawable(context, R.drawable.progress_type_2)
            2 -> bottomView.background = ContextCompat.getDrawable(context, R.drawable.progress_type_3)
            3 -> bottomView.background = ContextCompat.getDrawable(context, R.drawable.progress_type_4)
            4 -> bottomView.background = ContextCompat.getDrawable(context, R.drawable.progress_type_5)
            5 -> bottomView.background = ContextCompat.getDrawable(context, R.drawable.progress_type_6)
            6 -> bottomView.background = ContextCompat.getDrawable(context, R.drawable.progress_type_7)
        }
    }

}