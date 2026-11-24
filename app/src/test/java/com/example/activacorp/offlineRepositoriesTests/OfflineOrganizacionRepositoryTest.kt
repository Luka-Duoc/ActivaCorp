package com.example.activacorp.offlineRepositoriesTests

import com.example.activacorp.data.local.dao.OrganizacionDao
import com.example.activacorp.data.local.entity.OrganizacionEntity
import com.example.activacorp.data.repository.offlineRepositories.OfflineOrganizacionRepository
import com.example.activacorp.model.enums.TipoOrganizacion
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class OfflineOrganizacionRepositoryTest {

    private val organizacionDao: OrganizacionDao = mockk(relaxed = true)

    private lateinit var repository: OfflineOrganizacionRepository

    @BeforeEach
    fun setUp() {
        repository = OfflineOrganizacionRepository(organizacionDao)
    }

    @Test
    fun `obtenerOrganizaciones devuelve el estad emitido por el dao`() = runTest {
        val orgFake = listOf(OrganizacionEntity(1L, "organizacion", mockk<TipoOrganizacion>(relaxed = true),
                rubro = "Otro", comuna = 0L, sitioWeb = "organizacion.cl",
                publico = "Sin publico objetivo",
                historial = "Sin historial")
        )

        every { organizacionDao.obtenerTodas() } returns flowOf(orgFake )

        val resultado = repository.obtenerTodas().first()

        assertEquals(orgFake, resultado)
    }

    @Test
    fun `insertar organizacion llama a insertar en el dao`() = runTest {
        val nuevaOrg = OrganizacionEntity(1L, "organizacion", mockk<TipoOrganizacion>(relaxed = true), rubro = "Otro", comuna = 0L,
            sitioWeb = "organizacion.cl", publico = "Sin publico objetivo", historial = "Sin historial")

        repository.insertar(nuevaOrg)

        coVerify(exactly = 1) { organizacionDao.insertar(nuevaOrg) }


    }

    @Test
    fun `Eliminar organizacion llama a eliminar en el dao`() = runTest {
        val org = OrganizacionEntity(1L, "organizacion", mockk<TipoOrganizacion>(relaxed = true), rubro = "Otro", comuna = 0L,
            sitioWeb = "organizacion.cl", publico = "Sin publico objetivo", historial = "Sin historial")

        repository.eliminar(org)

        coVerify(exactly = 1){ organizacionDao.eliminar(org) }
    }

    @Test
    fun `actualizar organizacion llama al metodo actualizacion en el dao`() = runTest {
        val org = OrganizacionEntity(1L, "organizacion", mockk<TipoOrganizacion>(relaxed = true), rubro = "Otro", comuna = 0L,
            sitioWeb = "organizacion.cl", publico = "Sin publico objetivo", historial = "Sin historial")

        repository.actualizar(org)

        coVerify(exactly = 1) { organizacionDao.actualizar(org) }
    }
}