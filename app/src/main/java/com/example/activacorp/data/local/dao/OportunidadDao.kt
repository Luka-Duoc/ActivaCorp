package com.example.activacorp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.activacorp.data.local.entity.OportunidadEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OportunidadDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(org: List<OportunidadEntity>)

    @Query("SELECT * FROM oportunidades")
    fun obtenerTodas(): Flow<List<OportunidadEntity>>
}