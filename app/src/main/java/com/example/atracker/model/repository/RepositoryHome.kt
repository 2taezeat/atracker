package com.example.atracker.model.repository

import com.example.atracker.BuildConfig
import com.example.atracker.model.RetrofitClient
import com.example.atracker.model.api.HomeAddService
import com.example.atracker.model.dto.LoginRequest
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class RepositoryHome {

    private val homeAddService : HomeAddService = RetrofitClient.getClient(BuildConfig.BASE_URL).create(HomeAddService::class.java)


    suspend fun repositoryHomeFun(loginRequest: LoginRequest) : retrofit2.Response<JsonObject> {
        val apiResponse = CoroutineScope(Dispatchers.IO).async{
            homeAddService.companySearch(
                AccessToken = "",
                loginRequest = LoginRequest(profileImageId = ""),
                page = 0,
                size = 0)
        }.await()

        return apiResponse
    }

}