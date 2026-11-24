package com.example.activacorp.offlineRepositoriesTests

import com.example.activacorp.data.local.dao.EventoDao
import com.example.activacorp.data.local.entity.EventoEntity
import com.example.activacorp.data.repository.offlineRepositories.OfflineEventoRepository
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class OfflineEventoRepositoryTest {

    private val eventoDao: EventoDao = mockk(relaxed = true)

    private lateinit var repository: OfflineEventoRepository

    @BeforeEach
    fun setUp() {
        repository = OfflineEventoRepository(eventoDao)
    }

    @Test
    fun `Obtener todos retorna la lista de eventos desde el dao`() = runTest {
        val eventoFake = listOf(
            EventoEntity(1, nombre = "organizacion1", fechaInicio = "hoy", fechaTermino = "manana", comuna = 1, organizacion = 1),
            EventoEntity(2, nombre = "organizacion2", fechaInicio = "ayer", fechaTermino = "pasado mañana", comuna = 2, organizacion = 2)
        )

        every { eventoDao.obtenerTodos() } returns flowOf(eventoFake)

        val resultado = repository.obtenerTodos().first()

        assertEquals(eventoFake, resultado)
    }

    @Test
    fun `insertar evento llama a insertar dao`() = runTest {
        val evento = EventoEntity(1, nombre = "organizacion1", fechaInicio = "hoy", fechaTermino = "manana", comuna = 1, organizacion = 1)

        repository.insertar(evento)

        coVerify(exactly = 1) { eventoDao.insertar(evento) }
    }

    @Test
    fun `eliminar evento llama a eliminar dao`() = runTest {
        val evento = EventoEntity(1, nombre = "organizacion1", fechaInicio = "hoy", fechaTermino = "manana", comuna = 1, organizacion = 1)

        repository.eliminar(evento)

        coVerify(exactly = 1) { eventoDao.eliminar(evento) }
    }

    @Test
    fun `actualizar evento llama a actualizar dao`() = runTest {
        val evento = EventoEntity(1, nombre = "organizacion1", fechaInicio = "hoy", fechaTermino = "manana", comuna = 1, organizacion = 1)

        repository.actualizar(evento)

        coVerify(exactly = 1) { eventoDao.actualizar(evento) }
    }
}