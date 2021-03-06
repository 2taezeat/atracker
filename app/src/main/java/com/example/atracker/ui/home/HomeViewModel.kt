package com.example.atracker.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atracker.model.dto.*
import com.example.atracker.model.repository.RepositoryHome
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.*
import kotlin.collections.ArrayList

class HomeViewModel : ViewModel() {
    val repositoryHome = RepositoryHome()

    var _zonedDateTime = MutableLiveData<ZonedDateTime>()
    var zonedDateTime : LiveData<ZonedDateTime> = _zonedDateTime

    var year: MutableLiveData<Int> = MutableLiveData()
    var month: MutableLiveData<Int> = MutableLiveData()
    var day: MutableLiveData<Int> = MutableLiveData()
    var hour: MutableLiveData<Int> = MutableLiveData()
    var minute: MutableLiveData<Int> = MutableLiveData()

    private val workTypeItems = MutableLiveData<List<String>>()


    init {
        val instant = Instant.now()
        _zonedDateTime.value = instant.atZone(TimeZone.getDefault().toZoneId())
        workTypeItems.value = listOf("PERMANENT", "TEMPORARY", "INTERN")
        initTimeDateCurrent()
    }




    private val _companyList = MutableLiveData<List<CompanySearchContent>>().apply {
        value = listOf()
    }
    val companyList : LiveData<List<CompanySearchContent>> = _companyList


    val _companyWord = MutableLiveData<String>().apply {
        value = ""
    }
    val companyWord : LiveData<String> = _companyWord


    val _companyId = MutableLiveData<Int>().apply {
        value = 0
    }
    val companyId : LiveData<Int> = _companyId


    val _positionWord = MutableLiveData<String>().apply {
        value = ""
    }
    val positionWord : LiveData<String> = _positionWord

    val _workTypeWord = MutableLiveData<String>().apply {
        value = ""
    }
    val workTypeWord : LiveData<String> = _workTypeWord



    val _workTypeSelection = MutableLiveData<Int>().apply {
        value = -1
    }
    val workTypeSelection : LiveData<Int> = _workTypeSelection


    private val _homeAddSelectedStage = MutableLiveData<ArrayList<Stage>>().apply {
        arrayListOf<Stage>()
    }
    val homeAddSelectedStage : LiveData<ArrayList<Stage>> = _homeAddSelectedStage

    private val _homeAddSelectedProgress = MutableLiveData<ArrayList<HomeAddProgress>>().apply {
    }
    val homeAddSelectedProgress : LiveData<ArrayList<HomeAddProgress>> = _homeAddSelectedProgress


    private val _homeAddDateSelectFlag = MutableLiveData<Boolean?>().apply {
        null
    }
    val homeAddDateSelectFlag : LiveData<Boolean?> = _homeAddDateSelectFlag



    private val _homeAddStagesContent = MutableLiveData<List<StageResponseItem>>().apply {
    }
    val homeAddStagesContent : LiveData<List<StageResponseItem>> = _homeAddStagesContent

    private val _postApplyFlag = MutableLiveData<Boolean?>().apply {
        null
    }
    val postApplyFlag : LiveData<Boolean?> = _postApplyFlag



//    private val _homeProgressArrayList = MutableLiveData<ArrayList<HomeProgressItem>>().apply {
//        value = arrayListOf(
//            HomeProgressItem(companyTitle = "0", jobType = "qwe", myProgress = 2, totalProgress = 2, true),
//            HomeProgressItem(companyTitle = "1", jobType = "asd", myProgress = 3, totalProgress = 3, true),
//            HomeProgressItem(companyTitle = "2", jobType = "asd", myProgress = 7, totalProgress = 7, true),
//            HomeProgressItem(companyTitle = "3", jobType = "asd", myProgress = 4, totalProgress = 5, true),
//            HomeProgressItem(companyTitle = "4", jobType = "asd", myProgress = 1, totalProgress = 4, true),
//            HomeProgressItem(companyTitle = "5", jobType = "asd", myProgress = 1, totalProgress = 7, true),
//            HomeProgressItem(companyTitle = "6", jobType = "asd", myProgress = 2, totalProgress = 7, true),
//            HomeProgressItem(companyTitle = "7", jobType = "asd", myProgress = 6, totalProgress = 6, true),
//            )
//    }
//
//    val homeProgressArrayList : LiveData<ArrayList<HomeProgressItem>> = _homeProgressArrayList



    private val _homeDisplayArrayList = MutableLiveData<ArrayList<HomeProgressItem>>().apply {
        value = arrayListOf()
    }

    val homeDisplayArrayList : LiveData<ArrayList<HomeProgressItem>> = _homeDisplayArrayList



//    private val _homeWriteProgressSelectArrayList = MutableLiveData<ArrayList<ArrayList<String>>>().apply {
//        value = arrayListOf(
//            arrayListOf("1??? ??????", "2??? ??????", "3??? ??????", "4??? ??????", "5??? ??????",  "6??? ??????", ),
//            arrayListOf("1??? ??????", "2??? ??????", "3??? ??????" ),
//            arrayListOf("1??? ??????", "2??? ??????", "3??? ??????", "4??? ??????", "5??? ??????" )
//        )
//    }
//
//    val homeWriteProgressSelectArrayList : LiveData<ArrayList<ArrayList<String>>> = _homeWriteProgressSelectArrayList


    private val _homeWriteProgressSelectedMap = MutableLiveData<MutableMap<Int, ArrayList<String>>>().apply {
        value = mutableMapOf(
            2445 to arrayListOf("1??? ??????", "2??? ??????", "3??? ??????", "4??? ??????", "5??? ??????",  "6??? ??????"),
            1 to arrayListOf("1??? ??????", "2??? ??????", "3??? ??????" ),
            2 to arrayListOf("1??? ??????", "2??? ??????", "3??? ??????", "4??? ??????", "5??? ??????" )
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
                HomeDetailItem(progressType = 0,progressName = "1??? ??????", itemType = ProgressItemBodyType.REVIEW, freeTitle = "freetitle1", freeBody = "free1", totalReviewBody = "hello_0##################$$$$$$$$$$##########%%%%%%%%%", questionBody = null, answerBody = null, qnaReviewBody = null, progressIsPassing = IsPassing.SUCCESS),
                HomeDetailItem(progressType = 1,progressName = "2??? ??????", itemType = ProgressItemBodyType.QNA, freeTitle = null, freeBody = null, totalReviewBody = "hello_1", questionBody = "q2", answerBody = "a2", qnaReviewBody = "qnaReview2", progressIsPassing = IsPassing.SUCCESS),
                HomeDetailItem(progressType = 2,progressName = "3??? ??????", itemType = ProgressItemBodyType.FREE, freeTitle = "freetitle3", freeBody = "freeBody3", totalReviewBody = "hello_2\n\n\n\n\n\n\n\n\n\n\n\n\n", questionBody = null, answerBody = null, qnaReviewBody = null,  progressIsPassing = IsPassing.WAITING),
                HomeDetailItem(progressType = 3,progressName = "4??? ??????", itemType = ProgressItemBodyType.REVIEW, freeTitle = null, freeBody = null, totalReviewBody = "hello_3\n\n\n\n\n\n\n\n\n\n\n\n\n", questionBody = null, answerBody = null, qnaReviewBody = null,  progressIsPassing = IsPassing.FAIL),
                HomeDetailItem(progressType = 3,progressName = "4??? ??????", itemType = ProgressItemBodyType.QNA, freeTitle = null, freeBody = null, totalReviewBody = "hello_3", questionBody = "qna_3", answerBody = "answer_3", qnaReviewBody = "qna_review_body_3",  progressIsPassing = null),
                HomeDetailItem(progressType = 3,progressName = "4??? ??????", itemType = ProgressItemBodyType.FREE, freeTitle = "freetitle4", freeBody = "freeBody4", totalReviewBody = "hello_3", questionBody = null, answerBody = null, qnaReviewBody = null,  progressIsPassing = null),
                HomeDetailItem(progressType = 4,progressName = "5??? ??????", itemType = ProgressItemBodyType.FREE, freeTitle = null, freeBody = null, totalReviewBody = "hello_4", questionBody = null, answerBody = null, qnaReviewBody = null,  progressIsPassing = null),
                HomeDetailItem(progressType = 5,progressName = "6??? ??????", itemType = ProgressItemBodyType.FREE, freeTitle = null, freeBody = null, totalReviewBody = "hello_5", questionBody = null, answerBody = null, qnaReviewBody = null,  progressIsPassing = null),
            )
        )
    }

    val homeDetailContents : LiveData<MutableMap<Int, ArrayList<HomeDetailItem>>> = _homeDetailContents


    private val _homeApplyIdContent = MutableLiveData<Apply>().apply {
        value = Apply(apply_id = 0,
            company_id = 0,
            company_name = "",
            job_position = "",
            job_type = "",
            stage_progress = listOf()
        )
    }

    val homeApplyIdContent : LiveData<Apply> = _homeApplyIdContent




    fun getCompanyTitle (searchWord : String) {
        viewModelScope.launch {
            val apiResult = repositoryHome.companySearchPostCall(accessToken = "",
                companySearchRequest = CompanySearchRequest(
                title = searchWord,
                userDefined = true),
                page = 1,
                size = 10
            )

            if (apiResult.code() == 200) {
                val getResult = apiResult.body()!!.companySearchContents
                Log.d("getResult", "${getResult}")
                _companyList.value = getResult


            } else {

            }
        }
    }


    fun getStage() {
        viewModelScope.launch {
            val apiResult = repositoryHome.stageGetCall(accessToken = "")

            if (apiResult.code() == 200) {
                val getResult = apiResult.body()!!
                Log.d("getResult", "${getResult}")
                _homeAddStagesContent.value = getResult
            }

        }
    }


    fun postApply() {
        val createApplyRequest = CreateApplyRequest(
            company = Company(id = _companyId.value!!, name = _companyWord.value!!),
            job_position = _positionWord.value!!,
            job_type = _workTypeWord.value!!,
            stages = _homeAddSelectedStage.value!!
        )

        Log.d("createApplyRequest", "${createApplyRequest}")

        viewModelScope.launch {
            val apiResult = repositoryHome.createApplyPostCall(accessToken = "", createApplyRequest = createApplyRequest )
            Log.d("getResult_1", "${apiResult}")
            if (apiResult.code() == 200) {
                switch(_postApplyFlag)
            }
        }
    }

    fun updateApply(applyId : Int) {
        val updateApplyRequest = UpdateApplyRequest(apply_id = applyId,
            company = Company(id = _companyId.value!!, name = _companyWord.value!!),
            job_position = _positionWord.value!!,
            job_type = _workTypeWord.value!!,
            stages = _homeAddSelectedStage.value!!)

        Log.d("updateApplyRequest", "${updateApplyRequest}")

        viewModelScope.launch {
            val apiResult = repositoryHome.updateApplyPutCall(accessToken = "", updateApplyRequest = updateApplyRequest )
            Log.d("getResult_33", "${apiResult}")
            if (apiResult.code() == 200) {
                switch(_postApplyFlag)
            }
        }
    }



    fun deleteApply(deleteIds : Array<Int>) {
        viewModelScope.launch {
            val apiResult = repositoryHome.deleteApplyCall(accessToken = "", ids = deleteIds )
            Log.d("deleteApply", "${apiResult}")
            if (apiResult.code() == 200) {
                switch(_postApplyFlag)
            }

        }
    }



    fun getApplyDisplay(applyIds : Array<Int>? = null, includeContent : Boolean? = false) {
        viewModelScope.launch {
            val apiResult = repositoryHome.applyGetCall(
                accessToken = "",
                applyIds = applyIds,
                includeContent = includeContent)

            if (apiResult.code() == 200) {
                val getResult = apiResult.body()!!.applies
                Log.d("getResult22", "${getResult}")

                val newArrayList = arrayListOf<HomeProgressItem>()

                for (apply in getResult){
                    var myProgress = 0
                    var success = true
                    val applyId = apply.apply_id
                    val stageProgress = apply.stage_progress
                    val companyTitle = apply.company_name
                    val jobType = apply.job_position
                    val totalProgress = stageProgress.size

                    for (stage in stageProgress) {
                        if (stage.status == IsPassingTmp.FAIL.toString() && success)
                            success = false

                        if (stage.status == IsPassingTmp.PASS.toString())
                            myProgress += 1
                    }
                    newArrayList.add(HomeProgressItem(
                        companyTitle = companyTitle,
                        jobType = jobType,
                        myProgress = myProgress,
                        totalProgress = totalProgress,
                        success = success,
                        applyId = applyId
                        )
                    )
                }
                _homeDisplayArrayList.value = newArrayList
            }
        }
    }



    fun getApplyDetail(applyIds : Array<Int>? = null, includeContent : Boolean? = false) {
        viewModelScope.launch {
            val apiResult = repositoryHome.applyGetCall(
                accessToken = "",
                applyIds = applyIds,
                includeContent = includeContent)

            if (apiResult.code() == 200) {
                val getResult = apiResult.body()!!.applies[0]
                _homeApplyIdContent.value = getResult
                Log.d("_homeApplyIdContent" ,"${_homeApplyIdContent.value}")
            }
        }
    }


    fun setWorkTypePosition(position : Int) {
        _workTypeSelection.value = position
        _workTypeWord.value = workTypeItems.value!![position]
    }

    fun setCompanyNameID(position: Int) {
        _companyId.value = _companyList.value!![position].id
    }


    fun setSelectedChipStage(addCheckedStage : ArrayList<Stage>) {
        _homeAddSelectedStage.value = addCheckedStage
        Log.d("tttt123", "${_homeAddSelectedStage.value}")
    }


    fun setSelectedChipName(addCheckedProgress : ArrayList<HomeAddProgress>) {
        _homeAddSelectedProgress.value = addCheckedProgress
    }



    fun onDateChanged(year: Int, month: Int, day: Int){
        val dataTime = LocalDateTime.of(year, month + 1, day, hour.value!!, minute.value!!)
        val defaultZoneId = TimeZone.getDefault().toZoneId()
        _zonedDateTime.value = dataTime.atZone(defaultZoneId)
    }


    fun onTimeChanged(hour: Int, minute: Int){
        val dataTime = LocalDateTime.of(year.value!!, month.value!! + 1, day.value!!, hour, minute)
        val defaultZoneId = TimeZone.getDefault().toZoneId()
        _zonedDateTime.value = dataTime.atZone(defaultZoneId)
    }


    fun initTimeDateCurrent(){
        val newDate = Calendar.getInstance()
        year.value = newDate.get(Calendar.YEAR)
        month.value = newDate.get(Calendar.MONTH)
        day.value = newDate.get(Calendar.DAY_OF_MONTH)
        hour.value = newDate.get(Calendar.HOUR_OF_DAY)
        minute.value = newDate.get(Calendar.MINUTE)
    }


    fun setZonedHomeAddProgress(position: Int?) {
        _homeAddSelectedStage.value!![position!!].event_at = _zonedDateTime.value.toString()
        _homeAddSelectedProgress.value!![position!!].zonedDateTime = _zonedDateTime.value


        switch(_homeAddDateSelectFlag)
        Log.d("tttt1234", "${_homeAddSelectedStage.value}")

    }

    fun switch(mutableLiveData: MutableLiveData<Boolean?>){
        mutableLiveData.value = if(mutableLiveData.value==null) true else !mutableLiveData.value!!
    }

    fun clearHomeAddText(){
        _positionWord.value = ""
        _companyWord.value = ""
        _companyList.value = listOf()
    }

    fun setHomeEdit(){
        _positionWord.value = _homeApplyIdContent.value!!.job_position
        _companyWord.value = _homeApplyIdContent.value!!.company_name
        _companyId.value = _homeApplyIdContent.value!!.company_id

        _workTypeWord.value = _homeApplyIdContent.value!!.job_type
        _homeAddSelectedStage.value = arrayListOf()


        for ( s in _homeApplyIdContent.value!!.stage_progress) {
            val stage = Stage(event_at = "", order = s.order, stage_id = s.stage_id)
            _homeAddSelectedStage.value!!.add(stage)
        }

        //_homeAddSelectedStage.value = _homeApplyIdContent.value!!.stage_progresses

//        val createApplyRequest = CreateApplyRequest(
//            company = Company(id = _companyId.value!!, name = _companyWord.value!!),
//            job_position = _positionWord.value!!,
//            job_type = _workTypeWord.value!!,
//            stages = _homeAddSelectedStage.value!!
//        )

    }

    fun setWorkTypeSpinnerPosition() : Int{
        var idx = 0

        for(w in workTypeItems.value!!) {
            if (w == _homeApplyIdContent.value!!.job_type)
                break
            idx += 1
        }

        return idx
    }




}