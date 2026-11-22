package com.example.activacorp.data.repository

import com.example.activacorp.data.local.entity.SeguimientoEntity
import kotlinx.coroutines.flow.Flow

interface SeguimientoRepository {
    suspend fun insertar(seguimiento: SeguimientoEntity)
    suspend fun update(seguimiento: SeguimientoEntity)
    suspend fun delete(seguimiento: SeguimientoEntity)
    fun obtenerSeguimientos(): Flow<List<SeguimientoEntity>>
}