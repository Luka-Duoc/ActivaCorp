package com.example.activacorp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.activacorp.data.local.entity.OrganizacionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrganizacionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(org: List<OrganizacionEntity>)

    @Query("SELECT * FROM organizaciones")
    fun obtenerTodas(): Flow<List<OrganizacionEntity>>
}