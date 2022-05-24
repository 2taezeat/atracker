package com.example.atracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.atracker.databinding.FragmentHomeDisplayBinding

class AlertDialogFragment(alertDialogListener: AlertDialogListener) : DialogFragment() {

    companion object{
        const val TAG = "AlertDialogFragment"
        fun instance(alertDialogListener: AlertDialogListener, text: String): AlertDialogFragment {
            return AlertDialogFragment(alertDialogListener, text)
        }
    }


    private var _binding: FragmentHomeDisplayBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentHomeDisplayBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }




}