package com.example.atracker.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atracker.model.dto.*
import com.example.atracker.model.repository.RepositoryHome
import com.example.atracker.utils.Event
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
//            arrayListOf("1차 면접", "2차 면접", "3차 면접", "4차 면접", "5차 면접",  "6차 면접", ),
//            arrayListOf("1차 면접", "2차 면접", "3차 면접" ),
//            arrayListOf("1차 면접", "2차 면접", "3차 면접", "4차 면접", "5차 면접" )
//        )
//    }
//
//    val homeWriteProgressSelectArrayList : LiveData<ArrayList<ArrayList<String>>> = _homeWriteProgressSelectArrayList


    private val _homeWriteProgressSelectedMap = MutableLiveData<MutableMap<Int, ArrayList<String>>>().apply {
        value = mutableMapOf(
            2545 to arrayListOf("1차 면접", "2차 면접", "3차 면접", "4차 면접", "5차 면접",  "6차 면접"),
            1 to arrayListOf("1차 면접", "2차 면접", "3차 면접" ),
            2 to arrayListOf("1차 면접", "2차 면접", "3차 면접", "4차 면접", "5차 면접" )
        )
    }

    val homeWriteProgressSelectedMap : LiveData<MutableMap<Int, ArrayList<String>>> = _homeWriteProgressSelectedMap



    private val _homeProgressNameDetail = MutableLiveData<Event<ArrayList<String>>>()

    val homeProgressNameDetail : LiveData<Event<ArrayList<String>>> = _homeProgressNameDetail


    private val _homeProgressNameWrite = MutableLiveData<ArrayList<String>>()

    val homeProgressNameWrite : LiveData<ArrayList<String>> = _homeProgressNameWrite


    private val _tmp = MutableLiveData<MutableSet<Int>>()
    val tmp : LiveData<MutableSet<Int>> = _tmp


    private val _tmp2 = MutableLiveData<MutableSet<Int>>()

    val tmp2 : LiveData<MutableSet<Int>> = _tmp2


    private val _tmp3 = MutableLiveData<MutableSet<Int>>()

    val tmp3 : LiveData<MutableSet<Int>> = _tmp3



    fun pos(boolean: Boolean, num : Int){
        val new = _tmp.value!!
        val new2 = _tmp2.value!!

        if (boolean){
            new.add(num)
            new2.remove(num)
        } else {
            new.remove(num)
            new2.add(num)
        }

        _tmp.value = new
        _tmp2.value = new2
        Log.d("tmp_pos", "${_tmp.value}, ${_tmp2.value}")
    }


    fun refresh(){
        val new = mutableSetOf<Int>()
        _tmp.value = new

        val new3 = _tmp3.value!!.toMutableSet()
        _tmp2.value = new3!!
        Log.d("refresh", "${_tmp2.value}, ${_tmp3.value}")
    }


    fun setId(chipId : Int){
        _tmp3.value!!.add(chipId)
    }


    init {
        val instant = Instant.now()
        _zonedDateTime.value = instant.atZone(TimeZone.getDefault().toZoneId())
        workTypeItems.value = listOf("PERMANENT", "TEMPORARY", "INTERN")
        initTimeDateCurrent()
        _tmp.value = mutableSetOf()
        _tmp2.value = mutableSetOf()
        _tmp3.value = mutableSetOf()
    }




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
//            2519 to arrayListOf<HomeDetailItem>(
//                HomeDetailItem(progressType = 0,progressName = "1차 면접", itemType = ProgressItemBodyType.REVIEW, freeTitle = "freetitle1", freeBody = "free1", totalReviewBody = "hello_0##################$$$$$$$$$$##########%%%%%%%%%", questionBody = null, answerBody = null, qnaReviewBody = null, progressIsPassing = IsPassing.SUCCESS),
//                HomeDetailItem(progressType = 1,progressName = "2차 면접", itemType = ProgressItemBodyType.QNA, freeTitle = null, freeBody = null, totalReviewBody = "hello_1", questionBody = "q2", answerBody = "a2", qnaReviewBody = "qnaReview2", progressIsPassing = IsPassing.SUCCESS),
//                HomeDetailItem(progressType = 2,progressName = "3차 면접", itemType = ProgressItemBodyType.FREE, freeTitle = "freetitle3", freeBody = "freeBody3", totalReviewBody = "hello_2\n\n\n\n\n\n\n\n\n\n\n\n\n", questionBody = null, answerBody = null, qnaReviewBody = null,  progressIsPassing = IsPassing.WAITING),
//                HomeDetailItem(progressType = 3,progressName = "4차 면접", itemType = ProgressItemBodyType.REVIEW, freeTitle = null, freeBody = null, totalReviewBody = "hello_3\n\n\n\n\n\n\n\n\n\n\n\n\n", questionBody = null, answerBody = null, qnaReviewBody = null,  progressIsPassing = IsPassing.FAIL),
//                HomeDetailItem(progressType = 3,progressName = "4차 면접", itemType = ProgressItemBodyType.QNA, freeTitle = null, freeBody = null, totalReviewBody = "hello_3", questionBody = "qna_3", answerBody = "answer_3", qnaReviewBody = "qna_review_body_3",  progressIsPassing = null),
//                HomeDetailItem(progressType = 3,progressName = "4차 면접", itemType = ProgressItemBodyType.FREE, freeTitle = "freetitle4", freeBody = "freeBody4", totalReviewBody = "hello_3", questionBody = null, answerBody = null, qnaReviewBody = null,  progressIsPassing = null),
//                HomeDetailItem(progressType = 4,progressName = "5차 면접", itemType = ProgressItemBodyType.FREE, freeTitle = null, freeBody = null, totalReviewBody = "hello_4", questionBody = null, answerBody = null, qnaReviewBody = null,  progressIsPassing = null),
//                HomeDetailItem(progressType = 5,progressName = "6차 면접", itemType = ProgressItemBodyType.FREE, freeTitle = null, freeBody = null, totalReviewBody = "hello_5", questionBody = null, answerBody = null, qnaReviewBody = null,  progressIsPassing = null),
//            ),
            2545 to arrayListOf<HomeDetailItem>(
                HomeDetailItem(progressType = 0,progressName = "서류", itemType = ProgressItemBodyType.REVIEW, freeTitle = "freetitle1", freeBody = "free1", totalReviewBody = "hello_0##################$$$$$$$$$$##########%%%%%%%%%", questionBody = null, answerBody = null, qnaReviewBody = null, progressIsPassing = IsPassing.SUCCESS),
                HomeDetailItem(progressType = 1,progressName = "사전 과제", itemType = ProgressItemBodyType.QNA, freeTitle = null, freeBody = null, totalReviewBody = "hello_1", questionBody = "q2", answerBody = "a2", qnaReviewBody = "qnaReview2", progressIsPassing = IsPassing.SUCCESS),
                HomeDetailItem(progressType = 1,progressName = "포트폴리오", itemType = ProgressItemBodyType.QNA, freeTitle = null, freeBody = null, totalReviewBody = "hello_1", questionBody = "q2", answerBody = "a2", qnaReviewBody = "qnaReview2", progressIsPassing = IsPassing.SUCCESS),
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



    fun switchFlagNull(){
        _postApplyFlag.value = null
    }


    fun getCompanyInfo (searchWord : String) {
        viewModelScope.launch {
            val apiResult = repositoryHome.companySearchPostCall(accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdHJrLWFjY2Vzc1Rva2VuIiwidG9rZW5fdHlwZSI6IkFDQ0VTU19UT0tFTiIsImlkIjoiNDAiLCJpYXQiOjE2NTg3MjY1MDQsImV4cCI6MTY1ODczMDEwNH0.ycfdM7NhFKtj7APOzxCJVL276_SuXP_tHa9dlX-wep8",
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


    fun addCompanyInfo () {
        viewModelScope.launch {
            val apiResult = repositoryHome.companyAddPostCall(accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdHJrLWFjY2Vzc1Rva2VuIiwidG9rZW5fdHlwZSI6IkFDQ0VTU19UT0tFTiIsImlkIjoiNDAiLCJpYXQiOjE2NTg3MjY1MDQsImV4cCI6MTY1ODczMDEwNH0.ycfdM7NhFKtj7APOzxCJVL276_SuXP_tHa9dlX-wep8",
                createCompanyRequest = listOf(CreateCompanyRequestItem(name = _companyWord.value!!))
            )

            if (apiResult.code() == 200) {
                val getResult = apiResult.body()!!.companies
                Log.d("addCompanyInfo", "${getResult}")
                _companyId.value = getResult[0].id
                _companyWord.value = getResult[0].name

            } else {

            }
        }
    }





    fun getStage() {
        viewModelScope.launch {
            val apiResult = repositoryHome.stageGetCall(accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdHJrLWFjY2Vzc1Rva2VuIiwidG9rZW5fdHlwZSI6IkFDQ0VTU19UT0tFTiIsImlkIjoiNDAiLCJpYXQiOjE2NTg3MjY1MDQsImV4cCI6MTY1ODczMDEwNH0.ycfdM7NhFKtj7APOzxCJVL276_SuXP_tHa9dlX-wep8")

            if (apiResult.code() == 200) {
                val getResult = apiResult.body()!!
                Log.d("getResult", "${getResult}")
                _homeAddStagesContent.value = getResult

                for (sc in getResult){
                    //_map.value!![sc.id] = false
                    //_tmp2.value!!.add()
                }

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
            val apiResult = repositoryHome.createApplyPostCall(accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdHJrLWFjY2Vzc1Rva2VuIiwidG9rZW5fdHlwZSI6IkFDQ0VTU19UT0tFTiIsImlkIjoiNDAiLCJpYXQiOjE2NTg3MjY1MDQsImV4cCI6MTY1ODczMDEwNH0.ycfdM7NhFKtj7APOzxCJVL276_SuXP_tHa9dlX-wep8", createApplyRequest = createApplyRequest )
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
            val apiResult = repositoryHome.updateApplyPutCall(accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdHJrLWFjY2Vzc1Rva2VuIiwidG9rZW5fdHlwZSI6IkFDQ0VTU19UT0tFTiIsImlkIjoiNDAiLCJpYXQiOjE2NTg3MjY1MDQsImV4cCI6MTY1ODczMDEwNH0.ycfdM7NhFKtj7APOzxCJVL276_SuXP_tHa9dlX-wep8", updateApplyRequest = updateApplyRequest )
            Log.d("getResult_33", "${apiResult}")
            if (apiResult.code() == 200) {
                switch(_postApplyFlag)
            }
        }
    }



    fun deleteApply(deleteIds : Array<Int>) {
        viewModelScope.launch {
            val apiResult = repositoryHome.deleteApplyCall(accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdHJrLWFjY2Vzc1Rva2VuIiwidG9rZW5fdHlwZSI6IkFDQ0VTU19UT0tFTiIsImlkIjoiNDAiLCJpYXQiOjE2NTg3MjY1MDQsImV4cCI6MTY1ODczMDEwNH0.ycfdM7NhFKtj7APOzxCJVL276_SuXP_tHa9dlX-wep8", ids = deleteIds )
            Log.d("deleteApply", "${apiResult}")
            if (apiResult.code() == 200) {
                switch(_postApplyFlag)
            }

        }
    }



    fun getApplyDisplay(applyIds : Array<Int>? = null, includeContent : Boolean? = false) {
        viewModelScope.launch {
            val apiResult = repositoryHome.applyGetCall(
                accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdHJrLWFjY2Vzc1Rva2VuIiwidG9rZW5fdHlwZSI6IkFDQ0VTU19UT0tFTiIsImlkIjoiNDAiLCJpYXQiOjE2NTg3MjY1MDQsImV4cCI6MTY1ODczMDEwNH0.ycfdM7NhFKtj7APOzxCJVL276_SuXP_tHa9dlX-wep8",
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



    fun getApplyDetail(applyIds : Array<Int>? = null, includeContent : Boolean? = true) {
        viewModelScope.launch {
            val apiResult = repositoryHome.applyGetCall(
                accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdHJrLWFjY2Vzc1Rva2VuIiwidG9rZW5fdHlwZSI6IkFDQ0VTU19UT0tFTiIsImlkIjoiNDAiLCJpYXQiOjE2NTg3MjY1MDQsImV4cCI6MTY1ODczMDEwNH0.ycfdM7NhFKtj7APOzxCJVL276_SuXP_tHa9dlX-wep8",
                applyIds = applyIds,
                includeContent = includeContent)

            if (apiResult.code() == 200) {
                val getResult = apiResult.body()!!.applies[0]
                _homeApplyIdContent.value = getResult

                val newStageTitleArrayList = arrayListOf<String>()

                val stageProgress = getResult.stage_progress
                for (s in stageProgress) {
                    newStageTitleArrayList.add(s.stage_title)
                }
                _homeProgressNameDetail.value = Event(newStageTitleArrayList)
                Log.d("_homeApplyIdContent" ,"${_homeProgressNameDetail.value}")
            }
        }


    }

//    fun updateStageProgress(){
//        viewModelScope.launch {
//            val apiResult = repositoryHome.updateStageProgressCall(
//                accessToken = "",
//                stageProgressRequest = applyIds,
//           )
//
//            if (apiResult.code() == 200) {
//                val getResult = apiResult.body()!!
//
//            }
//        }
//    }


    fun setWorkTypePosition(position : Int) {
        _workTypeSelection.value = position
        _workTypeWord.value = workTypeItems.value!![position]
    }

    fun setCompanyNameID(position: Int) {
        _companyWord.value = _companyList.value!![position].name
        _companyId.value = _companyList.value!![position].id
        Log.d("tttt123444", "${_companyWord.value}")
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
        mutableLiveData.value = if(mutableLiveData.value == null) true else !mutableLiveData.value!!
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

        Log.d("setHomeEdit", "${_homeAddSelectedStage.value}")

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

    fun setHomeProgressNameWrite(ori : ArrayList<String>){
        _homeProgressNameWrite.value = ori

    }



}