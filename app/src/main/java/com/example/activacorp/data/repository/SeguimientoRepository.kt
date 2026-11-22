package com.example.activacorp.data.repository

import com.example.activacorp.data.local.entity.SeguimientoEntity
import kotlinx.coroutines.flow.Flow

interface SeguimientoRepository {
    suspend fun insertar(seguimiento: SeguimientoEntity)
    suspend fun actualizar(seguimiento: SeguimientoEntity)
    suspend fun eliminar(seguimiento: SeguimientoEntity)
    fun obtenerSeguimientos(): Flow<List<SeguimientoEntity>>
}