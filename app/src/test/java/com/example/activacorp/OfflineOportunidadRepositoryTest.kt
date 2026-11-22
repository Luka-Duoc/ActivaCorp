package com.example.activacorp
import com.example.activacorp.data.local.dao.OportunidadDao
import com.example.activacorp.data.local.entity.OportunidadEntity
import com.example.activacorp.data.repository.OfflineOportunidadRepository
import com.example.activacorp.model.EstadoGestion
import com.example.activacorp.model.OportunidadConEjecutivo
import com.example.activacorp.model.OportunidadConOrganizacion
import com.example.activacorp.model.PotencialComercial
import com.example.activacorp.model.TipoOportunidad
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OfflineOportunidadRepositoryTest {
    private val oportunidadDao: OportunidadDao = mockk(relaxed = true)

    private lateinit var repository: OfflineOportunidadRepository

    @BeforeEach
    fun setUp() {
        repository = OfflineOportunidadRepository(oportunidadDao)
    }

    @Test
    fun `insertarOportunidades delega la insercion al dao`() = runTest {
        val oportunidad = OportunidadEntity(
            id = 1L,
            nombre = "Convenio Salud",
            prioridad = 2,
            tipo = mockk<TipoOportunidad>(relaxed = true),
            potencial = mockk<PotencialComercial>(relaxed = true),
            estado = mockk<EstadoGestion>(relaxed = true)
        )

        repository.insertarOportunidades(oportunidad)

        coVerify(exactly = 1) { oportunidadDao.insertar(oportunidad) }
    }

    @Test
    fun `actualizarOportunidad delega la actualizacion al dao`() = runTest {
        val oportunidad = OportunidadEntity(
            id = 2L,
            nombre = "Convenio Actualizado",
            prioridad = 1,
            tipo = mockk<TipoOportunidad>(relaxed = true),
            potencial = mockk<PotencialComercial>(relaxed = true),
            estado = mockk<EstadoGestion>(relaxed = true)
        )

        repository.actualizarOportunidad(oportunidad)

        coVerify(exactly = 1) { oportunidadDao.actualizar(oportunidad) }
    }

    @Test
    fun `eliminarOportunidad delega la eliminacion al dao`() = runTest {
        val oportunidad = OportunidadEntity(
            id = 3L,
            nombre = "Convenio Descartado",
            prioridad = 3,
            tipo = mockk<TipoOportunidad>(relaxed = true),
            potencial = mockk<PotencialComercial>(relaxed = true),
            estado = mockk<EstadoGestion>(relaxed = true)
        )

        repository.eliminarOportunidad(oportunidad)

        coVerify(exactly = 1) { oportunidadDao.eliminar(oportunidad) }
    }

    @Test
    fun `obtenerTodas retorna la lista de oportunidades desde el dao`() = runTest {
        val listaFake = listOf(
            OportunidadEntity(
                id = 1L,
                nombre = "Oportunidad 1",
                prioridad = 1,
                tipo = mockk(relaxed = true),
                potencial = mockk(relaxed = true),
                estado = mockk(relaxed = true)
            )
        )
        every { oportunidadDao.obtenerTodas() } returns flowOf(listaFake)

        val resultado = repository.obtenerTodas().first()

        assertEquals(listaFake, resultado)
    }

    @Test
    fun `contadores retornan los valores emitidos por el dao`() = runTest {
        every { oportunidadDao.contarPendientes() } returns flowOf(4)
        every { oportunidadDao.contarPrioridadAlta() } returns flowOf(2)
        every { oportunidadDao.contarConfirmadas() } returns flowOf(7)

        assertEquals(4, repository.contarPendientes().first())
        assertEquals(2, repository.contarPrioridadAlta().first())
        assertEquals(7, repository.contarConfirmadas().first())
    }

    @Test
    fun `obtenerOpPrioridadAlta y obtenerOportunidadesPorUsuario retornan las consultas del dao`() = runTest {
        val altasFake = listOf(mockk<OportunidadConOrganizacion>(relaxed = true))
        val porUsuarioFake = listOf(mockk<OportunidadConEjecutivo>(relaxed = true))

        every { oportunidadDao.obtenerOpPrioridadAlta() } returns flowOf(altasFake)
        every { oportunidadDao.obtenerOportunidadesPorUsuario(10L) } returns flowOf(porUsuarioFake)

        assertEquals(altasFake, repository.obtenerOpPrioridadAlta().first())
        assertEquals(porUsuarioFake, repository.obtenerOportunidadesPorUsuario(10L).first())
    }
}