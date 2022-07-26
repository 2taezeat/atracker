package com.example.atracker.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atracker.model.dto.CalendarEvent
import com.example.atracker.model.dto.ExperienceType
import com.example.atracker.model.repository.RepositoryHome
import com.example.atracker.model.repository.RepositoryMyPage
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.*

class MyPageViewModel : ViewModel() {

    val repositoryMyPage = RepositoryMyPage()

    private val _userNickName = MutableLiveData<String>().apply {
        value = ""
    }
    val userNickName : LiveData<String> = _userNickName

    private val _userJobPosition = MutableLiveData<String>().apply {
        value = ""
    }
    val userJobPosition : LiveData<String> = _userJobPosition

    private val _userExperienceType = MutableLiveData<String>().apply {
        value = ""
    }

    val userExperienceType : LiveData<String> = _userExperienceType


    init {

    }



    fun getMyPage() {
        viewModelScope.launch {
            val apiResult = repositoryMyPage.myPageGetCall(accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdHJrLWFjY2Vzc1Rva2VuIiwidG9rZW5fdHlwZSI6IkFDQ0VTU19UT0tFTiIsImlkIjoiNDYiLCJpYXQiOjE2NTg4MDg2MzUsImV4cCI6MTY1ODgxMjIzNX0.3TVmrID8U6owYl6F3rWT7Zfn_KtyoEE7uU7hezYi40E")

            if (apiResult.code() == 200) {
                val getResult = apiResult.body()
                Log.d("getMyPage", "${getResult}")
                _userNickName.value = getResult!!.nick_name
                _userJobPosition.value = getResult!!.job_position

                when (getResult!!.experience_type) {
                    ExperienceType.EXPERIENCED.toString() -> _userExperienceType.value = "경력"
                    ExperienceType.NOT_EXPERIENCED.toString() -> _userExperienceType.value = "신입"
                }
            } else {
                _userNickName.value = " "
            }

        }
    }



}