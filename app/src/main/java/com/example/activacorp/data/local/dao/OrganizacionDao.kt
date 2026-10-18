package com.example.activacorp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.activacorp.data.local.entity.OrganizacionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrganizacionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(org: List<OrganizacionEntity>)

    @Update
    suspend fun actualizar(organizacion: OrganizacionEntity)

    @Query("SELECT * FROM organizaciones")
    fun obtenerTodas(): Flow<List<OrganizacionEntity>>
}