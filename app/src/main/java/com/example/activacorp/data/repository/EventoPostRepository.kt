package com.example.activacorp.data.repository

import com.example.activacorp.data.remote.RetroFitInstance
import com.google.gson.JsonObject

class EventoPostRepository {

    suspend fun getEventosPosts(): JsonObject {
        return RetroFitInstance.api.getEventosPosts()
    }
}