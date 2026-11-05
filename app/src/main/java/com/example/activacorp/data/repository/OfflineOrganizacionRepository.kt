package com.example.activacorp.data.repository

import com.example.activacorp.data.local.dao.OrganizacionDao
import com.example.activacorp.data.local.entity.OrganizacionEntity
import kotlinx.coroutines.flow.Flow

class OfflineOrganizacionRepository(
    private val organizacionDao: OrganizacionDao
) : OrganizacionRepository {

    override suspend fun insertar(organizaciones: List<OrganizacionEntity>) = organizacionDao.insertar(organizaciones)

    override suspend fun actualizar(organizacion: OrganizacionEntity) = organizacionDao.actualizar(organizacion)

    override suspend fun eliminar(organizacion: OrganizacionEntity) = organizacionDao.eliminar(organizacion)

    override fun obtenerTodas(): Flow<List<OrganizacionEntity>> = organizacionDao.obtenerTodas()
}