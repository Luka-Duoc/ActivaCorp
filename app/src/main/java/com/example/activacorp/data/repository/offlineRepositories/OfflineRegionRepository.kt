package com.example.activacorp.data.repository.offlineRepositories

import com.example.activacorp.data.local.dao.RegionDao
import com.example.activacorp.data.local.entity.RegionEntity
import com.example.activacorp.data.repository.RegionRepository
import kotlinx.coroutines.flow.Flow

class OfflineRegionRepository(
    private val regionDao: RegionDao
): RegionRepository {
    override suspend fun insertar(region: RegionEntity) =
        regionDao.insertar(listOf(region))


    override fun obtenerTodas(): Flow<List<RegionEntity>> =
        regionDao.obtenerTodas()

}