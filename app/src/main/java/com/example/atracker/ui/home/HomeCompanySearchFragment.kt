package com.example.atracker.ui.home

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.atracker.R
import com.example.atracker.databinding.FragmentHomeCompanySearchBinding
import com.example.atracker.ui.MainActivity


class HomeCompanySearchFragment : DialogFragment(),HomeCompanySearchOnclickListener {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private var _binding: FragmentHomeCompanySearchBinding? = null
    private val binding get() = _binding!!
    private val parentActivity by lazy {
        activity as MainActivity
    }
    private val lazyContext by lazy {
        requireContext()
    }

    private val homeCompanySearchAdapter by lazy {
        HomeCompanySearchAdapter(this)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeCompanySearchBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        binding.homeVM = homeViewModel

        binding.homeCompanySearchCompanyET.setOnFocusChangeListener { view, isFocuse ->
            if (isFocuse)
                binding.homeCompanySearchView1.background = ContextCompat.getDrawable(lazyContext, R.color.progress_color_7)
            else
                binding.homeCompanySearchView1.background = ContextCompat.getDrawable(lazyContext, R.color.atracker_gray_4)
        }


        binding.homeCompanySearchRV.also{
            it.layoutManager = LinearLayoutManager(parentActivity, LinearLayoutManager.VERTICAL, false)
            it.setHasFixedSize(false)
            it.adapter = homeCompanySearchAdapter
        }



        binding.homeCompanySearchCompanyET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("onTextChanged", "${charSequence},${p1},${p2},${p3}")
                if (charSequence!!.length >= 2) {
                    homeViewModel.getCompanyInfo(charSequence.toString())
                }

                if (charSequence.toString() != homeViewModel.companyWord.value) {
                    homeViewModel.clearCompanyValue()
                }

            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        homeViewModel.companyList.observe(viewLifecycleOwner, Observer {
            val companyNameList = homeViewModel.companyList.value
            Log.d("companyTitleList", "${it}")
            homeCompanySearchAdapter.submitList(companyNameList!!.toMutableList())
        })



        binding.homeCompanySearchButton1.setOnClickListener {
            dismiss()
        }

        binding.homeCompanySearchButton2.setOnClickListener {
            homeViewModel.addCompanyInfo()
            dismiss()
        }





        return binding.root
    }

    override fun onClickContainerView(view: View, position: Int, viewTag: String) {
        homeViewModel.setCompanyNameID(position)
        binding.homeCompanySearchCompanyET.setText(viewTag)
    }

    override fun onDismiss(dialog: DialogInterface) {
        val homeAddFragment = requireParentFragment().childFragmentManager.fragments[0] as HomeAddFragment
        homeAddFragment.setViewGray()
        super.onDismiss(dialog)
    }

}