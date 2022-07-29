package com.example.atracker.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atracker.BuildConfig
import com.example.atracker.model.dto.*
import com.example.atracker.model.local.App
import com.example.atracker.model.repository.RepositoryHome
import com.example.atracker.model.repository.RepositoryMyPage
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
    val repositoryMyPage = RepositoryMyPage()

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

    private val _stageProgressesPutFail = MutableLiveData<Event<Boolean>>()
    val stageProgressesPutFail : LiveData<Event<Boolean>> = _stageProgressesPutFail

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

    private val _homeDetailContentForDisplay = MutableLiveData<ArrayList<HomeDetailItem>>().apply { // map 아님 주의
        arrayListOf<HomeDetailItem>()
    }
    val homeDetailContentForDisplay : LiveData<ArrayList<HomeDetailItem>> = _homeDetailContentForDisplay


    private val _stageProgressRequest = MutableLiveData<StageProgressRequest>()
    val stageProgressRequest : LiveData<StageProgressRequest> = _stageProgressRequest

    private val _arrayListStageProgresse = MutableLiveData<ArrayList<StageProgresse>>()
    val arrayListStageProgresse : LiveData<ArrayList<StageProgresse>> = _arrayListStageProgresse

//    private val _arrayListDeletedContent = MutableLiveData<ArrayList<DeletedContent>>()
//    val arrayListDeletedContent : LiveData<ArrayList<DeletedContent>> = _arrayListDeletedContent
//
//    private val _arrayListNewContent = MutableLiveData<ArrayList<NewContent>>()
//    val arrayListNewContent : LiveData<ArrayList<NewContent>> = _arrayListNewContent
//
//    private val _arrayListUpdatedContent = MutableLiveData<ArrayList<UpdatedContent>>()
//    val arrayListUpdatedContent : LiveData<ArrayList<UpdatedContent>> = _arrayListUpdatedContent



    private val _homeApplyIdContent = MutableLiveData<Apply>().apply {
        value = Apply(apply_id = -1, company_id = 0, company_name = "", job_position = "", job_type = "", stage_progress = listOf())
    }
    val homeApplyIdContent : LiveData<Apply> = _homeApplyIdContent

    private val _portfolioNum1 = MutableLiveData<Int>() // 서류 전형
    val portfolioNum1 : LiveData<Int> = _portfolioNum1

    private val _portfolioNum2 = MutableLiveData<Int>() // 면접 전형
    val portfolioNum2 : LiveData<Int> = _portfolioNum2

    private val _portfolioNum3 = MutableLiveData<Int>() // 최종 전형
    val portfolioNum3 : LiveData<Int> = _portfolioNum3

    private val _portfolioNumTotal = MutableLiveData<Int>() // 토탈 합격률
    val portfolioNumTotal : LiveData<Int> = _portfolioNumTotal


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

        _portfolioNum1.value = 0
        _portfolioNum2.value = 0
        _portfolioNum3.value = 0
        _portfolioNumTotal.value = 0

        _arrayListStageProgresse.value = arrayListOf()
        _stageProgressRequest.value = StageProgressRequest(stage_progresses = arrayListOf())
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

    fun decideIsPassing(stageStatusString : String?) : IsPassing {
        when (stageStatusString) {
            IsPassing.PASS.toString() -> return IsPassing.PASS
            IsPassing.FAIL.toString() -> return IsPassing.FAIL
            IsPassing.NOT_STARTED.toString() -> return IsPassing.NOT_STARTED
            IsPassing.IN_PROGRESS.toString() -> return IsPassing.IN_PROGRESS
            else -> return IsPassing.NOT_STARTED
        }
    }

    fun decideProgressItemBodyType(contentContentTypeString : String?) : ProgressItemBodyType {
        when (contentContentTypeString) {
            ProgressItemBodyType.NOT_DEFINED.toString() -> return ProgressItemBodyType.NOT_DEFINED
            ProgressItemBodyType.FREE_FORM.toString() -> return ProgressItemBodyType.FREE_FORM
            ProgressItemBodyType.QNA.toString() -> return ProgressItemBodyType.QNA
            ProgressItemBodyType.OVERALL.toString() -> return ProgressItemBodyType.OVERALL
            else -> return ProgressItemBodyType.NOT_DEFINED
        }
    }

    fun stageContentParsing(contentContentString : String?, decidedContentContentType : ProgressItemBodyType) : ArrayList<String?>  {
//        var freeTitle : String? = null // FREE_FORM
//        var freeBody : String? = null // FREE_FORM
//        var totalReviewBody : String? = null // OVERALL
//        var questionBody : String? = null // QNA
//        var answerBody : String? = null // QNA
//        var qnaReviewBody : String? = null // QNA

        val allParsedContentStringList = arrayListOf<String?>(null, null, null, null, null, null, null)

        val dropString = contentContentString?.let {it.drop(1).dropLast(1)} // "{,}" 날리기 및 null 처리
        val splitList = dropString?.let { it.split(',') } // ',' 제거 및 null 처리
        Log.d("stageContentParsing", "${dropString}, ${splitList}")

        when (decidedContentContentType) {
            ProgressItemBodyType.NOT_DEFINED -> {

            }
            ProgressItemBodyType.FREE_FORM -> {
                splitList?.let { spList ->
                    for (s in spList) {
                        val standard = s[1]
                        val finalContentString = s.substring(5,s.lastIndex)
                        when (standard) {
                            't' -> allParsedContentStringList[0] = finalContentString
                            'b' -> allParsedContentStringList[1] = finalContentString
                        }
                    }
                }
            }
            ProgressItemBodyType.QNA -> {
                splitList?.let { spList ->
                    for (s in spList) {
                        val standard = s[1]
                        val finalContentString = s.substring(5,s.lastIndex)
                        when (standard) {
                            'q' -> allParsedContentStringList[3] = finalContentString
                            'a' -> allParsedContentStringList[4] = finalContentString
                            'f' -> allParsedContentStringList[5] = finalContentString
                        }
                    }
                }
            }
            ProgressItemBodyType.OVERALL -> {
                allParsedContentStringList[2] = contentContentString
            }
        }

        return allParsedContentStringList
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    fun getCompanyInfo (searchWord : String, page : Int, size : Int = 10, isScroll : Boolean) { // 예외 처리 보류 (220726)
        viewModelScope.launch {
            val apiResult = repositoryHome.companySearchPostCall(accessToken = App.prefs.getValue(BuildConfig.ACCESS_LOCAL_TOKEN)!!,
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
                testSignInHome()
            }
        }
    }


    fun addCompanyInfo () {
        viewModelScope.launch {
            val apiResult = repositoryHome.companyAddPostCall(accessToken = App.prefs.getValue(BuildConfig.ACCESS_LOCAL_TOKEN)!!,
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
                testSignInHome()
            }
        }
    }


    fun getStage() {
        viewModelScope.launch {
            val apiResult = repositoryHome.stageGetCall(accessToken = App.prefs.getValue(BuildConfig.ACCESS_LOCAL_TOKEN)!!)

            if (apiResult.code() == 200) {
                val getResult = apiResult.body()!!
                Log.d("getResult", "${getResult}")
                _homeAddStagesContent.value = getResult
                //ApiExceptionUtil._apiExceptionFlag.value = Event(true)

            } else {
                _homeAddStagesContent.value = listOf()
                testSignInHome()
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
            val apiResult = repositoryHome.createApplyPostCall(accessToken = App.prefs.getValue(BuildConfig.ACCESS_LOCAL_TOKEN)!!, createApplyRequest = createApplyRequest )
            Log.d("getResult_1", "${apiResult}")
            if (apiResult.code() == 200) {
                switch(_postApplyFlag)
                _updateApplyFail.value = Event(false)
            } else {
                _updateApplyFail.value = Event(true)
                testSignInHome()
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
            val apiResult = repositoryHome.updateApplyPutCall(accessToken = App.prefs.getValue(BuildConfig.ACCESS_LOCAL_TOKEN)!!, updateApplyRequest = updateApplyRequest )
            Log.d("getResult_33", "${apiResult}")
            if (apiResult.code() == 200) {
                switch(_postApplyFlag)
                _updateApplyFail.value = Event(false)
            } else {
                _updateApplyFail.value = Event(true)
                testSignInHome()
            }
        }
    }


    fun deleteApply(deleteIds : Array<Int>) {
        viewModelScope.launch {
            val apiResult = repositoryHome.deleteApplyCall(accessToken = App.prefs.getValue(BuildConfig.ACCESS_LOCAL_TOKEN)!!, ids = deleteIds )
            Log.d("deleteApply", "${apiResult}")
            if (apiResult.code() == 200) {
                switch(_postApplyFlag)
                _deleteApplyFail.value = Event(false)
            } else {
                //switch(_deleteApplyFail)
                _deleteApplyFail.value = Event(true)
                testSignInHome()
            }
        }
    }


    fun getApplyDisplay(applyIds : Array<Int>? = null, includeContent : Boolean? = false) { // 예외 처리 보류 (220725)
        viewModelScope.launch {
            val apiResult = repositoryHome.applyGetCall(
                accessToken = App.prefs.getValue(BuildConfig.ACCESS_LOCAL_TOKEN)!!,
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
                        if (stageStatus == IsPassing.FAIL.toString() && success) // status type => FAIL, IN_PROGRESS, NOT_STARTED, PASS 4가지
                            success = false

                        if (stageStatus == IsPassing.PASS.toString())
                            myProgress += 1

                        detailMyProgress += 1
                    }

                    newArrayList.add(HomeProgressItem(companyTitle = companyTitle, jobType = jobType, myProgress = myProgress, totalProgress = totalProgress, success = success, applyId = applyId))
                    detailArrayList.add(HomeProgressItem(companyTitle = companyTitle, jobType = jobType, myProgress = detailMyProgress, totalProgress = totalProgress, success = detailSuccess, applyId = applyId))
                }
                _homeDisplayArrayList.value = newArrayList
                _homeDetailArrayList.value = detailArrayList
            } else {
                testSignInHome()
            }
        }
    }



    fun getApplyDetail(applyIds : Array<Int>? = null, includeContent : Boolean? = true) {
        viewModelScope.launch {
            val apiResult = repositoryHome.applyGetCall(
                accessToken = App.prefs.getValue(BuildConfig.ACCESS_LOCAL_TOKEN)!!,
                applyIds = applyIds,
                includeContent = includeContent)

            if (apiResult.code() == 200) {
                val newStageTitleArrayList = arrayListOf<String>()
                val apply = apiResult.body()!!.applies[0]
                _homeApplyIdContent.value = apply

                val forDetailDisplayArrayList = arrayListOf<HomeDetailItem>()
                val stageProgress = apply.stage_progress

                for (stage in stageProgress) { // 단위는 stage 당 (서류 당, 1차 면접 당...)
                    //val stageEventAt = stage.event_at // mvp 안쓰임
                    val stageOrder = stage.order
                    val stageStatus = stage.status // String 상태 // status type => FAIL, IN_PROGRESS, NOT_STARTED, PASS 4가지, 서버에서 받아 올때는 String 임.
                    val stageTitle = stage.stage_title
                    val stageContents = stage.stage_contents
                    val stageRealId = stage.id // 그 stage 의 실질 살아있는 id
                    val stageTheId = stage.stage_id // '서류' 그 자체 id ( 10000, 10001 ... )

                    val decidedStageStatus = decideIsPassing(stageStatus)

                    _arrayListStageProgresse.value!!.add(StageProgresse(
                        deleted_contents = arrayListOf(),
                        id = stageRealId,
                        new_contents = arrayListOf(),
                        status = stageStatus,
                        updated_contents = arrayListOf()
                    )
                    )

                    for (sContent in stageContents) {
                        val contentOrder = sContent.order
                        val contentId = sContent.id
                        val contentContentType = sContent.content_type // FREE_FORM, NOT_DEFINED, OVERALL, QNA, 서버로 받아 올때는 string 임
                        val contentContent = sContent.content // * contentContentType 에 따른 문자열 처리 해주어야함,
                        // QNA = "{ \"q\": \"질문1\", \"a\": \"답변1\", \"f\": \"피드백\",}" /// FREE_FORM => "{ \"t\": \"자유 예시 타이틀\", \b\": \"자유 예시 컨텐츠\"}", OVERALL => "종합후기 예시 텍스트1"

                        val decidedContentContentType = decideProgressItemBodyType(contentContentType)
                        val allParsedContentStringList = stageContentParsing(contentContent, decidedContentContentType)

//                        _arrayListStageProgresse.value!!.add(StageProgresse(
//                            deleted_contents = arrayListOf(),
//                            id = stageRealId,
//                            new_contents = arrayListOf(),
//                            status = stageStatus,
//                            updated_contents = arrayListOf()
//                        )
//                        )

                        when (contentContentType) {
                            ProgressItemBodyType.OVERALL.toString() -> {
                                forDetailDisplayArrayList.add(HomeDetailItem(
                                    progressType = stageOrder, progressName = stageTitle, itemType = ProgressItemBodyType.OVERALL,
                                    freeTitle = allParsedContentStringList[0], freeBody = allParsedContentStringList[1],
                                    totalReviewBody = allParsedContentStringList[2],
                                    questionBody = allParsedContentStringList[3], answerBody = allParsedContentStringList[4], qnaReviewBody = allParsedContentStringList[5],
                                    progressIsPassing = decidedStageStatus,
                                    contentOrder = contentOrder, contentId = contentId,
                                    stageRealId = stageRealId
                                ))
                            }
                            ProgressItemBodyType.QNA.toString() -> {
                                forDetailDisplayArrayList.add(HomeDetailItem(
                                    progressType = stageOrder, progressName = stageTitle, itemType = ProgressItemBodyType.QNA,
                                    freeTitle = allParsedContentStringList[0], freeBody = allParsedContentStringList[1],
                                    totalReviewBody = allParsedContentStringList[2],
                                    questionBody = allParsedContentStringList[3], answerBody = allParsedContentStringList[4], qnaReviewBody = allParsedContentStringList[5],
                                    progressIsPassing = decidedStageStatus,
                                    contentOrder = contentOrder, contentId = contentId,
                                    stageRealId = stageRealId
                                ))
                            }
                            ProgressItemBodyType.FREE_FORM.toString() -> {
                                forDetailDisplayArrayList.add(HomeDetailItem(
                                    progressType = stageOrder, progressName = stageTitle, itemType = ProgressItemBodyType.FREE_FORM,
                                    freeTitle = allParsedContentStringList[0], freeBody = allParsedContentStringList[1],
                                    totalReviewBody = allParsedContentStringList[2],
                                    questionBody = allParsedContentStringList[3], answerBody = allParsedContentStringList[4], qnaReviewBody = allParsedContentStringList[5],
                                    progressIsPassing = decidedStageStatus,
                                    contentOrder = contentOrder, contentId = contentId,
                                    stageRealId = stageRealId
                                ))
                            }
                            ProgressItemBodyType.NOT_DEFINED.toString() -> {
                                forDetailDisplayArrayList.add(HomeDetailItem(
                                    progressType = stageOrder, progressName = stageTitle, itemType = ProgressItemBodyType.NOT_DEFINED,
                                    freeTitle = allParsedContentStringList[0], freeBody = allParsedContentStringList[1],
                                    totalReviewBody = allParsedContentStringList[2],
                                    questionBody = allParsedContentStringList[3], answerBody = allParsedContentStringList[4], qnaReviewBody = allParsedContentStringList[5],
                                    progressIsPassing = decidedStageStatus,
                                    contentOrder = contentOrder, contentId = contentId,
                                    stageRealId = stageRealId
                                ))
                            }
                        }
                    }

                    newStageTitleArrayList.add(stageTitle)
                }
                _homeProgressNameDetail.value = Event(newStageTitleArrayList)
                _homeDetailContentForDisplay.value = forDetailDisplayArrayList

                _getApplyDetailFail.value = Event(false)
            } else {
                _getApplyDetailFail.value = Event(true)
                testSignInHome()
            }
        }
    }


    fun getMyApplyPfratio() { // getApplyDisplay 와 같은 이유로 일단 예외 처리 보류
        viewModelScope.launch {
            val apiResult = repositoryMyPage.myApplyPfratioGetCall(accessToken = App.prefs.getValue(BuildConfig.ACCESS_LOCAL_TOKEN)!!)
            if (apiResult.code() == 200) {
                val getResult = apiResult.body()!!
                Log.d("getMyApplyPfratio", "${getResult}")
                _portfolioNum1.value = getResult.pf1
                _portfolioNum2.value = getResult.pf2
                _portfolioNum3.value = getResult.pf3
                _portfolioNumTotal.value = getResult.pf_total
            } else {
                testSignInHome()
            }
        }
    }


    fun progressNameToStageRealId(progressName : String) : Int {
        return _homeDetailContentForDisplay.value!!.find { it.progressName == progressName }!!.stageRealId
    }


    fun deletedContentUpdate(progressName : String, deletedContent : DeletedContent) {
        for (sp in _arrayListStageProgresse.value!!) {
            val stageRealId = sp.id
            if (stageRealId == progressNameToStageRealId(progressName)) {
                sp.deleted_contents.add(deletedContent)
            }
        }
    }

    fun newContentUpdate(progressName : String, newContent : NewContent) {
        for (sp in _arrayListStageProgresse.value!!) {
            val stageRealId = sp.id
            if (stageRealId == progressNameToStageRealId(progressName)) {
                sp.new_contents.add(newContent)
            }
        }
    }

    fun updateContentsUpdate(progressName : String, updatedContent : UpdatedContent) {
        for (sp in _arrayListStageProgresse.value!!) {
            val stageRealId = sp.id
            if (stageRealId == progressNameToStageRealId(progressName)) {
                sp.updated_contents.add(updatedContent)
            }
        }
    }

    fun isPassingUpdate(progressName : String, inProgressIsChecked : Boolean, failIsChecked: Boolean, passIsChecked : Boolean ) {
        val finalStatus : String
        when {
            inProgressIsChecked -> finalStatus = "IN_PROGRESS"
            failIsChecked -> finalStatus = "FAIL"
            passIsChecked -> finalStatus = "PASS"
            else -> finalStatus = "NOT_STARTED"
        }

        for (sp in _arrayListStageProgresse.value!!) {
            val stageRealId = sp.id
            if (stageRealId == progressNameToStageRealId(progressName)) {
                sp.status = finalStatus
            }
        }
    }


    fun setStageProgressRequest() {
        _stageProgressRequest.value!!.stage_progresses = _arrayListStageProgresse.value!!
        Log.d("qwe_final_stageProgressRequest", "${_stageProgressRequest.value}")
    }

    fun updateStageProgress(){
        Log.d("qweqwe_Call", "${_stageProgressRequest.value!!}")

        viewModelScope.launch {
            val apiResult = repositoryHome.updateStageProgressCall(
                accessToken = App.prefs.getValue(BuildConfig.ACCESS_LOCAL_TOKEN)!!,
                stageProgressRequest = _stageProgressRequest.value!!,
           )
            if (apiResult.code() == 200) {
                _stageProgressesPutFail.value = Event(false)
            } else {
                _stageProgressesPutFail.value = Event(true)
            }
        }
    }

    fun clearStageProgress() {
        Log.d("qwe_clear", "qwe_clear")
        _arrayListStageProgresse.value = arrayListOf()
        _stageProgressRequest.value = StageProgressRequest(stage_progresses = arrayListOf())
    }


    fun testSignInHome() { // refresh 역할
        Log.d("test_sign", "homeviewmodel")

        viewModelScope.launch {
            val apiResult = repositorySign.testSignCall(
                email = App.prefs.getValue(BuildConfig.EMAIL)!!,
                experience_type = "EXPERIENCED",
                job_position = "개발자",
                nick_name = "닉네임1"
            )

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