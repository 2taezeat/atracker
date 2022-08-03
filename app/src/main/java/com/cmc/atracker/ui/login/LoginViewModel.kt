package com.cmc.atracker.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc.atracker.BuildConfig
import com.cmc.atracker.model.dto.LoginGoogleRequest
import com.cmc.atracker.model.local.App
import com.cmc.atracker.model.repository.RepositoryGoogleLogin
import com.cmc.atracker.model.repository.RepositorySign
import com.cmc.atracker.utils.Event
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val repositorySign = RepositorySign()
    val repositoryGoogleLogin = RepositoryGoogleLogin()

    private val _testSignLoginFail = MutableLiveData<Event<Boolean>>()
    val testSignLoginFail : LiveData<Event<Boolean>> = _testSignLoginFail


    fun getAccessTokenFromGoogleByAuthCode(authCode : String) {
        viewModelScope.launch {
            val apiResult = repositoryGoogleLogin.googleSignCall(
                loginGoogleRequest = LoginGoogleRequest(
                grant_type = "authorization_code",
                client_id = "937085987148-n29vmraidvro4qd0nq5hfsvo4rn7jo9k.apps.googleusercontent.com",
                client_secret = "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",
                redirect_uri = "",
                code = authCode))

            if (apiResult.code() == 200) {
                val getResult = apiResult.body()!!
                Log.d("google_sign_login_call", "${getResult} 1111")
                App.prefs.setValue(BuildConfig.FROM_GOOGLE_ACCESS_TOKEN, getResult.access_token)

            } else {
                Log.d("google_sign_login_call", "${apiResult.code()}, ${apiResult.body()}, ${apiResult.headers()} 222")
            }

        }

    }





}