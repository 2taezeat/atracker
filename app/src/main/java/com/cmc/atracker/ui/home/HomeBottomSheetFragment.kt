package com.cmc.atracker.ui.home

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
import com.cmc.atracker.R
import com.cmc.atracker.databinding.FragmentHomeBottomSheetBinding
import com.cmc.atracker.ui.AlertDialogFragment
import com.cmc.atracker.ui.AlertDialogListener
import com.cmc.atracker.ui.MainActivity
import com.cmc.atracker.utils.AlertApiObject
import com.cmc.atracker.utils.AlertType
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class HomeBottomSheetFragment(progressIndex : Int, displayListPosition : Int) : BottomSheetDialogFragment() {
    private val homeViewModel: HomeViewModel by activityViewModels()
    private var _binding: FragmentHomeBottomSheetBinding? = null
    private val binding get() = _binding!!
    private var progressIndex: Int? = null
    private var displayListPosition: Int? = null
    private val parentActivity by lazy {
        activity as MainActivity
    }


    init {
        this.progressIndex = progressIndex
        this.displayListPosition = displayListPosition
    }

    lateinit var alertDialogFragment : AlertDialogFragment



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBottomSheetBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        alertDialogFragment = AlertDialogFragment.instance(
            object : AlertDialogListener {
                override fun onLeftClick() {
                }
                override fun onCenterClick() {
                }
                override fun onRightClick() {
                }
            },
            AlertType.TYPE14,
            null
        )


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
            homeViewModel.switchFlagNull(homeViewModel._addCalendarToAddFlag) // homeAdd position 편집시를 위한 초기화
            navController.navigate(action)
            parentActivity.mainBottomNavigationDisappear()
            dismiss()

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
                                    homeViewModel.getMyApplyPfratio()

                                    val navController = parentActivity.findNavController(R.id.navHostFragmentActivityMain)
                                    dismiss()
                                    navController.popBackStack()

                                    homeViewModel.switchFlagNull(homeViewModel._postApplyFlag)
                                }
                            })
                        }
                    }
                }

                override fun onRightClick() {
                    when (alertType) {
                        AlertType.TYPE12 -> {
                            val applyId = homeViewModel.homeDisplayArrayList.value!![displayListPosition!!].applyId
                            val deleteIds = arrayOf(applyId)
                            homeViewModel.deleteApply(deleteIds)
                            homeViewModel.deleteApplyFail.observe(viewLifecycleOwner, Observer {
                                Log.d("deleteApplyFail", "${it}")
                                it.getContentIfNotHandled()?.let { boolean ->
                                    if( boolean)
                                        parentActivity.showAlertInstance(AlertApiObject.alertDialogFragment)
                                    else
                                        showAlert(AlertType.TYPE13)
                                }
                            })
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