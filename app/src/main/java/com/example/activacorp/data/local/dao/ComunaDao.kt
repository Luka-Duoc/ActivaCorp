package com.example.activacorp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.activacorp.data.local.entity.ComunaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ComunaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(comuna: List<ComunaEntity>)

    @Query("SELECT * FROM comunas")
    fun obtenerTodas(): Flow<List<ComunaEntity>>
}