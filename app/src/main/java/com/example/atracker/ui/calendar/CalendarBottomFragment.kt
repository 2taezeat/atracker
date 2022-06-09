package com.example.atracker.ui.calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.atracker.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


/**
 * A simple [Fragment] subclass.
 * Use the [CalendarBottomFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalendarBottomFragment : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar_bottom, container, false)
    }

    companion object {



        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CalendarBottomFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}