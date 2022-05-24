package com.example.atracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.atracker.databinding.FragmentAlertDialogBinding
import com.example.atracker.databinding.FragmentHomeDisplayBinding
import com.example.atracker.utils.AlertType

class AlertDialogFragment(private val alertDialogListener: AlertDialogListener, private val alertType : AlertType ) : DialogFragment() {

    companion object{
        const val TAG = "AlertDialogFragment"
        fun instance(alertDialogListener: AlertDialogListener, alertType : AlertType): AlertDialogFragment {
            return AlertDialogFragment(alertDialogListener, alertType)
        }
    }

    private var _binding: FragmentAlertDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlertDialogBinding.inflate(inflater, container, false)
        val root: View = binding.root


        when (alertType) {
            AlertType.TYPE1 -> {

            }

            AlertType.TYPE2 -> {

            }

            AlertType.TYPE3 -> {

            }
            AlertType.TYPE4 -> {

            }

        }


        return root
    }




}