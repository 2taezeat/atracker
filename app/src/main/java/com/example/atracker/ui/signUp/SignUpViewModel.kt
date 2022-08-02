package com.example.atracker.ui.signUp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atracker.BuildConfig
import com.example.atracker.model.dto.ExperienceType
import com.example.atracker.model.dto.SignRequest
import com.example.atracker.model.dto.TokenRefreshRequest
import com.example.atracker.model.local.App
import com.example.atracker.model.repository.RepositorySign
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    val repositorySign = RepositorySign()



    val _signUpNickName = MutableLiveData<String>().apply {
        value = ""
    }
    val signUpNickName : LiveData<String> = _signUpNickName


    val _signUpPosition = MutableLiveData<String>().apply {
        value = ""
    }
    val signUpPosition : LiveData<String> = _signUpPosition

    val _signUpCareer = MutableLiveData<String>().apply {
        value = ""
    }
    val signUpCareer : LiveData<String> = _signUpCareer


    val careerAgeItems = MutableLiveData<List<String>>()

    init {
        careerAgeItems.value = listOf("신입", "경력")
    }


    fun setUserCareerPosition(position : Int) {
        _signUpCareer.value = careerAgeItems.value!![position]
        when (_signUpCareer.value) {
            "경력" -> _signUpCareer.value = "EXPERIENCED"
            "신입" -> _signUpCareer.value = "NOT_EXPERIENCED"
        }
    }


    fun postSignUp() { // 처음 회원 가입시
        viewModelScope.launch {
            val googleAccessToken = App.prefs.getValue(BuildConfig.FROM_GOOGLE_ACCESS_TOKEN)!!
            val apiResult = repositorySign.signPostCall(signRequest = SignRequest(
                access_token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjE1NDllMGFlZjU3NGQxYzdiZGQxMzZjMjAyYjhkMjkwNTgwYjE2NWMiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI5MzcwODU5ODcxNDgtdHYxaHE4YzMzOTAzb2ZjN2diOTduMzByaDcxazg4aG0uYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI5MzcwODU5ODcxNDgtaGc5NTgza2g5Mmw0N3JqbGlhOXZuZzJ2NWVnN2I1NGIuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDM3NDY3NDUzNjc5NDQwMjc1MzgiLCJoZCI6ImtvcmVhLmFjLmtyIiwiZW1haWwiOiJ0eXVzZGM3NzJAa29yZWEuYWMua3IiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IuKAjeydtO2DnOqyvVsg7ZWZ67aA7KG47JeFIC8g7Lu07ZOo7YSw7ZWZ6rO8IF0iLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EvQUl0YnZtbm1HSWo5cU9fVWxVWnFocFNON0lNMzJ1WERJaC1XbTVVMlpsYW89czk2LWMiLCJnaXZlbl9uYW1lIjoi7J207YOc6rK9WyDtlZnrtoDsobjsl4UgLyDsu7Ttk6jthLDtlZnqs7wgXSIsImZhbWlseV9uYW1lIjoi4oCNIiwibG9jYWxlIjoia28iLCJpYXQiOjE2NTk0Mjg1NDcsImV4cCI6MTY1OTQzMjE0N30.QCYhulroW8AY3CuvxLyy_3UgZ1bP8f9iFswZ3tICaCsbs7VBQBaEpepgNgnaPLArsdqnJrIlHyAECq1VDZE-6BP9HugL95TneCfWd6fuNBp8KqZ68FN0ZVPj1q7rkMheiGGe9rAXovYtsJfGSAOYyxOiOGdueQ3x2V4EAXzP679miqJUVciGumUGH2kPDUbsm43OXVqiTGUUAw2F-vFInYRxEsB_VV4Zc17BOo6QQAJnbH03Hs_WF-0fEBNZWeD5Gs5LJMVU-LFyCqCbSq78ZjEdtqkiyKedktIuLGF0f0Bgibn1ONaJVDFArkNlkVeodxgrlrGOwiZz8apHL_uK7g",
                experience_type = _signUpCareer.value!!,
                job_position = _signUpPosition.value!!,
                nick_name = _signUpNickName.value!!,
                sso = "GOOGLE"
                )
            )

            if (apiResult.code() == 200) {
                val getResult = apiResult.body()!!
                val at = getResult.access_token
                val rt = getResult.refresh_token

                //val tokenDrop = bodyToken.drop(1).dropLast(1)
                App.prefs.setValue(BuildConfig.ACCESS_LOCAL_TOKEN, "Bearer $at") // * drop 과 bearer 해야되는지 확인해야됨
                App.prefs.setValue(BuildConfig.REFRESH_LOCAL_TOKEN, "Bearer $rt")

            } else {

            }

        }
    }


    fun refreshToken() { // refresh token 호출
        viewModelScope.launch {
            val apiResult = repositorySign.refreshTokenCall(
                tokenRefreshRequest = TokenRefreshRequest( App.prefs.getValue(BuildConfig.REFRESH_LOCAL_TOKEN)!! )
            )


            if (apiResult.code() == 200) {
                val getResult = apiResult.body()!!
                val at = getResult.access_token

                // new access_token 받아서 update
                //val tokenDrop = bodyToken.drop(1).dropLast(1)
                App.prefs.setValue(BuildConfig.ACCESS_LOCAL_TOKEN, "Bearer $at") // * drop 과 bearer 해야되는지 확인해야됨

            } else {

            }

        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////


    fun testSignInSign() {
        Log.d("test_sign", "SignUp")

        viewModelScope.launch {
            val apiResult = repositorySign.testSignCall(
                email = App.prefs.getValue(BuildConfig.EMAIL)!!,
                experience_type = _signUpCareer.value!!,
                job_position = _signUpPosition.value!!,
                nick_name = _signUpNickName.value!!
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