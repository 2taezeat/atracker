package com.example.atracker.ui.home

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.atracker.databinding.FragmentEventCreateBinding
import com.example.atracker.databinding.FragmentHomeAddDateBinding
import com.example.atracker.ui.calendar.CalendarViewModel

class HomeAddDateFragment : DialogFragment() {


    private var _binding: FragmentHomeAddDateBinding? = null
    private val binding get() = _binding!!

    private val calendarViewModel: CalendarViewModel by activityViewModels()




    companion object{
        const val TAG = "HomeAddDateFragment"
    }
    private val lazyContext by lazy {
        requireContext()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeAddDateBinding.inflate(inflater, container, false)

        binding.calendarVM = calendarViewModel


        val root: View = binding.root
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        binding.eventCreateButton1.setOnClickListener {
            dismiss()
        }


        binding.eventCreateButton2.setOnClickListener {
            calendarViewModel.saveEvent()
            dismiss()
        }




        return root
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        calendarViewModel.clearEventText()
        calendarViewModel.initTimeDateCurrent()
    }


}