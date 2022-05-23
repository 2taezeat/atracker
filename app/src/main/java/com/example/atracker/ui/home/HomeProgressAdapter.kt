package com.example.atracker.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.atracker.utils.ProgressBarDrawable
import com.example.atracker.databinding.HomeProgressItemBinding
import com.example.atracker.model.dto.HomeProgressItem

class HomeProgressAdapter(homeProgressOnclickListener: HomeProgressOnclickListener) :
    androidx.recyclerview.widget.ListAdapter<HomeProgressItem, RecyclerView.ViewHolder>(
        JobContentDiffCallback()) {

    var context: Context? = null


    class JobContentDiffCallback : DiffUtil.ItemCallback<HomeProgressItem>() {
        override fun areItemsTheSame(
            oldItem: HomeProgressItem,
            newItem: HomeProgressItem,
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(
            oldItem: HomeProgressItem,
            newItem: HomeProgressItem,
        ): Boolean {
            return oldItem == newItem
        }
    }

    private var homeProgressOnclickListener: HomeProgressOnclickListener? = null

    init {
        this.homeProgressOnclickListener = homeProgressOnclickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): homeProgressViewHolder {
        val viewHolder = homeProgressViewHolder(HomeProgressItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ), this.homeProgressOnclickListener!!)
        context = parent.context

        return viewHolder


//        return homeProgressViewHolder( HomeProgressItemBinding.inflate(
//            LayoutInflater.from(parent.context), parent, false
//            ), this.homeProgressOnclickListener!!
//        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as homeProgressViewHolder
        val homeProgressItem = getItem(position) as HomeProgressItem
        holder.bind(homeProgressItem)
    }

    inner class homeProgressViewHolder(
        val homeProgressItemBinding: HomeProgressItemBinding,
        homeProgressOnclickListener: HomeProgressOnclickListener,
    ) : RecyclerView.ViewHolder(homeProgressItemBinding.root), View.OnClickListener {
        private var homeProgressOnclickListener: HomeProgressOnclickListener? = null

        init {
            homeProgressItemBinding.homeProgressItemMainCL.setOnClickListener(this)
            this.homeProgressOnclickListener = homeProgressOnclickListener
        }


        fun bind(homeProgressItem: HomeProgressItem) {


            val bgProgress = ProgressBarDrawable(context = context!!,
                homeProgressItem.totalProgress,
                homeProgressItem.myProgress,
                homeProgressItem.success)

            homeProgressItemBinding.homeMainProgressBar.progressDrawable = bgProgress


            homeProgressItemBinding.homeProgressCompanyTitleTV.text = homeProgressItem.companyTitle
            homeProgressItemBinding.homeProgressjobTypeTV.text = homeProgressItem.jobType

        }


        override fun onClick(view: View?) {
            this.homeProgressOnclickListener?.onClickContainerView(view = view!!,
                position = bindingAdapterPosition,
                viewTag = "tmp")
        }
    }
}