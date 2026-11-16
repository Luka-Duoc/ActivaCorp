package com.example.activacorp.data.repository

import com.example.activacorp.data.local.dao.ContactoDao
import com.example.activacorp.data.local.entity.ContactoEntity
import kotlinx.coroutines.flow.Flow

interface ContactoRepository {

    suspend fun insertar(contacto: ContactoEntity): Long
    suspend fun actualizar(contacto: ContactoEntity)
    suspend fun eliminar(contacto: ContactoEntity)
    fun obtenerTodos(): Flow<List<ContactoEntity>>
    fun obtenerPorOrganizacion(organizacionId: Long): Flow<List<ContactoEntity>>
}