package com.example.atracker.ui.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atracker.model.dto.SignRequest
import com.example.atracker.model.repository.RepositoryLogin
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    val repositoryLogin = RepositoryLogin()



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


    fun postSign() {
        viewModelScope.launch {
            val apiResult = repositoryLogin.signPostCall(signRequest = SignRequest(
                access_token = "",
                experience_type = _signUpCareer.value!!,
                job_position = _signUpPosition.value!!,
                nick_name = _signUpNickName.value!!,
                sso = "GOOGLE"
                )
            )

            if (apiResult.code() == 200) {
                val getResult = apiResult.body()

            } else {

            }

        }
    }


}