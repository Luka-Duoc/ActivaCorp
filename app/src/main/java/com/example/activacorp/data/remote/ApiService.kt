package com.example.activacorp.data.remote

import com.google.gson.JsonObject
import retrofit2.http.GET

interface ApiService {

    @GET("eventos")
    suspend fun getEventosPosts(): JsonObject
}