package com.example.atracker.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.atracker.databinding.HomeCompanySearchItemBinding
import com.example.atracker.utils.ProgressBarDrawable
import com.example.atracker.databinding.HomeProgressItemBinding
import com.example.atracker.model.dto.CompanySearchContent
import com.example.atracker.model.dto.HomeProgressItem

class HomeCompanySearchAdapter(homeCompanySearchOnclickListener: HomeCompanySearchOnclickListener) :
    androidx.recyclerview.widget.ListAdapter<CompanySearchContent, RecyclerView.ViewHolder>(
        HomeCompanySearchDiffCallback()) {

    var context: Context? = null


    class HomeCompanySearchDiffCallback : DiffUtil.ItemCallback<CompanySearchContent>() {
        override fun areItemsTheSame(
            oldItem: CompanySearchContent,
            newItem: CompanySearchContent,
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(
            oldItem: CompanySearchContent,
            newItem: CompanySearchContent,
        ): Boolean {
            return oldItem == newItem
        }
    }

    private var homeCompanySearchOnclickListener: HomeCompanySearchOnclickListener? = null

    init {
        this.homeCompanySearchOnclickListener = homeCompanySearchOnclickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCompanySearchViewHolder {
        val viewHolder = HomeCompanySearchViewHolder(HomeCompanySearchItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ), this.homeCompanySearchOnclickListener!!)
        context = parent.context

        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as HomeCompanySearchViewHolder
        val companySearchContent = getItem(position) as CompanySearchContent
        holder.bind(companySearchContent)
    }

    inner class HomeCompanySearchViewHolder(
        val homeCompanySearchItemBinding: HomeCompanySearchItemBinding, homeCompanySearchOnclickListener: HomeCompanySearchOnclickListener,
    ) : RecyclerView.ViewHolder(homeCompanySearchItemBinding.root), View.OnClickListener {
        private var homeCompanySearchOnclickListener: HomeCompanySearchOnclickListener? = null

        init {
            homeCompanySearchItemBinding.homeCompanySearchItemMainCL.setOnClickListener(this)
            this.homeCompanySearchOnclickListener = homeCompanySearchOnclickListener
        }


        fun bind(companySearchContent: CompanySearchContent) {
            homeCompanySearchItemBinding.itemCompanyTitleTV.text = companySearchContent.name
        }


        override fun onClick(view: View?) {
            this.homeCompanySearchOnclickListener?.onClickContainerView(view = view!!,
                position = bindingAdapterPosition,
                viewTag = currentList[bindingAdapterPosition].name)
        }
    }
}