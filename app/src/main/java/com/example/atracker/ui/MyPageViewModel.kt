package com.example.atracker.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atracker.BuildConfig
import com.example.atracker.model.dto.CalendarEvent
import com.example.atracker.model.dto.ExperienceType
import com.example.atracker.model.dto.TokenRefreshRequest
import com.example.atracker.model.local.App
import com.example.atracker.model.repository.RepositoryHome
import com.example.atracker.model.repository.RepositoryMyPage
import com.example.atracker.model.repository.RepositorySign
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.*

class MyPageViewModel : ViewModel() {

    val repositoryMyPage = RepositoryMyPage()
    val repositorySign = RepositorySign()


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
            val apiResult = repositoryMyPage.myPageGetCall(
                accessToken = App.prefs.getValue(BuildConfig.ACCESS_LOCAL_TOKEN)!!)

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
                //testSignInMyPage()
                refreshTokenMyPage()
            }

        }
    }


    fun signOutPost() {
        viewModelScope.launch {
            val apiResult = repositorySign.signOutPostCall(
                accessToken = App.prefs.getValue(BuildConfig.ACCESS_LOCAL_TOKEN)!!)
            
            if (apiResult.code() == 200) {
                val getResult = apiResult.body()
                Log.d("getMyPage", "${getResult}")

            } else {

            }
        }
    }


    fun refreshTokenMyPage() { // refresh token 호출
        viewModelScope.launch {
            val apiResult = repositorySign.refreshTokenCall(
                tokenRefreshRequest = TokenRefreshRequest( App.prefs.getValue(BuildConfig.REFRESH_LOCAL_TOKEN)!! )
            )

            if (apiResult.code() == 200) {
                val getResult = apiResult.body()!!
                val at = getResult.access_token

                App.prefs.setValue(BuildConfig.ACCESS_LOCAL_TOKEN, "Bearer $at")

            } else {

            }

        }
    }





}