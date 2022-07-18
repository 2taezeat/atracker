package com.example.atracker.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atracker.model.dto.CalendarEvent
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





    fun getMyPage() {
        viewModelScope.launch {
            val apiResult = repositoryMyPage.myPageGetCall(accessToken = "")

            if (apiResult.code() == 200) {
                val getResult = apiResult.body()
                Log.d("getResult", "${getResult}")
            }

        }
    }



}