package com.example.atracker.model.repository

import com.example.atracker.BuildConfig
import com.example.atracker.model.RetrofitClient
import com.example.atracker.model.api.HomeAddService
import com.example.atracker.model.dto.CompanySearchRequest
import com.example.atracker.model.dto.LoginRequest
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class RepositoryHome {

    private val homeAddService : HomeAddService = RetrofitClient.getClient(BuildConfig.BASE_URL).create(HomeAddService::class.java)


    suspend fun repositoryHomeFun(companySearchRequest: CompanySearchRequest) : retrofit2.Response<JsonObject> {
        val apiResponse = CoroutineScope(Dispatchers.IO).async{
            homeAddService.companySearch(
                AccessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhdHJrLWFjY2Vzc1Rva2VuIiwidG9rZW5fdHlwZSI6IkFDQ0VTU19UT0tFTiIsImlkIjoiMTIiLCJpYXQiOjE2NTYxNDQyMjgsImV4cCI6MTY1NjE0NzgyOH0.bZZz5bFTjUG8MsYdj92HH233vDE0eslVWtB-sCP1fHE",
                companySearchRequest = companySearchRequest,
                page = 1,
                size = 10)
        }.await()

        return apiResponse
    }

}