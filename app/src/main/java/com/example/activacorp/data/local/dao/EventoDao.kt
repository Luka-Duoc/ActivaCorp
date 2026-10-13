package com.example.activacorp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.activacorp.data.local.entity.EventoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventoDao {

    @Insert
    suspend fun insertar(evento: EventoEntity)

    @Update
    suspend fun actualizar(evento: EventoEntity)

    @Delete
    suspend fun eliminar(evento: EventoEntity)

    @Query("select * from eventos")
    fun obtenerTodos(): Flow<List<EventoEntity>>

}