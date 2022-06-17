package com.example.atracker.ui.calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.atracker.R
import com.example.atracker.databinding.FragmentCalendarBottomBinding
import com.example.atracker.databinding.FragmentHomeDetailBinding
import com.example.atracker.model.dto.CalendarEvent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CalendarBottomFragment(calendarEvent: CalendarEvent) : BottomSheetDialogFragment() {

    private var calendarEvent: CalendarEvent? = null


    init {
        this.calendarEvent = calendarEvent
    }

    companion object {
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            CalendarBottomFragment(calendarEvent).apply {
//                arguments = Bundle().apply {
//
//                }
//            }
    }



    private var _binding: FragmentCalendarBottomBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentCalendarBottomBinding.inflate(inflater, container, false)


        binding.calendarBottomEventTitleET.setText(calendarEvent?.eventTitle)
        binding.calendarBottomLocationET.setText(calendarEvent?.eventLocation)
        binding.calendarBottomNoteET.setText(calendarEvent?.eventNote)


        binding.calendarBottomEditTV.setOnClickListener {
            binding.calendarBottomSaveTV.visibility = View.VISIBLE
            it.visibility = View.INVISIBLE

            binding.calendarBottomEventTitleET.isFocusableInTouchMode = true
            binding.calendarBottomLocationET.isFocusableInTouchMode = true
            binding.calendarBottomNoteET.isFocusableInTouchMode = true

        }


        binding.calendarBottomSaveTV.setOnClickListener {
            binding.calendarBottomEditTV.visibility = View.VISIBLE
            it.visibility = View.INVISIBLE

            binding.calendarBottomEventTitleET.isFocusableInTouchMode = false
            binding.calendarBottomLocationET.isFocusableInTouchMode = false
            binding.calendarBottomNoteET.isFocusableInTouchMode = false

            binding.calendarBottomEventTitleET.focusable = View.NOT_FOCUSABLE
            binding.calendarBottomLocationET.focusable = View.NOT_FOCUSABLE
            binding.calendarBottomNoteET.focusable = View.NOT_FOCUSABLE


        }





        return binding.root
    }


    override fun getTheme(): Int = R.style.CustomBottomSheetDialog


}