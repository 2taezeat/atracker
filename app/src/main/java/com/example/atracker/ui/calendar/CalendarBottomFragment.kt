package com.example.atracker.ui.calendar

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.activityViewModels
import com.example.atracker.R
import com.example.atracker.databinding.FragmentCalendarBottomBinding
import com.example.atracker.databinding.FragmentHomeDetailBinding
import com.example.atracker.model.dto.CalendarEvent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CalendarBottomFragment(calendarEvent: CalendarEvent) : BottomSheetDialogFragment() {

    private var calendarEvent: CalendarEvent? = null
    private val calendarViewModel: CalendarViewModel by activityViewModels()



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
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)


        binding.calendarBottomEventTitleET.setText(calendarEvent?.eventTitle)
        binding.calendarBottomLocationET.setText(calendarEvent?.eventLocation)
        binding.calendarBottomNoteET.setText(calendarEvent?.eventNote)

        val localDate = calendarEvent?.zonedDateTime?.toLocalDate()!!
        val localTime = calendarEvent?.zonedDateTime?.toLocalTime()!!



        binding.calendarBottomDatePicker.updateDate(localDate.year, localDate.monthValue - 1, localDate.dayOfMonth)
        binding.calendarBottomTimePicker.hour = localTime.hour
        binding.calendarBottomTimePicker.minute = localTime.minute



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



        binding.calendarBottomDeleteTV.setOnClickListener {
            calendarViewModel.deleteEvent(calendarEvent!!)
            dismiss()
        }




        return binding.root
    }


    override fun getTheme(): Int = R.style.CustomBottomSheetDialog


}