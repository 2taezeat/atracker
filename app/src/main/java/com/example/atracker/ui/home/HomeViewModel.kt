package com.example.atracker.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atracker.model.dto.*
import com.example.atracker.model.repository.RepositoryHome
import com.example.atracker.model.repository.RepositorySign
import com.example.atracker.utils.Event
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.*
import kotlin.collections.ArrayList


class HomeViewModel : ViewModel() {
    val repositoryHome = RepositoryHome()
    val repositorySign = RepositorySign()

    var _zonedDateTime = MutableLiveData<ZonedDateTime>()
    var zonedDateTime : LiveData<ZonedDateTime> = _zonedDateTime

    var year: MutableLiveData<Int> = MutableLiveData()
    var month: MutableLiveData<Int> = MutableLiveData()
    var day: MutableLiveData<Int> = MutableLiveData()
    var hour: MutableLiveData<Int> = MutableLiveData()
    var minute: MutableLiveData<Int> = MutableLiveData()

    private val workTypeItems = MutableLiveData<List<String>>()

    private val _companyList = MutableLiveData<List<CompanySearchContent>>().apply {
        value = listOf()
    }
    val companyList : LiveData<List<CompanySearchContent>> = _companyList

    val _companyResponse = MutableLiveData<CompanySearchResponse?>()
    val companyResponse : LiveData<CompanySearchResponse?> = _companyResponse

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
    val homeAddSelectedProgress : LiveData<ArrayList<HomeAddProgress>> = _homeAddSelectedProgress // HomeAddCalendarEventsAdapter 에 사용

    private val _homeAddDateSelectFlag = MutableLiveData<Boolean?>().apply {
        null
    }
    val homeAddDateSelectFlag : LiveData<Boolean?> = _homeAddDateSelectFlag

    private val _homeAddStagesContent = MutableLiveData<List<StageResponseItem>>().apply {
    }
    val homeAddStagesContent : LiveData<List<StageResponseItem>> = _homeAddStagesContent

    val _postApplyFlag = MutableLiveData<Boolean?>().apply {
        null
    }
    val postApplyFlag : LiveData<Boolean?> = _postApplyFlag

    private val _deleteApplyFail = MutableLiveData<Event<Boolean>>()
    val deleteApplyFail : LiveData<Event<Boolean>> = _deleteApplyFail

    private val _getApplyDetailFail = MutableLiveData<Event<Boolean>>()
    val getApplyDetailFail : LiveData<Event<Boolean>> = _getApplyDetailFail

    private val _addCompanyFail = MutableLiveData<Event<Boolean>>()
    val addCompanyFail : LiveData<Event<Boolean>> = _addCompanyFail

    private val _updateApplyFail = MutableLiveData<Event<Boolean>>()
    val updateApplyFail : LiveData<Event<Boolean>> = _updateApplyFail

    val _addCalendarToAddFlag = MutableLiveData<Boolean?>()
    val addCalendarToAddFlag : LiveData<Boolean?> = _addCalendarToAddFlag

    private val _homeDisplayArrayList = MutableLiveData<ArrayList<HomeProgressItem>>().apply {
        value = arrayListOf()
    }
    val homeDisplayArrayList : LiveData<ArrayList<HomeProgressItem>> = _homeDisplayArrayList

    private val _homeDetailArrayList = MutableLiveData<ArrayList<HomeProgressItem>>().apply { // home Detail progress bar drawable 용
        value = arrayListOf()
    }
    val homeDetailArrayList : LiveData<ArrayList<HomeProgressItem>> = _homeDetailArrayList

    private val _homeProgressNameDetail = MutableLiveData<Event<ArrayList<String>>>()
    val homeProgressNameDetail : LiveData<Event<ArrayList<String>>> = _homeProgressNameDetail

    private val _homeProgressNameWrite = MutableLiveData<ArrayList<String>>()
    val homeProgressNameWrite : LiveData<ArrayList<String>> = _homeProgressNameWrite

    private val _trueChipSet = MutableLiveData<MutableSet<Int>>()
    val trueChipSet : LiveData<MutableSet<Int>> = _trueChipSet

    private val _falseChipSet = MutableLiveData<MutableSet<Int>>()
    val falseChipSet : LiveData<MutableSet<Int>> = _falseChipSet

    private val _oriChipSet = MutableLiveData<MutableSet<Int>>()
    val oriChipSet : LiveData<MutableSet<Int>> = _oriChipSet

    private val _homeDetailContents = MutableLiveData<MutableMap<Int, ArrayList<HomeDetailItem>>>().apply {
        value = mutableMapOf(
            3039 to arrayListOf<HomeDetailItem>(
                HomeDetailItem(progressType = 0,progressName = "서류", itemType = ProgressItemBodyType.REVIEW, freeTitle = "freetitle1", freeBody = "free1", totalReviewBody = "hello_0##################$$$$$$$$$$##########%%%%%%%%%", questionBody = null, answerBody = null, qnaReviewBody = null, progressIsPassing = IsPassing.SUCCESS),
                HomeDetailItem(progressType = 1,progressName = "사전 과제", itemType = ProgressItemBodyType.QNA, freeTitle = null, freeBody = null, totalReviewBody = "hello_1", questionBody = "q2", answerBody = "a2", qnaReviewBody = "qnaReview2", progressIsPassing = IsPassing.SUCCESS),
                HomeDetailItem(progressType = 1,progressName = "포트폴리오", itemType = ProgressItemBodyType.QNA, freeTitle = null, freeBody = null, totalReviewBody = "hello_1", questionBody = "q2", answerBody = "a2", qnaReviewBody = "qnaReview2", progressIsPassing = IsPassing.SUCCESS),
            )
        )
    }
    val homeDetailContents : LiveData<MutableMap<Int, ArrayList<HomeDetailItem>>> = _homeDetailContents

    private val _homeDetailContentForDisplay = MutableLiveData<ArrayList<HomeDetailItem>>().apply { // map 아님 주의
        arrayListOf<HomeDetailItem>()
    }

    val homeDetailContentForDisplay : LiveData<ArrayList<HomeDetailItem>> = _homeDetailContentForDisplay

    private val _homeApplyIdContent = MutableLiveData<Apply>().apply {
        value = Apply(apply_id = -1, company_id = 0, company_name = "", job_position = "", job_type = "", stage_progress = listOf())
    }
    val homeApplyIdContent : LiveData<Apply> = _homeApplyIdContent

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    fun initTimeDateCurrent(){
        val newDate = Calendar.getInstance()
        year.value = newDate.get(Calendar.YEAR)
        month.value = newDate.get(Calendar.MONTH)
        day.value = newDate.get(Calendar.DAY_OF_MONTH)
        hour.value = newDate.get(Calendar.HOUR_OF_DAY)
        minute.value = newDate.get(Calendar.MINUTE)
    }

    init {
        val instant = Instant.now()
        _zonedDateTime.value = instant.atZone(TimeZone.getDefault().toZoneId())
        workTypeItems.value = listOf("PERMANENT", "TEMPORARY", "INTERN")
        initTimeDateCurrent()
        _trueChipSet.value = mutableSetOf()
        _falseChipSet.value = mutableSetOf()
        _oriChipSet.value = mutableSetOf()
        _companyResponse.value = null
        _addCalendarToAddFlag.value = null
        _homeDetailContentForDisplay.value = arrayListOf()
    }

    fun setWorkTypePosition(position : Int) {
        _workTypeSelection.value = position
        _workTypeWord.value = workTypeItems.value!![position]
    }

    fun setCompanyNameID(position: Int) {
        _companyWord.value = _companyList.value!![position].name
        _companyId.value = _companyList.value!![position].id
        Log.d("setCompanyNameID", "${_companyWord.value}")
    }

    fun setSelectedChipStage(addCheckedStage : ArrayList<Stage>) {
        _homeAddSelectedStage.value = addCheckedStage
        Log.d("setSelectedChipStage", "${_homeAddSelectedStage.value}")
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

    fun setZonedHomeAddProgress(position: Int?) {
        val zoneToLocal = _zonedDateTime.value!!.toLocalDateTime()
        _homeAddSelectedStage.value!![position!!].event_at = zoneToLocal.toString()+'Z' // time 값을 정확히 맞추기 위함.
        _homeAddSelectedProgress.value!![position!!].zonedDateTime = _zonedDateTime.value

        switch(_homeAddDateSelectFlag)
        Log.d("setZonedHomeAddProgress", "${_homeAddSelectedStage.value}")
    }

    fun switch(mutableLiveData: MutableLiveData<Boolean?>){
        mutableLiveData.value = if(mutableLiveData.value == null) true else !mutableLiveData.value!!
    }

    fun setHomeAddDateSelectTime(position: Int?) {
        val selectedZoneTime = _homeAddSelectedProgress.value!![position!!].zonedDateTime

        selectedZoneTime?.let {
            year.value = it.year
            month.value = it.monthValue - 1
            day.value = it.dayOfMonth
            hour.value = it.hour
            minute.value = it.minute
        }

    }

    fun clearHomeAddText(){
        clearCompanyValue()

        _positionWord.value = ""
        setClearCompanyList()

        _trueChipSet.value = mutableSetOf()
        _falseChipSet.value = mutableSetOf()

        _addCalendarToAddFlag.value = null
    }

    fun setClearCompanyList(){
        _companyList.value = listOf()
        _companyResponse.value = null
    }

    fun setHomeEdit(){
        _companyWord.value = _homeApplyIdContent.value!!.company_name
        _companyId.value = _homeApplyIdContent.value!!.company_id
        //_positionWord.value = _homeApplyIdContent.value!!.job_position
        _workTypeWord.value = _homeApplyIdContent.value!!.job_type
        _homeAddSelectedStage.value = arrayListOf()

        for ( s in _homeApplyIdContent.value!!.stage_progress) {
            val stage = Stage(event_at = s.event_at, order = s.order, stage_id = s.stage_id)
            _homeAddSelectedStage.value!!.add(stage)
        }
        Log.d("setHomeEdit", "${_homeAddSelectedStage.value}")
    }

    fun setEditPositionWord() {
        _positionWord.value = _homeApplyIdContent.value!!.job_position
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

    fun setHomeProgressNameWrite(ori : ArrayList<String>){
        _homeProgressNameWrite.value = ori
    }


    fun clearCompanyValue(){
        _companyWord.value = ""
        _companyId.value = 0
    }

    fun setChipSet(boolean: Boolean, num : Int){
        val trueNew = _trueChipSet.value!!
        val falseNew = _falseChipSet.value!!

        if (boolean){
            trueNew.add(num)
            falseNew.remove(num)
        } else {
            trueNew.remove(num)
            falseNew.add(num)
        }

        _trueChipSet.value = trueNew
        _falseChipSet.value = falseNew
        Log.d("tmp_pos", "${_trueChipSet.value}, ${_falseChipSet.value}")
    }

    fun refreshChip(){
        _trueChipSet.value = mutableSetOf<Int>()
        val newOriChip = _oriChipSet.value!!.toMutableSet()
        _falseChipSet.value = newOriChip
    }

    fun setOriChipId(chipId : Int){
        _oriChipSet.value!!.add(chipId)
    }

    fun switchFlagNull(mutableLiveData: MutableLiveData<Boolean?>){
        mutableLiveData.value = null
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    fun getCompanyInfo (searchWord : String, page : Int, size : Int = 10, isScroll : Boolean) { // 예외 처리 보류 (220726)
        viewModelScope.launch {
            val apiResult = repositoryHome.companySearchPostCall(accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdHJrLWFjY2Vzc1Rva2VuIiwidG9rZW5fdHlwZSI6IkFDQ0VTU19UT0tFTiIsImlkIjoiNjEiLCJpYXQiOjE2NTg5MjAxOTUsImV4cCI6MTY1ODkyMzc5NX0.ZjdvkYBxQckVyHJRdj2XG8XuZAFlpRhsiyrp5RkXMr0",
                companySearchRequest = CompanySearchRequest(
                title = searchWord,
                userDefined = true),
                page = page,
                size = size
            )
            Log.d("getCompanyInfo", "${isScroll}")


            if (apiResult.code() == 200) {
                val getResult = apiResult.body()!!
                Log.d("companySearchContents", "${getResult}")

                if (isScroll) {
                    val new = _companyList.value!!.toMutableList()
                    new.addAll(getResult.companySearchContents)
                    _companyList.value = new
                } else {
                    val new = getResult.companySearchContents
                    _companyList.value = new
                }

                val newCompanyResponse : CompanySearchResponse = getResult
                _companyResponse.value = newCompanyResponse
            } else {

            }
        }
    }


    fun addCompanyInfo () {
        viewModelScope.launch {
            val apiResult = repositoryHome.companyAddPostCall(accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdHJrLWFjY2Vzc1Rva2VuIiwidG9rZW5fdHlwZSI6IkFDQ0VTU19UT0tFTiIsImlkIjoiNjEiLCJpYXQiOjE2NTg5MjAxOTUsImV4cCI6MTY1ODkyMzc5NX0.ZjdvkYBxQckVyHJRdj2XG8XuZAFlpRhsiyrp5RkXMr0",
                createCompanyRequest = listOf(CreateCompanyRequestItem(name = _companyWord.value!!))
            )

            if (apiResult.code() == 200) {
                val getResult = apiResult.body()!!.companies
                Log.d("addCompanyInfo", "${getResult}")
                _companyId.value = getResult[0].id
                _companyWord.value = getResult[0].name
                _addCompanyFail.value = Event(false)
            } else {
                _addCompanyFail.value = Event(true)
            }
        }
    }


    fun getStage() {
        viewModelScope.launch {
            val apiResult = repositoryHome.stageGetCall(accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdHJrLWFjY2Vzc1Rva2VuIiwidG9rZW5fdHlwZSI6IkFDQ0VTU19UT0tFTiIsImlkIjoiNjEiLCJpYXQiOjE2NTg5MjAxOTUsImV4cCI6MTY1ODkyMzc5NX0.ZjdvkYBxQckVyHJRdj2XG8XuZAFlpRhsiyrp5RkXMr0")

            if (apiResult.code() == 200) {
                val getResult = apiResult.body()!!
                Log.d("getResult", "${getResult}")
                _homeAddStagesContent.value = getResult
                //ApiExceptionUtil._apiExceptionFlag.value = Event(true)

            } else {
                _homeAddStagesContent.value = listOf()
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
            val apiResult = repositoryHome.createApplyPostCall(accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdHJrLWFjY2Vzc1Rva2VuIiwidG9rZW5fdHlwZSI6IkFDQ0VTU19UT0tFTiIsImlkIjoiNjEiLCJpYXQiOjE2NTg5MjAxOTUsImV4cCI6MTY1ODkyMzc5NX0.ZjdvkYBxQckVyHJRdj2XG8XuZAFlpRhsiyrp5RkXMr0", createApplyRequest = createApplyRequest )
            Log.d("getResult_1", "${apiResult}")
            if (apiResult.code() == 200) {
                switch(_postApplyFlag)
                _updateApplyFail.value = Event(false)
            } else {
                _updateApplyFail.value = Event(true)
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
            val apiResult = repositoryHome.updateApplyPutCall(accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdHJrLWFjY2Vzc1Rva2VuIiwidG9rZW5fdHlwZSI6IkFDQ0VTU19UT0tFTiIsImlkIjoiNjEiLCJpYXQiOjE2NTg5MjAxOTUsImV4cCI6MTY1ODkyMzc5NX0.ZjdvkYBxQckVyHJRdj2XG8XuZAFlpRhsiyrp5RkXMr0", updateApplyRequest = updateApplyRequest )
            Log.d("getResult_33", "${apiResult}")
            if (apiResult.code() == 200) {
                switch(_postApplyFlag)
                _updateApplyFail.value = Event(false)
            } else {
                _updateApplyFail.value = Event(true)
            }
        }
    }


    fun deleteApply(deleteIds : Array<Int>) {
        viewModelScope.launch {
            val apiResult = repositoryHome.deleteApplyCall(accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdHJrLWFjY2Vzc1Rva2VuIiwidG9rZW5fdHlwZSI6IkFDQ0VTU19UT0tFTiIsImlkIjoiNjEiLCJpYXQiOjE2NTg5MjAxOTUsImV4cCI6MTY1ODkyMzc5NX0.ZjdvkYBxQckVyHJRdj2XG8XuZAFlpRhsiyrp5RkXMr0", ids = deleteIds )
            Log.d("deleteApply", "${apiResult}")
            if (apiResult.code() == 200) {
                switch(_postApplyFlag)
                _deleteApplyFail.value = Event(false)
            } else {
                //switch(_deleteApplyFail)
                _deleteApplyFail.value = Event(true)
            }

        }
    }


    fun getApplyDisplay(applyIds : Array<Int>? = null, includeContent : Boolean? = false) { // 예외 처리 보류 (220725)
        viewModelScope.launch {
            val apiResult = repositoryHome.applyGetCall(
                accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdHJrLWFjY2Vzc1Rva2VuIiwidG9rZW5fdHlwZSI6IkFDQ0VTU19UT0tFTiIsImlkIjoiNjEiLCJpYXQiOjE2NTg5MjAxOTUsImV4cCI6MTY1ODkyMzc5NX0.ZjdvkYBxQckVyHJRdj2XG8XuZAFlpRhsiyrp5RkXMr0",
                applyIds = applyIds,
                includeContent = includeContent)

            if (apiResult.code() == 200) {
                val getResult = apiResult.body()!!.applies
                Log.d("getResult22", "${getResult}")

                val newArrayList = arrayListOf<HomeProgressItem>()
                val detailArrayList = arrayListOf<HomeProgressItem>()

                for (apply in getResult){
                    var myProgress = 0
                    var success = true
                    val applyId = apply.apply_id
                    val stageProgress = apply.stage_progress
                    val companyTitle = apply.company_name
                    val jobType = apply.job_position
                    val totalProgress = stageProgress.size

                    var detailMyProgress = 0
                    val detailSuccess = true


                    for (stage in stageProgress) {
                        val stageStatus = stage.status // status type => FAIL, IN_PROGRESS, NOT_STARTED, PASS 4가지

                        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        if (stageStatus == IsPassingTmp.FAIL.toString() && success) // status type => FAIL, IN_PROGRESS, NOT_STARTED, PASS 4가지
                            success = false

                        if (stageStatus == IsPassingTmp.PASS.toString())
                            myProgress += 1

                        detailMyProgress += 1
                    }

                    newArrayList.add(HomeProgressItem(companyTitle = companyTitle, jobType = jobType, myProgress = myProgress, totalProgress = totalProgress, success = success, applyId = applyId))
                    detailArrayList.add(HomeProgressItem(companyTitle = companyTitle, jobType = jobType, myProgress = detailMyProgress, totalProgress = totalProgress, success = detailSuccess, applyId = applyId))
                }
                _homeDisplayArrayList.value = newArrayList
                _homeDetailArrayList.value = detailArrayList
            } else {

            }
        }
    }



    fun getApplyDetail(applyIds : Array<Int>? = null, includeContent : Boolean? = true) {
        viewModelScope.launch {
            val apiResult = repositoryHome.applyGetCall(
                accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdHJrLWFjY2Vzc1Rva2VuIiwidG9rZW5fdHlwZSI6IkFDQ0VTU19UT0tFTiIsImlkIjoiNjEiLCJpYXQiOjE2NTg5MjAxOTUsImV4cCI6MTY1ODkyMzc5NX0.ZjdvkYBxQckVyHJRdj2XG8XuZAFlpRhsiyrp5RkXMr0",
                applyIds = applyIds,
                includeContent = includeContent)

            val _tmp = MutableLiveData<MutableMap<Int, ArrayList<HomeDetailItem>>>().apply {
                value = mutableMapOf(
                    3039 to arrayListOf<HomeDetailItem>(
                        HomeDetailItem(progressType = 0,progressName = "서류", itemType = ProgressItemBodyType.REVIEW, freeTitle = "freetitle1", freeBody = "free1", totalReviewBody = "hello_0##################$$$$$$$$$$##########%%%%%%%%%", questionBody = null, answerBody = null, qnaReviewBody = null, progressIsPassing = IsPassing.SUCCESS),
                        HomeDetailItem(progressType = 1,progressName = "사전 과제", itemType = ProgressItemBodyType.QNA, freeTitle = null, freeBody = null, totalReviewBody = "hello_1", questionBody = "q2", answerBody = "a2", qnaReviewBody = "qnaReview2", progressIsPassing = IsPassing.SUCCESS),
                        HomeDetailItem(progressType = 1,progressName = "포트폴리오", itemType = ProgressItemBodyType.QNA, freeTitle = null, freeBody = null, totalReviewBody = "hello_1", questionBody = "q2", answerBody = "a2", qnaReviewBody = "qnaReview2", progressIsPassing = IsPassing.SUCCESS),
                    )
                )
            }

            if (apiResult.code() == 200) {
                val newStageTitleArrayList = arrayListOf<String>()
                val apply = apiResult.body()!!.applies[0]
                _homeApplyIdContent.value = apply

                val forDetailDisplayArrayList = arrayListOf<HomeDetailItem>()
                val stageProgress = apply.stage_progress

                for (stage in stageProgress) { // 단위는 stage 당 (서류 당, 1차 면접 당...)
                    //val stageEventAt = stage.event_at // mvp 안쓰임
                    val stageTheId = stage.id // '서류' 그 자체 id ( 10000, 10001 ... )
                    val stageOrder = stage.order
                    val stageStatus = stage.status // status type => FAIL, IN_PROGRESS, NOT_STARTED, PASS 4가지
                    val stageTitle = stage.stage_title
                    val stageContents = stage.stage_contents
                    val stageRealId = stage.stage_id // 그 stage 의 실질 살아있는 id

                    for (sContent in stageContents) {
                        val contentOrder = sContent.order
                        val contentId = sContent.id
                        val contentContentType = sContent.content_type // FREE_FORM, NOT_DEFINED, OVERALL, QNA
                        val contentContent = sContent.content // * contentContentType 에 따른 문자열 처리 해주어야함,
                        // QNA = "{ \"q\": \"질문1\", \"a\": \"답변1\", \"f\": \"피드백\",}" /// FREE_FORM => "{ \"t\": \"자유 예시 타이틀\", \b\": \"자유 예시 컨텐츠\"}", OVERALL => "종합후기 예시 텍스트1"

                        when (contentContentType) {
                            ProgressItemBodyTypeTmp.OVERALL.toString() -> {

                            }
                            ProgressItemBodyTypeTmp.QNA.toString() -> {

                            }
                            ProgressItemBodyTypeTmp.FREE_FORM.toString() -> {

                            }
                            ProgressItemBodyTypeTmp.NOT_DEFINED.toString() -> {
                                forDetailDisplayArrayList.add(HomeDetailItem(progressType = 0,
                                    progressName = "",
                                    itemType =,
                                    freeTitle = null,
                                    freeBody = null,
                                    totalReviewBody = null,
                                    questionBody = null,
                                    answerBody = null,
                                    qnaReviewBody = null,
                                    progressIsPassing = null
                                ))

                            }
                        }


                        ///////////////////////////////////////////////////
                        newStageTitleArrayList.add(stageTitle)
                    }


                    _homeProgressNameDetail.value = Event(newStageTitleArrayList)
                    Log.d("_homeApplyIdContent" ,"${_homeProgressNameDetail.value}")
                    _getApplyDetailFail.value = Event(false)
                }
            } else {
                _getApplyDetailFail.value = Event(true)
            }
        }


    }

//    fun updateStageProgress(){
//        viewModelScope.launch {
//            val apiResult = repositoryHome.updateStageProgressCall(
//                accessToken = "",
//                stageProgressRequest = qwe,
//           )
//
//            if (apiResult.code() == 200) {
//                val getResult = apiResult.body()!!
//
//            } else {
//
//            }
//        }
//    }



//    fun deleteStageProgress(deleteIds : Array<Int>){ // mvp 에 안쓰임
//        viewModelScope.launch {
//            val apiResult = repositoryHome.deleteStageProgressCall(
//                accessToken = "",
//                ids = deleteIds
//            )
//            if (apiResult.code() == 200) {
//                val getResult = apiResult.body()!!
//            } else {
//            }
//        }
//    }

}