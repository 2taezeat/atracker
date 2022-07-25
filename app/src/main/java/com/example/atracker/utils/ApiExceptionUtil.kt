package com.example.atracker.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


object ApiExceptionUtil {
    val _apiExceptionFlag = MutableLiveData<Event<Boolean>>()
    val apiExceptionFlag : LiveData<Event<Boolean>> = _apiExceptionFlag


}

