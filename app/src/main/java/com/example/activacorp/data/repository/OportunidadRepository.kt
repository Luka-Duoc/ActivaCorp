package com.example.activacorp.data.repository

import com.example.activacorp.data.local.entity.OportunidadEntity
import com.example.activacorp.model.OportunidadConEjecutivo
import com.example.activacorp.model.OportunidadConOrganizacion
import kotlinx.coroutines.flow.Flow

interface OportunidadRepository {
    suspend fun insertarOportunidades(lista: List<OportunidadEntity>)
    suspend fun actualizarOportunidad(oportunidad: OportunidadEntity)
    suspend fun eliminarOportunidad(oportunidad: OportunidadEntity)
    fun obtenerTodas(): Flow<List<OportunidadEntity>>
    fun contarPendientes(): Flow<Int>
    fun contarPrioridadAlta(): Flow<Int>
    fun contarConfirmadas(): Flow<Int>
    fun obtenerOpPrioridadAlta(): Flow<List<OportunidadConOrganizacion>>
    fun obtenerOportunidadesPorUsuario(usuarioId: Long): Flow<List<OportunidadConEjecutivo>>
}