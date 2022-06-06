package com.example.atracker.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.atracker.model.dto.HomeDetailItem
import com.example.atracker.model.dto.HomeProgressItem

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text


    private val _homeProgressArrayList = MutableLiveData<ArrayList<HomeProgressItem>>().apply {
        value = arrayListOf(
            HomeProgressItem(companyTitle = "0", jobType = "qwe", myProgress = 2, totalProgress = 2, true),
            HomeProgressItem(companyTitle = "1", jobType = "asd", myProgress = 3, totalProgress = 3, true),
            HomeProgressItem(companyTitle = "2", jobType = "asd", myProgress = 7, totalProgress = 7, true),
            HomeProgressItem(companyTitle = "3", jobType = "asd", myProgress = 4, totalProgress = 5, true),
            HomeProgressItem(companyTitle = "4", jobType = "asd", myProgress = 1, totalProgress = 4, true),
            HomeProgressItem(companyTitle = "5", jobType = "asd", myProgress = 1, totalProgress = 7, true),
            HomeProgressItem(companyTitle = "6", jobType = "asd", myProgress = 2, totalProgress = 7, true),
            HomeProgressItem(companyTitle = "7", jobType = "asd", myProgress = 6, totalProgress = 6, true),
            )
    }

    val homeProgressArrayList : LiveData<ArrayList<HomeProgressItem>> = _homeProgressArrayList


    private val _homeWriteProgressSelectArrayList = MutableLiveData<ArrayList<ArrayList<String>>>().apply {
        value = arrayListOf(
            arrayListOf("1차 면접", "2차 면접", "3차 면접", "4차 면접", "5차 면접",  "6차 면접", ),
            arrayListOf("1차 면접", "2차 면접", "3차 면접" ),
            arrayListOf("1차 면접", "2차 면접", "3차 면접", "4차 면접", "5차 면접" )
        )
    }

    val homeWriteProgressSelectArrayList : LiveData<ArrayList<ArrayList<String>>> = _homeWriteProgressSelectArrayList


    private val _homeDetailContents = MutableLiveData<ArrayList<HomeDetailItem>>().apply {
        value = arrayListOf(
            HomeDetailItem(progressType = 0,progressName = "00", itemType = "totalReview", totalReviewBody = "hello_0##################$$$$$$$$$$####################################################################%%%%%%%%%%%%", questionBody = null, answerBody = null),
            HomeDetailItem(progressType = 1,progressName = "11", itemType = "totalReview", totalReviewBody = "hello_1", questionBody = null, answerBody = null),
            HomeDetailItem(progressType = 2,progressName = "22", itemType = "totalReview", totalReviewBody = "hello_2\n\n\n\n\n\n\n\n\n\n\n\n\n", questionBody = null, answerBody = null),
            HomeDetailItem(progressType = 3,progressName = "33", itemType = "totalReview", totalReviewBody = "hello_3", questionBody = null, answerBody = null),
            HomeDetailItem(progressType = 4,progressName = "44", itemType = "totalReview", totalReviewBody = "hello_4", questionBody = null, answerBody = null),
            HomeDetailItem(progressType = 5,progressName = "55", itemType = "totalReview", totalReviewBody = "hello_5", questionBody = null, answerBody = null),
            HomeDetailItem(progressType = 6,progressName = "66", itemType = "totalReview", totalReviewBody = "hello_6", questionBody = null, answerBody = null),


        )
    }

    val homeDetailContents : LiveData<ArrayList<HomeDetailItem>> = _homeDetailContents


}