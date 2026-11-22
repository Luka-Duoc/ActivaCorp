package com.example.activacorp.data.repository

import com.example.activacorp.data.local.dao.ResultadoDao
import com.example.activacorp.data.local.entity.ResultadoEntity
import com.example.activacorp.data.local.entity.SeguimientoEntity

class OfflineResultadoRepository(private val resultadoDao: ResultadoDao): ResultadoRepository {
    override suspend fun insertar(resultado: ResultadoEntity) = resultadoDao.insertar(resultado)
    override suspend fun actualizar(resultado: ResultadoEntity) = resultadoDao.actualizar(resultado)
    override suspend fun eliminar(resultado: ResultadoEntity) = resultadoDao.delete(resultado)
    override fun obtenerResultados() = resultadoDao.obtenerResultados()
}