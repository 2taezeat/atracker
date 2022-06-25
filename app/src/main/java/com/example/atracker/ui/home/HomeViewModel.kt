package com.example.atracker.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atracker.model.dto.*
import com.example.atracker.model.repository.RepositoryHome
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    val repositoryHome = RepositoryHome()

    private val _companyTitleList = MutableLiveData<List<String>>().apply {
        value = listOf()
    }
    val companyTitleList : LiveData<List<String>> = _companyTitleList


    val _companyWord = MutableLiveData<String>().apply {
        value = ""
    }
    val companyWord : LiveData<String> = _companyWord




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


    //private val _events = MutableLiveData<MutableMap<LocalDate, List<CalendarEvent>>>()


//    private val _homeWriteProgressSelectArrayList = MutableLiveData<ArrayList<ArrayList<String>>>().apply {
//        value = arrayListOf(
//            arrayListOf("1차 면접", "2차 면접", "3차 면접", "4차 면접", "5차 면접",  "6차 면접", ),
//            arrayListOf("1차 면접", "2차 면접", "3차 면접" ),
//            arrayListOf("1차 면접", "2차 면접", "3차 면접", "4차 면접", "5차 면접" )
//        )
//    }
//
//    val homeWriteProgressSelectArrayList : LiveData<ArrayList<ArrayList<String>>> = _homeWriteProgressSelectArrayList


    private val _homeWriteProgressSelectedMap = MutableLiveData<MutableMap<Int, ArrayList<String>>>().apply {
        value = mutableMapOf(
            0 to arrayListOf("1차 면접", "2차 면접", "3차 면접", "4차 면접", "5차 면접",  "6차 면접"),
            1 to arrayListOf("1차 면접", "2차 면접", "3차 면접" ),
            2 to arrayListOf("1차 면접", "2차 면접", "3차 면접", "4차 면접", "5차 면접" )
        )
    }

    val homeWriteProgressSelectedMap : LiveData<MutableMap<Int, ArrayList<String>>> = _homeWriteProgressSelectedMap


//    private val _homeDetailContents = MutableLiveData<ArrayList<HomeDetailItem>>().apply {
//        value = arrayListOf(
//            HomeDetailItem(progressType = 0,progressName = "00", itemType = ProgressItemBodyType.REVIEW, totalReviewBody = "hello_0##################$$$$$$$$$$####################################################################%%%%%%%%%%%%", questionBody = null, answerBody = null),
//            HomeDetailItem(progressType = 1,progressName = "11", itemType = ProgressItemBodyType.REVIEW, totalReviewBody = "hello_1", questionBody = null, answerBody = null),
//            HomeDetailItem(progressType = 2,progressName = "22", itemType = ProgressItemBodyType.REVIEW, totalReviewBody = "hello_2\n\n\n\n\n\n\n\n\n\n\n\n\n", questionBody = null, answerBody = null),
//            HomeDetailItem(progressType = 3,progressName = "33", itemType = ProgressItemBodyType.REVIEW, totalReviewBody = "hello_3\n\n\n\n\n\n\n\n\n\n\n\n\n", questionBody = null, answerBody = null),
//            HomeDetailItem(progressType = 3,progressName = "33", itemType = ProgressItemBodyType.QNA, totalReviewBody = "hello_3", questionBody = "qna_3", answerBody = "answer_3"),
//            HomeDetailItem(progressType = 4,progressName = "44", itemType = ProgressItemBodyType.REVIEW, totalReviewBody = "hello_4", questionBody = null, answerBody = null),
//            HomeDetailItem(progressType = 5,progressName = "55", itemType = ProgressItemBodyType.REVIEW, totalReviewBody = "hello_5", questionBody = null, answerBody = null),
//            HomeDetailItem(progressType = 6,progressName = "66", itemType = ProgressItemBodyType.REVIEW, totalReviewBody = "hello_6", questionBody = null, answerBody = null),
//        )
//    }
//
//    val homeDetailContents : LiveData<ArrayList<HomeDetailItem>> = _homeDetailContents




    private val _homeDetailContents = MutableLiveData<MutableMap<Int, ArrayList<HomeDetailItem>>>().apply {
        value = mutableMapOf(
            0 to arrayListOf<HomeDetailItem>(
                HomeDetailItem(progressType = 0,progressName = "1차 면접", itemType = ProgressItemBodyType.REVIEW, totalReviewBody = "hello_0##################$$$$$$$$$$##########%%%%%%%%%", questionBody = null, answerBody = null, progressIsPassing = IsPassing.SUCCESS),
                HomeDetailItem(progressType = 1,progressName = "2차 면접", itemType = ProgressItemBodyType.REVIEW, totalReviewBody = "hello_1", questionBody = null, answerBody = null, progressIsPassing = IsPassing.SUCCESS),
                HomeDetailItem(progressType = 2,progressName = "3차 면접", itemType = ProgressItemBodyType.REVIEW, totalReviewBody = "hello_2\n\n\n\n\n\n\n\n\n\n\n\n\n", questionBody = null, answerBody = null, progressIsPassing = IsPassing.WAITING),
                HomeDetailItem(progressType = 3,progressName = "4차 면접", itemType = ProgressItemBodyType.REVIEW, totalReviewBody = "hello_3\n\n\n\n\n\n\n\n\n\n\n\n\n", questionBody = null, answerBody = null, progressIsPassing = IsPassing.FAIL),
                HomeDetailItem(progressType = 3,progressName = "4차 면접", itemType = ProgressItemBodyType.QNA, totalReviewBody = "hello_3", questionBody = "qna_3", answerBody = "answer_3", progressIsPassing = null),
                HomeDetailItem(progressType = 4,progressName = "5차 면접", itemType = ProgressItemBodyType.REVIEW, totalReviewBody = "hello_4", questionBody = null, answerBody = null, progressIsPassing = null),
                HomeDetailItem(progressType = 5,progressName = "6차 면접", itemType = ProgressItemBodyType.REVIEW, totalReviewBody = "hello_5", questionBody = null, answerBody = null, progressIsPassing = null),
            )
        )
    }

    val homeDetailContents : LiveData<MutableMap<Int, ArrayList<HomeDetailItem>>> = _homeDetailContents




    fun getCompanyTitle (searchWord : String) {
        viewModelScope.launch {
            val apiResult = repositoryHome.companySearchCall(accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdHJrLWFjY2Vzc1Rva2VuIiwidG9rZW5fdHlwZSI6IkFDQ0VTU19UT0tFTiIsImlkIjoiMTMiLCJpYXQiOjE2NTYxNDg1ODMsImV4cCI6MTY1NjE1MjE4M30.R2Z4Cv9q-HCaYNJQpq0QpikpARuqePSSAZPDSjPD7SE",
                companySearchRequest = CompanySearchRequest(
                title = searchWord,
                userDefined = true),
                page = 1,
                size = 10
            )

            Log.d("result123123", "${apiResult}")


            if (apiResult.code() == 200) {
                val getResult = apiResult.body()!!.companySearchContents.map{ it.name }
                Log.d("getReuslt", "${getResult}")
                _companyTitleList.value = getResult


            } else {

            }



        }
    }




}