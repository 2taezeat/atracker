package com.example.atracker.ui.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.atracker.R
import com.example.atracker.databinding.FragmentHomeBottomSheetBinding
import com.example.atracker.ui.AlertDialogFragment
import com.example.atracker.ui.AlertDialogListener
import com.example.atracker.ui.MainActivity
import com.example.atracker.utils.AlertType
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


        binding.homeBottomSheetProgressEditCL.setOnClickListener {
            val action = HomeDetailFragmentDirections.actionNavigationHomeDetailToNavigationHomeAdd(progressIndex!!)
            val navController = parentActivity.findNavController(R.id.navHostFragmentActivityMain)
            dismiss()
            navController.navigate(action)
            parentActivity.mainBottomNavigationDisappear()

        }


        binding.homeBottomSheetReviewDeleteCL.setOnClickListener {
            showAlert(AlertType.TYPE12)
        }



        return binding.root
    }


    private fun showAlert(alertType: AlertType){
        val alertDialogFragment = AlertDialogFragment.instance(
            object : AlertDialogListener {
                override fun onLeftClick() {
                }

                override fun onCenterClick() {
                    when (alertType) {
                        AlertType.TYPE13 -> {
                            homeViewModel.postApplyFlag.observe(viewLifecycleOwner, Observer {

                                if (it == true) {
                                    homeViewModel.getApplyDisplay(applyIds = null, includeContent = false)

                                    val navController = parentActivity.findNavController(R.id.navHostFragmentActivityMain)
                                    dismiss()
                                    navController.popBackStack()

                                    homeViewModel.switchFlagNull()
                                }
                            })
                        }
                    }
                }

                override fun onRightClick() {
                    when (alertType) {
                        AlertType.TYPE12 -> {
                            Log.d("asdasd", "${progressIndex}, ${homeViewModel.homeDisplayArrayList.value}")
                            val applyId = homeViewModel.homeDisplayArrayList.value!![progressIndex!!].applyId
                            val deleteIds = arrayOf(applyId)
                            homeViewModel.deleteApply(deleteIds)

                            showAlert(AlertType.TYPE13)
                        }
                    }

                }
            },
            alertType,
            null
        )

        alertDialogFragment.show(childFragmentManager, AlertDialogFragment.TAG)
    }



}