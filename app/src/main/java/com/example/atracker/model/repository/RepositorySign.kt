package com.example.atracker.model.repository

import com.example.atracker.BuildConfig
import com.example.atracker.model.RetrofitClient
import com.example.atracker.model.api.HomeService
import com.example.atracker.model.api.SignService
import com.example.atracker.model.dto.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class RepositorySign {

    private val signService : SignService = RetrofitClient.getClient(BuildConfig.BASE_URL).create(SignService::class.java)

    suspend fun signPostCall (signRequest: SignRequest) : retrofit2.Response<SignResponse> {
        val apiResponse = CoroutineScope(Dispatchers.IO).async{
            signService.signPostApi(signRequest = signRequest)
        }.await()

        return apiResponse
    }


    suspend fun refreshTokenCall (tokenRefreshRequest: TokenRefreshRequest) : retrofit2.Response<TokenRefreshResponse> {
        val apiResponse = CoroutineScope(Dispatchers.IO).async{
            signService.refreshTokenPostApi(tokenRefreshRequest = tokenRefreshRequest)
        }.await()

        return apiResponse
    }


    suspend fun signOutPostCall (accessToken : String) : retrofit2.Response<Void> {
        val apiResponse = CoroutineScope(Dispatchers.IO).async{
            signService.signOutPostApi(accessToken = accessToken)
        }.await()

        return apiResponse
    }




}