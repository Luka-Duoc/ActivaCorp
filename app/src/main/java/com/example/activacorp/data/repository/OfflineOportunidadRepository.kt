package com.example.activacorp.data.repository

import com.example.activacorp.data.local.dao.OportunidadDao
import com.example.activacorp.data.local.entity.OportunidadEntity
import com.example.activacorp.model.OportunidadConEjecutivo
import com.example.activacorp.model.OportunidadConOrganizacion
import kotlinx.coroutines.flow.Flow

class OfflineOportunidadRepository(
    private val oportunidadDao: OportunidadDao
) : OportunidadRepository {

    override suspend fun insertarOportunidades(lista: List<OportunidadEntity>) =
        oportunidadDao.insertar(lista)

    override suspend fun actualizarOportunidad(oportunidad: OportunidadEntity) =
        oportunidadDao.actualizar(oportunidad)

    override suspend fun eliminarOportunidad(oportunidad: OportunidadEntity) =
        oportunidadDao.eliminar(oportunidad)

    override fun obtenerTodas(): Flow<List<OportunidadEntity>> =
        oportunidadDao.obtenerTodas()

    override fun contarPendientes(): Flow<Int> =
        oportunidadDao.contarPendientes()

    override fun contarPrioridadAlta(): Flow<Int> =
        oportunidadDao.contarPrioridadAlta()

    override fun contarConfirmadas(): Flow<Int> =
        oportunidadDao.contarConfirmadas()

    override fun obtenerOpPrioridadAlta(): Flow<List<OportunidadConOrganizacion>> =
        oportunidadDao.obtenerOpPrioridadAlta()

    override fun obtenerOportunidadesPorUsuario(usuarioId: Long): Flow<List<OportunidadConEjecutivo>> =
        oportunidadDao.obtenerOportunidadesPorUsuario(usuarioId)
}