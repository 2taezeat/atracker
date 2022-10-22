package com.cmc.atracker.model.repository

import com.cmc.atracker.BuildConfig
import com.cmc.atracker.model.RetrofitClientGoogleOauth
import com.cmc.atracker.model.api.GoogleSignService
import com.cmc.atracker.model.dto.LoginGoogleRequest
import com.cmc.atracker.model.dto.LoginGoogleResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class RepositoryGoogleLogin {

    private val googleSignService : GoogleSignService = RetrofitClientGoogleOauth.getClientGoogleOauth(BuildConfig.GOOGLE_OAUTH_BASE_URL).create(GoogleSignService::class.java)

    suspend fun googleSignCall(loginGoogleRequest : LoginGoogleRequest) : retrofit2.Response<LoginGoogleResponse> {
        val apiResponse = CoroutineScope(Dispatchers.IO).async {
            googleSignService.googleSignApi(loginGoogleRequest = loginGoogleRequest)


        }.await()

        return apiResponse
    }


}