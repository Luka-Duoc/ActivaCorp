package com.example.activacorp.data.repository

import com.example.activacorp.data.local.entity.ComunaEntity
import kotlinx.coroutines.flow.Flow

interface ComunaRepository {

    suspend fun insertar(comuna: ComunaEntity)

    fun obtenerTodas(): Flow<List<ComunaEntity>>
}