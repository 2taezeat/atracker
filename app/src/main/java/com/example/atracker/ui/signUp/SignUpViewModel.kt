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


    val _signUpPosition = MutableLiveData<String>().apply {
        value = ""
    }
    val signUpPosition : LiveData<String> = _signUpPosition

    val _signUpCareer = MutableLiveData<String>().apply {
        value = ""
    }
    val signUpCareer : LiveData<String> = _signUpCareer


    val careerAgeItems = MutableLiveData<List<String>>()

    init {
        careerAgeItems.value = listOf("신입", "경력")
    }



    fun setUserCareerPosition(position : Int) {
        _signUpCareer.value = careerAgeItems.value!![position]

    }


}