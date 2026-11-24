package com.example.activacorp.data.repository.offlineRepositories

import com.example.activacorp.data.local.dao.ComunaDao
import com.example.activacorp.data.local.entity.ComunaEntity
import com.example.activacorp.data.repository.ComunaRepository
import kotlinx.coroutines.flow.Flow

class OfflineComunaRepository(
    private val comunaDao: ComunaDao
): ComunaRepository {
    override suspend fun insertar(comuna: ComunaEntity) =
        comunaDao.insertar(listOf(comuna))

    override fun obtenerTodas(): Flow<List<ComunaEntity>> =
        comunaDao.obtenerTodas()

}