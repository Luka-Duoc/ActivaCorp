package com.example.activacorp

import com.example.activacorp.data.local.dao.ContactoDao
import com.example.activacorp.data.local.entity.ContactoEntity
import com.example.activacorp.data.repository.ContactoRepository
import com.example.activacorp.data.repository.OfflineContactoRepository
import com.example.activacorp.model.AreaContacto
import com.example.activacorp.model.EstadoContacto
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class OfflineContactoRepositoryTest {

    private val contactoDao: ContactoDao = mockk(relaxed = true)

    private lateinit var repository: ContactoRepository

    @BeforeEach
    fun setUp() {
        repository = OfflineContactoRepository(contactoDao)
    }

    @Test
    fun `insertar contacto delega la insercion al dao`() = runTest {
        val contacto = ContactoEntity(id = 1L, organizacionId = 1L,
            nombre = "Nacho", cargo = "Jefe", area = AreaContacto.RRHH, correo = "nacho@rrhh.cl", telefono = "123456789",
            estado = EstadoContacto.SIN_RESPUESTA, fechaUltContacto = "10/12/2026", observaciones = "Sin observaciones"
        )

        repository.insertar(contacto)

        coVerify(exactly = 1) { contactoDao.insertar(contacto) }
    }

    // Eliminar
    @Test
    fun `eliminar Contacto delega la tarea al dao`() = runTest {
        val contacto = ContactoEntity(id = 1L, organizacionId = 1L,
            nombre = "Nacho", cargo = "Jefe", area = AreaContacto.RRHH, correo = "nacho@rrhh.cl", telefono = "123456789",
            estado = EstadoContacto.SIN_RESPUESTA, fechaUltContacto = "10/12/2026", observaciones = "Sin observaciones"
        )

        repository.eliminar(contacto)

        coVerify(exactly = 1) { contactoDao.eliminar(contacto) }
    }


    // Actualizar
    @Test
    fun `actualizar Contacto delega la tarea al dao`() = runTest {
        val contacto = ContactoEntity(id = 1L, organizacionId = 1L,
            nombre = "Nacho", cargo = "Jefe", area = AreaContacto.RRHH, correo = "nacho@rrhh.cl", telefono = "123456789",
            estado = EstadoContacto.SIN_RESPUESTA, fechaUltContacto = "10/12/2026", observaciones = "Sin observaciones"
        )

        repository.actualizar(contacto)

        coVerify(exactly = 1) { contactoDao.actualizar(contacto) }
    }

    // Obtener todos
    @Test
    fun `Obtener todos los Contactos delega la tarea al dao`() = runTest {
        val contactos = listOf(ContactoEntity(id = 1L, organizacionId = 1L,
            nombre = "Nacho", cargo = "Jefe", area = AreaContacto.RRHH, correo = "nacho@rrhh.cl", telefono = "123456789",
            estado = EstadoContacto.SIN_RESPUESTA, fechaUltContacto = "10/12/2026", observaciones = "Sin observaciones"
        ))

        every { contactoDao.obtenerTodos() } returns flowOf(contactos)
        val resultado = repository.obtenerTodos().first()

        assertEquals(contactos, resultado)
    }

}