package com.example.activacorp.offlineRepositoriesTests

import com.example.activacorp.data.local.dao.ResultadoDao
import com.example.activacorp.data.local.entity.ResultadoEntity
import com.example.activacorp.data.repository.offlineRepositories.OfflineResultadoRepository
import com.example.activacorp.model.enums.EstadoGestion
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class OfflineResultadoRepositoryTest {

    private val resultadoDao: ResultadoDao = mockk(relaxed = true)

    private lateinit var repository: OfflineResultadoRepository

    @BeforeEach
    fun setUp() {
        repository = OfflineResultadoRepository(resultadoDao)
    }

    @Test
    fun `insertar resultado delega la insercion al dao`() = runTest {
        val resultado = ResultadoEntity(
            id = 1L, fechaRealizacion = "10/12/2026", estadoFinal = EstadoGestion.EVENTO_IDENTIFICADO, cantidadEstimadaAsistentes = 200,
            cantidadContactosGenerados = 100, cantidadPersonasInteresadas = 50, cantidadInscripciones = 20, observaciones = "Sin observaciones",
            evaluacionGeneral = "Sin evaluacion", aprendizajes = "Sin aprendisajes"
        )

        repository.insertar(resultado)

        coVerify(exactly = 1) { resultadoDao.insertar(resultado) }
    }

    @Test
    fun `Eliminar resultado delega la eliminacion al dao`() = runTest {
        val resultado = ResultadoEntity(
            id = 1L, fechaRealizacion = "10/12/2026", estadoFinal = EstadoGestion.EVENTO_IDENTIFICADO, cantidadEstimadaAsistentes = 200,
            cantidadContactosGenerados = 100, cantidadPersonasInteresadas = 50, cantidadInscripciones = 20, observaciones = "Sin observaciones",
            evaluacionGeneral = "Sin evaluacion", aprendizajes = "Sin aprendisajes"
        )

        repository.eliminar(resultado)

        coVerify(exactly = 1) { resultadoDao.delete(resultado) }
    }

    @Test
    fun `Actualizar resultado delega la actualizacion al dao`() = runTest {
        val resultado = ResultadoEntity(
            id = 1L, fechaRealizacion = "10/12/2026", estadoFinal = EstadoGestion.EVENTO_IDENTIFICADO, cantidadEstimadaAsistentes = 200,
            cantidadContactosGenerados = 100, cantidadPersonasInteresadas = 50, cantidadInscripciones = 20, observaciones = "Sin observaciones",
            evaluacionGeneral = "Sin evaluacion", aprendizajes = "Sin aprendisajes"
        )

        repository.actualizar(resultado)

        coVerify(exactly = 1) { resultadoDao.actualizar(resultado) }
    }

    @Test
    fun `ObtenerResultados los resultado devuelve una lista emitida por el dao`() = runTest {
        val resultado = listOf(ResultadoEntity(
            id = 1L, fechaRealizacion = "10/12/2026", estadoFinal = EstadoGestion.EVENTO_IDENTIFICADO, cantidadEstimadaAsistentes = 200,
            cantidadContactosGenerados = 100, cantidadPersonasInteresadas = 50, cantidadInscripciones = 20, observaciones = "Sin observaciones",
            evaluacionGeneral = "Sin evaluacion", aprendizajes = "Sin aprendisajes"
        ))

        every { repository.obtenerResultados() } returns flowOf(resultado)

        val result = repository.obtenerResultados().first()

        assertEquals(resultado, result)
    }

}