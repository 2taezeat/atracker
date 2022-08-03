package com.cmc.atracker.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc.atracker.BuildConfig
import com.cmc.atracker.model.dto.ExperienceType
import com.cmc.atracker.model.dto.TokenRefreshRequest
import com.cmc.atracker.model.local.App
import com.cmc.atracker.model.repository.RepositoryMyPage
import com.cmc.atracker.model.repository.RepositorySign
import kotlinx.coroutines.launch

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