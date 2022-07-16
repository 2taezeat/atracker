package com.example.atracker.ui.signUp

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

class SignUpViewModel : ViewModel() {


    val _signUpNickName = MutableLiveData<String>().apply {
        value = ""
    }
    val signUpNickName : LiveData<String> = _signUpNickName



    init {

    }



}