package com.cmc.atracker.model.api

import com.cmc.atracker.model.dto.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface GoogleSignService {

    @POST("/oauth2/v4/token/")
    suspend fun googleSignApi(
        @Body loginGoogleRequest: LoginGoogleRequest

    ) : retrofit2.Response<LoginGoogleResponse>
}