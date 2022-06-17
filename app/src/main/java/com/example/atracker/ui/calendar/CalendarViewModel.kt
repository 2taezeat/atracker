package com.example.atracker.ui.calendar

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.atracker.R
import com.example.atracker.model.dto.CalendarEvent
import java.time.LocalDate
import java.util.*

class CalendarViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text



    private val _events = MutableLiveData<MutableMap<LocalDate, List<CalendarEvent>>>()
    val events : LiveData<MutableMap<LocalDate, List<CalendarEvent>>> = _events



    val _eventTitle = MutableLiveData<String>()
    val eventTitle : LiveData<String> = _eventTitle


    val _eventDate = MutableLiveData<LocalDate>()
    val eventDate : LiveData<LocalDate> = _eventDate



    fun saveEvent() {

//        selectedDate?.let {
//            events[it] = events[it].orEmpty().plus(CalendarEvent(UUID.randomUUID().toString(), text, it))
//            updateAdapterForDate(it)
//        }


        Log.d("test123", "${_eventTitle.value}, ${_eventDate.value}")



    }





}