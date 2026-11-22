package com.example.activacorp

import com.example.activacorp.data.local.dao.RegionDao
import com.example.activacorp.data.local.entity.RegionEntity
import com.example.activacorp.data.repository.OfflineRegionRepository
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OfflineRegionRepositoryTest {

    private val regionDao: RegionDao = mockk(relaxed = true)

    private lateinit var repository: OfflineRegionRepository

    @BeforeEach
    fun setUp() {
        repository = OfflineRegionRepository(regionDao)
    }

    @Test
    fun `insertar envuelve la region en una lista y la delega al dao`() = runTest {
        val region = RegionEntity(id = 13L, nombre = "Región Metropolitana")

        repository.insertar(region)

        coVerify(exactly = 1) { regionDao.insertar(listOf(region)) }
    }

    @Test
    fun `obtenerTodas retorna el flow emitido por el dao`() = runTest {
        val regionesFake = listOf(
            RegionEntity(id = 13L, nombre = "Región Metropolitana"),
            RegionEntity(id = 5L, nombre = "Valparaíso")
        )
        every { regionDao.obtenerTodas() } returns flowOf(regionesFake)

        val resultado = repository.obtenerTodas().first()

        assertEquals(regionesFake, resultado)
    }
}