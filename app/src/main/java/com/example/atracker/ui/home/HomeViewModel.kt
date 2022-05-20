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
            HomeProgressItem(companyTitle = "0", jobType = "qwe", myProgress = 0, totalProgress = 0),
            HomeProgressItem(companyTitle = "1", jobType = "asd", myProgress = 0, totalProgress = 0),
            HomeProgressItem(companyTitle = "2", jobType = "asd", myProgress = 0, totalProgress = 0),
            HomeProgressItem(companyTitle = "3", jobType = "asd", myProgress = 0, totalProgress = 0),
            HomeProgressItem(companyTitle = "4", jobType = "asd", myProgress = 0, totalProgress = 0),
            HomeProgressItem(companyTitle = "5", jobType = "asd", myProgress = 0, totalProgress = 0),
            HomeProgressItem(companyTitle = "6", jobType = "asd", myProgress = 0, totalProgress = 0),
            HomeProgressItem(companyTitle = "7", jobType = "asd", myProgress = 0, totalProgress = 0),


            )
    }

    val homeProgressArrayList : LiveData<ArrayList<HomeProgressItem>> = _homeProgressArrayList



}