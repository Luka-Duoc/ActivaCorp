package com.example.activacorp.offlineRepositoriesTests

import com.example.activacorp.data.local.dao.ComunaDao
import com.example.activacorp.data.local.entity.ComunaEntity
import com.example.activacorp.data.repository.offlineRepositories.OfflineComunaRepository
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OfflineComunaRepositoryTest {
    private val comunaDao: ComunaDao = mockk(relaxed = true)

    private lateinit var repository: OfflineComunaRepository

    @BeforeEach
    fun setUp() {
        repository = OfflineComunaRepository(comunaDao)
    }

    @Test
    fun `insertar envuelve la comuna en una lista y la delega al dao`() = runTest {
        val comuna = ComunaEntity(id = 1L, idRegion = 13L, nombre = "Los Quillayes")

        repository.insertar(comuna)

        coVerify(exactly = 1) { comunaDao.insertar(listOf(comuna)) }
    }

    @Test
    fun `obtenerTodas retorna el flow emitido por el dao`() = runTest {
        val comunasFake = listOf(
            ComunaEntity(id = 1L, idRegion = 13L, nombre = "La Florida"),
            ComunaEntity(id = 2L, idRegion = 13L, nombre = "Puente Alto")
        )
        every { comunaDao.obtenerTodas() } returns flowOf(comunasFake)

        val resultado = repository.obtenerTodas().first()

        Assertions.assertEquals(comunasFake, resultado)
    }
}