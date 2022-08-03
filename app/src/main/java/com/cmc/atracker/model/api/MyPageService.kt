package com.cmc.atracker.model.api

import com.cmc.atracker.model.dto.GetPfratioResponse
import com.cmc.atracker.model.dto.MyPageResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface MyPageService {

    @GET("/api/v1/user/mypage/")
    suspend fun myPageGetApi(
        @Header("Authorization") accessToken : String,
    ) : retrofit2.Response<MyPageResponse>



    @GET("/api/v1/user/statistics/apply/pfratio/")
    suspend fun myApplyPfratioGetApi(
        @Header("Authorization") accessToken : String,
    ) : retrofit2.Response<GetPfratioResponse>


}