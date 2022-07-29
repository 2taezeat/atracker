package com.example.atracker.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atracker.BuildConfig
import com.example.atracker.model.dto.ExperienceType
import com.example.atracker.model.dto.SignRequest
import com.example.atracker.model.dto.TokenRefreshRequest
import com.example.atracker.model.local.App
import com.example.atracker.model.repository.RepositorySign
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val repositorySign = RepositorySign()


    fun testSign() {
        viewModelScope.launch {
            val apiResult = repositorySign.testSignCall()

            if (apiResult.code() == 200) {
                val getResult = apiResult.body()!!
                val at = getResult.access_token
                val rt = getResult.refresh_token

                Log.d("test123_at", "${at}")
                Log.d("test123_rt", "${rt}")

                App.prefs.setValue(BuildConfig.ACCESS_LOCAL_TOKEN, "Bearer $at") // * drop 과 bearer 해야되는지 확인해야됨
                App.prefs.setValue(BuildConfig.REFRESH_LOCAL_TOKEN, "Bearer $rt") // * drop 과 bearer 해야되는지 확인해야됨
            } else {

            }

        }

    }








}