package com.example.atracker.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.atracker.model.dto.HomeProgressItem

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text


    private val _homeProgressArrayList = MutableLiveData<ArrayList<HomeProgressItem>>().apply {
        value = arrayListOf(
            HomeProgressItem(companyTitle = "asd", jobType = "qwe", myProgress = 0, totalProgress = 0),
            HomeProgressItem(companyTitle = "qwe", jobType = "asd", myProgress = 0, totalProgress = 0),
            )
    }

    val homeProgressArrayList : LiveData<ArrayList<HomeProgressItem>> = _homeProgressArrayList



}