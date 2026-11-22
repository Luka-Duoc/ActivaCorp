package com.example.activacorp.data.repository

import com.example.activacorp.data.local.entity.ResultadoEntity
import kotlinx.coroutines.flow.Flow

interface ResultadoRepository {
    suspend fun insertar(resultado: ResultadoEntity)
    suspend fun update(resultado: ResultadoEntity)
    suspend fun delete(resultado: ResultadoEntity)
    fun obtenerResultados(): Flow<List<ResultadoEntity>>
}