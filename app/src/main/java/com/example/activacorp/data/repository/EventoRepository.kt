package com.example.activacorp.data.repository

import com.example.activacorp.data.local.entity.EventoEntity
import kotlinx.coroutines.flow.Flow

interface EventoRepository {
    suspend fun insertar(evento: EventoEntity)

    suspend fun actualizar(evento: EventoEntity)

    suspend fun eliminar(evento: EventoEntity)

    fun obtenerTodos(): Flow<List<EventoEntity>>

}