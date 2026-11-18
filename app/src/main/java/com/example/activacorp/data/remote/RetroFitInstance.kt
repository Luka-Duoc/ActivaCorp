package com.example.activacorp.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroFitInstance {

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://firestore.googleapis.com/v1/projects/activacorp-9507a/databases/(default)/documents/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}