package com.example.activacorp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.activacorp.data.local.entity.SeguimientoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SeguimientoDao {

    @Insert
    suspend fun insertar(seguimiento: SeguimientoEntity)

    @Update
    suspend fun actualizar(seguimiento: SeguimientoEntity)

    @Delete
    suspend fun delete(seguimiento: SeguimientoEntity)

    @Query("select * from seguimientos")
    fun obtenerSeguimientos(): Flow<List<SeguimientoEntity>>

    @Query("select * from seguimientos ")
    fun obtenerSeguimientosPorTipoDeAccion(): Flow<List<SeguimientoEntity>>

}