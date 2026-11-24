package com.example.activacorp.offlineRepositoriesTests

import com.example.activacorp.data.local.dao.SeguimientoDao
import com.example.activacorp.data.local.entity.SeguimientoEntity
import com.example.activacorp.data.repository.offlineRepositories.OfflineSeguimientoRepository
import com.example.activacorp.model.enums.TipoDeAccion
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class OfflineSeguimientoRepositoryTest {

    private val seguimientoDao: SeguimientoDao = mockk(relaxed = true)

    private lateinit var repository: OfflineSeguimientoRepository

    @BeforeEach
    fun setUp() {
        repository = OfflineSeguimientoRepository(seguimientoDao)
    }

    @Test
    fun `Insertar Seguimientos delega el insertado al dao`() = runTest {
        val seguimiento = SeguimientoEntity(id = 1L, oportunidadRelacionada = 1L, usuarioResponsable = 1L, tipoDeAccion = TipoDeAccion.REQUIERE_SEGUIMIENTO,
            fecha = "HOY", hora = "12:00", resultado = null, proximaAccion = TipoDeAccion.EN_EVALUACION, fechaProxSeguimiento = "DIA",
            observaciones = "Ninguna")

        repository.insertar(seguimiento)

        coVerify(exactly = 1) { seguimientoDao.insertar(seguimiento) }
    }

    @Test
    fun `Eliminar seguimiento delega la eliminacion al dao`() = runTest {
        val seguimiento = SeguimientoEntity(id = 1L, oportunidadRelacionada = 1L, usuarioResponsable = 1L, tipoDeAccion = TipoDeAccion.REQUIERE_SEGUIMIENTO,
            fecha = "HOY", hora = "12:00", resultado = null, proximaAccion = TipoDeAccion.EN_EVALUACION, fechaProxSeguimiento = "DIA",
            observaciones = "Ninguna")

        repository.eliminar(seguimiento)

        coVerify(exactly = 1) { seguimientoDao.delete(seguimiento) }
    }

    @Test
    fun `Actualizar delega la actualizacion del seguimiento al dao`() = runTest {
        val seguimiento = SeguimientoEntity(id = 1L, oportunidadRelacionada = 1L, usuarioResponsable = 1L, tipoDeAccion = TipoDeAccion.REQUIERE_SEGUIMIENTO,
            fecha = "HOY", hora = "12:00", resultado = null, proximaAccion = TipoDeAccion.EN_EVALUACION, fechaProxSeguimiento = "DIA",
            observaciones = "Ninguna")

        repository.actualizar(seguimiento)

        coVerify(exactly = 1) { seguimientoDao.actualizar(seguimiento) }
    }

    @Test
    fun `obtenerSeguimientos los seguimientos devuelve la lista emitida por el dao`() = runTest {
        val seguimiento = listOf(SeguimientoEntity(id = 1L, oportunidadRelacionada = 1L, usuarioResponsable = 1L, tipoDeAccion = TipoDeAccion.REQUIERE_SEGUIMIENTO,
            fecha = "HOY", hora = "12:00", resultado = null, proximaAccion = TipoDeAccion.EN_EVALUACION, fechaProxSeguimiento = "DIA",
            observaciones = "Ninguna"))

        every { repository.obtenerSeguimientos() } returns flowOf(seguimiento)

        val resultado = repository.obtenerSeguimientos().first()

        assertEquals(seguimiento, resultado)
    }

}