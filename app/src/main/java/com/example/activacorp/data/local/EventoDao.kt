package com.example.activacorp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EventoDao {

    @Insert
    suspend fun insertar(evento: EventoEntity): Long

    @Query("select * from eventos")
    fun obtenerTodos(): Flow<List<EventoEntity>>

}