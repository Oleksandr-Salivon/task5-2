package com.example.task5_2

import android.app.Application
import com.example.task5_2.api.ApiInterface
import com.fasterxml.jackson.databind.ObjectMapper
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class MyApplication :Application() {

    public lateinit var httpApiService:ApiInterface

    override fun onCreate() {
        super.onCreate()

        httpApiService = initHttpApiService()
    }
    private fun initHttpApiService(): ApiInterface{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://httpapibooks.mocklab.io")
            .addConverterFactory(JacksonConverterFactory.create(ObjectMapper()))
            .build()
        return retrofit.create(ApiInterface::class.java)
    }
}