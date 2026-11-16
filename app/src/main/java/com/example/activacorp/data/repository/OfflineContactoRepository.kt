package com.example.activacorp.data.repository

import com.example.activacorp.data.local.dao.ContactoDao
import com.example.activacorp.data.local.entity.ContactoEntity
import kotlinx.coroutines.flow.Flow

class OfflineContactoRepository(
    private val contactoDao: ContactoDao
) : ContactoRepository {
    override suspend fun insertar(contacto: ContactoEntity): Long =
        contactoDao.insertar(contacto)

    override suspend fun actualizar(contacto: ContactoEntity) =
        contactoDao.actualizar(contacto)

    override suspend fun eliminar(contacto: ContactoEntity) =
        contactoDao.eliminar(contacto)

    override fun obtenerTodos(): Flow<List<ContactoEntity>> =
        contactoDao.obtenerTodos()

    override fun obtenerPorOrganizacion(organizacionId: Long): Flow<List<ContactoEntity>> =
        contactoDao.obtenerPorOrganizacion(organizacionId)

}