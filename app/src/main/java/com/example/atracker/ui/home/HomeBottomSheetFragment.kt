package com.example.atracker.ui.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.atracker.R
import com.example.atracker.databinding.FragmentCalendarBottomBinding
import com.example.atracker.databinding.FragmentHomeBottomSheetBinding
import com.example.atracker.ui.MainActivity
import com.example.atracker.ui.calendar.CalendarViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class HomeBottomSheetFragment(progressIndex : Int) : BottomSheetDialogFragment() {


    private val homeViewModel: HomeViewModel by activityViewModels()


    private var _binding: FragmentHomeBottomSheetBinding? = null
    private val binding get() = _binding!!
    private var progressIndex: Int? = null
    private val parentActivity by lazy {
        activity as MainActivity
    }


    init {
        this.progressIndex = progressIndex
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBottomSheetBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)


        binding.homeBottomSheetReviewEditCL.setOnClickListener {
            val action = HomeDetailFragmentDirections.actionNavigationHomeDetailToNavigationHomeWrite(progressIndex!!)
            val navController = parentActivity.findNavController(R.id.navHostFragmentActivityMain)
            dismiss()
            navController.navigate(action)
            parentActivity.mainBottomNavigationDisappear()
        }



        return binding.root
    }




}