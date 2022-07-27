package com.example.atracker.ui.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atracker.BuildConfig
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
    }


    fun postSignUp() { // 처음 회원 가입시
        viewModelScope.launch {
            val apiResult = repositorySign.signPostCall(signRequest = SignRequest(
                access_token = "",
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







}