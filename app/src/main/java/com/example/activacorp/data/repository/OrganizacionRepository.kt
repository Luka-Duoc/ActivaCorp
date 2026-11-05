package com.example.activacorp.data.repository

import com.example.activacorp.data.local.entity.OrganizacionEntity
import kotlinx.coroutines.flow.Flow

interface OrganizacionRepository {
    suspend fun insertar(organizaciones: List<OrganizacionEntity>)
    suspend fun actualizar(organizacion: OrganizacionEntity)
    suspend fun eliminar(organizacion: OrganizacionEntity)
    fun obtenerTodas(): Flow<List<OrganizacionEntity>>

}