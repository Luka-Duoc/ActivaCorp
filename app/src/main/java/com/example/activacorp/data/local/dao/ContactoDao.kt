package com.example.activacorp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.activacorp.data.local.entity.ContactoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactoDao {

    @Insert
    suspend fun insertar(contacto: ContactoEntity): Long

    @Update
    suspend fun actualizar(contacto: ContactoEntity)

    @Delete
    suspend fun eliminar(contacto: ContactoEntity)

    @Query("select * from contactos")
    fun obtenerTodos(): Flow<List<ContactoEntity>>

    @Query("select * from contactos where organizacionId = :organizacionId")
    fun obtenerPorOrganizacion(organizacionId: Long): Flow<List<ContactoEntity>>
}