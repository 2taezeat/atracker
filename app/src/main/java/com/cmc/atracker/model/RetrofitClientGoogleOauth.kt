package com.cmc.atracker.model

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientGoogleOauth {

    private lateinit var retrofit: Retrofit
    val gson = GsonBuilder()
        .setLenient()
        .create()



    fun getClientGoogleOauth(baseUrl : String) : Retrofit {
        if ( !this::retrofit.isInitialized) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor { chain ->
                    val requestBuilder = chain.request().newBuilder()
                        .removeHeader("User-Agent")
                        .addHeader("User-Agent","Android")
                    chain.proceed(requestBuilder.build())
                }.build()

            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

        return retrofit
    }



}