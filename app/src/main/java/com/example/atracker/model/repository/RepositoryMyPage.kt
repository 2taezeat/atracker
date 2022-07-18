package com.example.atracker.model.repository

import com.example.atracker.BuildConfig
import com.example.atracker.model.RetrofitClient
import com.example.atracker.model.api.HomeAddService
import com.example.atracker.model.api.MyPageService
import com.example.atracker.model.dto.MyPageResponse
import com.example.atracker.model.dto.StageResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class RepositoryMyPage {


    private val myPageService : MyPageService = RetrofitClient.getClient(BuildConfig.BASE_URL).create(MyPageService::class.java)



    suspend fun myPageGetCall (accessToken : String ) : retrofit2.Response<MyPageResponse>{
        val apiResponse = CoroutineScope(Dispatchers.IO).async {
            myPageService.myPageGetApi(accessToken = accessToken)
        }.await()

        return apiResponse

    }

}