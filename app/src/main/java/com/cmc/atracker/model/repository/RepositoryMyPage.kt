package com.cmc.atracker.model.repository

import com.cmc.atracker.BuildConfig
import com.cmc.atracker.model.RetrofitClient
import com.cmc.atracker.model.api.MyPageService
import com.cmc.atracker.model.dto.GetPfratioResponse
import com.cmc.atracker.model.dto.MyPageResponse
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


    suspend fun myApplyPfratioGetCall (accessToken : String ) : retrofit2.Response<GetPfratioResponse>{
        val apiResponse = CoroutineScope(Dispatchers.IO).async {
            myPageService.myApplyPfratioGetApi(accessToken = accessToken)
        }.await()

        return apiResponse
    }

}