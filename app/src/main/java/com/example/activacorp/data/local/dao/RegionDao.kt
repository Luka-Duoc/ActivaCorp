package com.example.activacorp.data.local.dao

import android.graphics.Region
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.activacorp.data.local.entity.RegionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RegionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(region: List<RegionEntity>)

    @Query("SELECT * FROM regiones")
    fun obtenerTodas(): Flow<List<RegionEntity>>


}