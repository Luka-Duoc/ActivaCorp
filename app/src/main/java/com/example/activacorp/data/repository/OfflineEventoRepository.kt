package com.example.activacorp.data.repository

import com.example.activacorp.data.local.dao.EventoDao
import com.example.activacorp.data.local.entity.EventoEntity
import kotlinx.coroutines.flow.Flow

class OfflineEventoRepository (
    private val eventoDao: EventoDao
) : EventoRepository {

    override suspend fun insertar(evento: EventoEntity) =
        eventoDao.insertar(evento)

    override suspend fun actualizar(evento: EventoEntity) =
        eventoDao.actualizar(evento)

    override suspend fun eliminar(evento: EventoEntity) =
        eventoDao.eliminar(evento)

    override fun obtenerTodos(): Flow<List<EventoEntity>> =
        eventoDao.obtenerTodos()

}