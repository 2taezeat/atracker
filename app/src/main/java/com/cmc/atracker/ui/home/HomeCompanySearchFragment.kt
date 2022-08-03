package com.cmc.atracker.ui.home

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
import androidx.recyclerview.widget.RecyclerView
import com.cmc.atracker.R
import com.cmc.atracker.databinding.FragmentHomeCompanySearchBinding
import com.cmc.atracker.ui.MainActivity
import com.cmc.atracker.utils.AlertApiObject


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


    var clickNotDuplicatedFlag = true


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
            it.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager =
                        LinearLayoutManager::class.java.cast(recyclerView.layoutManager)
                    val totalItemCount = layoutManager.itemCount
                    val lastVisible = layoutManager.findLastCompletelyVisibleItemPosition()

                    if (lastVisible >= totalItemCount - 1 && !it.canScrollVertically(1)) {
                        Log.d("scrollBottom", "scrollBottom")

                        val companyResponse = homeViewModel.companyResponse.value

                        companyResponse?.let { value ->
                            if (value.has_next) {
                                homeViewModel.getCompanyInfo(searchWord = homeViewModel.companyWord.value!!, page = value.next_page_no + 1, size = 10, true)
                            }
                        }
                    }

                } }
            )
        }




        binding.homeCompanySearchCompanyET.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("onTextChanged", "${charSequence},${p1},${p2},${p3}")
                clickNotDuplicatedFlag = true

                if (charSequence.toString() != homeViewModel.companyWord.value) {
                    homeViewModel.clearCompanyValue()
                }

                if (charSequence.toString().isNotEmpty())
                    binding.homeCompanySearchCancelIV.visibility = View.VISIBLE
                else
                    binding.homeCompanySearchCancelIV.visibility = View.INVISIBLE
            }

            override fun afterTextChanged(editable: Editable?) {
                if (editable!!.length >= 2  && clickNotDuplicatedFlag) {
                    homeViewModel.getCompanyInfo(homeViewModel.companyWord.value!!, 1, 10, false)
                    clickNotDuplicatedFlag = false
                }
            }
        })

        binding.homeCompanySearchCancelIV.setOnClickListener {
            binding.homeCompanySearchCompanyET.setText("")
            homeViewModel.setClearCompanyList()
            homeViewModel.clearCompanyValue()
        }


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

            homeViewModel.addCompanyFail.observe(viewLifecycleOwner, Observer {
                Log.d("addCompanyFail", "${it}")
                it.getContentIfNotHandled()?.let { boolean ->
                    if( boolean) {
                        parentActivity.showAlertInstance(AlertApiObject.alertDialogFragment)
                    } else {
                        dismiss()
                    }
                }
            })
        }


        return binding.root
    }

    override fun onClickContainerView(view: View, position: Int, viewTag: String) {
        clickNotDuplicatedFlag = false
        homeViewModel.setCompanyNameID(position)
        binding.homeCompanySearchCompanyET.setText(viewTag)
    }

    override fun onDismiss(dialog: DialogInterface) {
        val homeAddFragment = requireParentFragment().childFragmentManager.fragments[0] as HomeAddFragment
        homeAddFragment.setViewGray()
        super.onDismiss(dialog)
    }



}