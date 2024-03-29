package com.cmc.atracker.ui.calendar

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
import com.cmc.atracker.databinding.FragmentEventCreateBinding

class EventCreateFragment : DialogFragment() {


    private var _binding: FragmentEventCreateBinding? = null
    private val binding get() = _binding!!

    private val calendarViewModel: CalendarViewModel by activityViewModels()




    companion object{
        const val TAG = "EventCreateFragment"
    }
    private val lazyContext by lazy {
        requireContext()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEventCreateBinding.inflate(inflater, container, false)

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