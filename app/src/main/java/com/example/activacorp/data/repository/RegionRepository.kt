package com.example.activacorp.data.repository

import com.example.activacorp.data.local.entity.RegionEntity
import kotlinx.coroutines.flow.Flow

interface RegionRepository {

    suspend fun insertar(region: RegionEntity)

    fun obtenerTodas(): Flow<List<RegionEntity>>
}