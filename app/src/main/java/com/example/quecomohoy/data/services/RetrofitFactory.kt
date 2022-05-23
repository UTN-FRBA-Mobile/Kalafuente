package com.example.quecomohoy.data.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {
    fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.17:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}