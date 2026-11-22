package com.example.activacorp.data.repository

import com.example.activacorp.data.local.dao.SeguimientoDao
import com.example.activacorp.data.local.entity.SeguimientoEntity
import com.example.activacorp.model.GestionesPartConfirmada
import kotlinx.coroutines.flow.Flow

class OfflineSeguimientoRepository(private val seguimientoDao: SeguimientoDao) : SeguimientoRepository {
    override suspend fun insertar(seguimiento: SeguimientoEntity) = seguimientoDao.insertar(seguimiento)
    override suspend fun actualizar(seguimiento: SeguimientoEntity) = seguimientoDao.actualizar(seguimiento)
    override suspend fun eliminar(seguimiento: SeguimientoEntity) = seguimientoDao.delete(seguimiento)
    override fun obtenerSeguimientos() = seguimientoDao.obtenerSeguimientos()
    override fun obtenerGestionesPartConfirmada() = seguimientoDao.obtenerGestionesDeParticipacionConfirmada()
}