package com.cmc.atracker.ui.signUp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc.atracker.BuildConfig
import com.cmc.atracker.model.dto.SignRequest
import com.cmc.atracker.model.dto.TokenRefreshRequest
import com.cmc.atracker.model.local.App
import com.cmc.atracker.model.repository.RepositorySign
import com.cmc.atracker.utils.Event
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

    private val _postSignUpFail = MutableLiveData<Event<Boolean>>()
    val postSignUpFail : LiveData<Event<Boolean>> = _postSignUpFail

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
            val apiResult = repositorySign.signPostCall(signRequest = SignRequest(access_token = googleAccessToken, experience_type = _signUpCareer.value!!, job_position = _signUpPosition.value!!, nick_name = _signUpNickName.value!!, sso = "GOOGLE"))

            if (apiResult.code() == 200) {
                val getResult = apiResult.body()!!
                val at = getResult.access_token
                val rt = getResult.refresh_token

                //val tokenDrop = bodyToken.drop(1).dropLast(1)
                Log.d("postSign_at", "${at}")
                Log.d("postSign_rt", "${rt}")

                App.prefs.setValue(BuildConfig.ACCESS_LOCAL_TOKEN, "Bearer $at") // * drop 과 bearer 해야되는지 확인해야됨
                App.prefs.setValue(BuildConfig.REFRESH_LOCAL_TOKEN, "Bearer $rt")
                _postSignUpFail.value = Event(false)
            } else {
                _postSignUpFail.value = Event(true)
            }
        }
    }


    fun refreshToken() { // refresh token 호출
        viewModelScope.launch {
            val apiResult = repositorySign.refreshTokenCall(tokenRefreshRequest = TokenRefreshRequest(App.prefs.getValue(BuildConfig.REFRESH_LOCAL_TOKEN)!!))

            if (apiResult.code() == 200) {
                val getResult = apiResult.body()!!
                val at = getResult.access_token

                App.prefs.setValue(BuildConfig.ACCESS_LOCAL_TOKEN, "Bearer $at") // * drop 과 bearer 해야되는지 확인해야됨

            } else {

            }

        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    

}