package com.example.atracker.ui.calendar

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.atracker.R
import com.example.atracker.model.dto.CalendarEvent
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
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


    var year: MutableLiveData<Int> = MutableLiveData(2022)
    var month: MutableLiveData<Int> = MutableLiveData(11)
    var day: MutableLiveData<Int> = MutableLiveData(12)

    var hour: MutableLiveData<Int> = MutableLiveData()
    var minute: MutableLiveData<Int> = MutableLiveData()



    var _zonedDateTime = MutableLiveData<ZonedDateTime>()
    var zonedDateTime : LiveData<ZonedDateTime> = _zonedDateTime

    init {
        _events.value = mutableMapOf<LocalDate, List<CalendarEvent>>()
    }




    fun saveEvent() {

//        selectedDate?.let {
//            events[it] = events[it].orEmpty().plus(CalendarEvent(UUID.randomUUID().toString(), text, it))
//            updateAdapterForDate(it)
//        }

        _events.value!![_zonedDateTime.value!!.toLocalDate()] = _events.value!![_zonedDateTime.value!!.toLocalDate()].orEmpty().plus(CalendarEvent(UUID.randomUUID().toString(), _eventTitle.value!!, _zonedDateTime.value!! ))

        Log.d("test123", "${_events.value}")

    }

    fun onDateChanged(year: Int, month: Int, day: Int){
        Log.v("Test_Picker", "$year $month $day")

    }


    fun onTimeChanged(hour: Int, minute: Int){
        Log.v("Test_Picker", "$hour $minute")

        val dataTime = LocalDateTime.of(year.value!!, month.value!! + 1, day.value!!, hour, minute)
        val defaultZoneId = TimeZone.getDefault().toZoneId()
        _zonedDateTime.value = dataTime.atZone(defaultZoneId)

        Log.d("test123123", "${_zonedDateTime.value}")

    }





}