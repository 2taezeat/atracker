package com.example.atracker.ui.calendar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.atracker.model.dto.CalendarEvent
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.*

class CalendarViewModel : ViewModel() {

    private val _events = MutableLiveData<MutableMap<LocalDate, List<CalendarEvent>>>()
    val events : LiveData<MutableMap<LocalDate, List<CalendarEvent>>> = _events

    val _eventTitle = MutableLiveData<String>()
    val eventTitle : LiveData<String> = _eventTitle


    val _eventLocation = MutableLiveData<String>()
    val eventLocation : LiveData<String> = _eventLocation
    val _eventNote = MutableLiveData<String>()
    val eventNote : LiveData<String> = _eventNote

    val _eventDate = MutableLiveData<LocalDate>()
    val eventDate : LiveData<LocalDate> = _eventDate

    var year: MutableLiveData<Int> = MutableLiveData()
    var month: MutableLiveData<Int> = MutableLiveData()
    var day: MutableLiveData<Int> = MutableLiveData()
    var hour: MutableLiveData<Int> = MutableLiveData()
    var minute: MutableLiveData<Int> = MutableLiveData()


    var _zonedDateTime = MutableLiveData<ZonedDateTime>()
    var zonedDateTime : LiveData<ZonedDateTime> = _zonedDateTime

    private val _eventChangeFlag = MutableLiveData<Boolean>(false)
    val eventChangeFlag : LiveData<Boolean> = _eventChangeFlag

    init {
        _events.value = mutableMapOf<LocalDate, List<CalendarEvent>>()
        val instant = Instant.now()
        _zonedDateTime.value = instant.atZone(TimeZone.getDefault().toZoneId())


        initTimeDateCurrent()
    }




    fun saveEvent() {
        _events.value!![_zonedDateTime.value!!.toLocalDate()] = _events.value!![_zonedDateTime.value!!.toLocalDate()].orEmpty().plus(CalendarEvent(
            UUID.randomUUID().toString(), _eventTitle.value, _zonedDateTime.value!!, _eventLocation.value, _eventNote.value )
        )

        _eventChangeFlag.value = true
        _eventChangeFlag.value = false
    }


    fun deleteEvent(calendarEvent: CalendarEvent) {
        val date = calendarEvent.zonedDateTime.toLocalDate()
        _events.value!![date] = _events.value!![date].orEmpty().minus(calendarEvent)

        _eventChangeFlag.value = true
        _eventChangeFlag.value = false
    }




    fun onDateChanged(year: Int, month: Int, day: Int){
        val dataTime = LocalDateTime.of(year, month + 1, day, hour.value!!, minute.value!!)
        val defaultZoneId = TimeZone.getDefault().toZoneId()
        _zonedDateTime.value = dataTime.atZone(defaultZoneId)

    }


    fun onTimeChanged(hour: Int, minute: Int){
        val dataTime = LocalDateTime.of(year.value!!, month.value!! + 1, day.value!!, hour, minute)
        val defaultZoneId = TimeZone.getDefault().toZoneId()
        _zonedDateTime.value = dataTime.atZone(defaultZoneId)
        Log.d("test123123", "${_zonedDateTime.value}")

    }


    fun clearEventText() {
        _eventTitle.value = ""
        _eventLocation.value = ""
        _eventNote.value = ""
    }

    fun initTimeDateCurrent(){
        val newDate = Calendar.getInstance()
        year.value = newDate.get(Calendar.YEAR)
        month.value = newDate.get(Calendar.MONTH)
        day.value = newDate.get(Calendar.DAY_OF_MONTH)
        hour.value = newDate.get(Calendar.HOUR_OF_DAY)
        minute.value = newDate.get(Calendar.MINUTE)
    }




}