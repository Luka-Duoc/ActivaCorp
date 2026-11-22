package com.example.activacorp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.activacorp.data.local.entity.ResultadoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ResultadoDao {
    @Insert
    suspend fun insertar(resultado: ResultadoEntity)

    @Update
    suspend fun actualizar(resultado: ResultadoEntity)

    @Delete
    suspend fun delete(resultado: ResultadoEntity)

    @Query("select * from resultados")
    fun obtenerResultados(): Flow<List<ResultadoEntity>>

}