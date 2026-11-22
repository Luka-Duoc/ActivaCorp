package com.example.activacorp

import com.example.activacorp.data.local.entity.OrganizacionEntity
import com.example.activacorp.data.repository.ContactoRepository
import com.example.activacorp.data.repository.OrganizacionRepository
import com.example.activacorp.model.AreaContacto
import com.example.activacorp.model.EstadoContacto
import com.example.activacorp.viewmodel.ContactoViewModel
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class ContactoViewModelTest {

    @JvmField
    @RegisterExtension
    val mainDispatcherRule = MainDispatcherRule()

    private val contactoRepository: ContactoRepository = mockk(relaxed = true)
    private val organizacionRepository: OrganizacionRepository = mockk(relaxed = true)

    private lateinit var viewModel: ContactoViewModel

    @BeforeEach
    fun setUp() {
        viewModel = ContactoViewModel(contactoRepository, organizacionRepository)
    }

    @Test
    fun `onNombreChange actualiza el nombre en estado y limpia errorNombre`() = runTest {
        viewModel.guardar()
        viewModel.onNombreChange("Camila Rojas")

        assertEquals("Camila Rojas", viewModel.estado.value.nombre, "El nombre no se actualizo")
        assertNull(viewModel.estado.value.errorNombre, "El error de nombre debe limpiarse")
    }

    @Test
    fun `onCargoChange actualiza el cargo en estado y limpia errorCargo`() = runTest {
        viewModel.guardar()
        viewModel.onCargoChange("Jefa de Bienestar")

        assertEquals("Jefa de Bienestar", viewModel.estado.value.cargo, "El cargo no se actualizo")
        assertNull(viewModel.estado.value.errorCargo, "El error de cargo debe limpiarse")
    }

    @Test
    fun `onAreaChange actualiza el area en estado`() = runTest {
        val area = mockk<AreaContacto>(relaxed = true)
        viewModel.onAreaChange(area)

        assertEquals(area, viewModel.estado.value.area, "El area no se actualizo")
    }

    @Test
    fun `onCorreoChange actualiza el correo en estado y limpia ErrorContacto`() = runTest {
        viewModel.guardar()
        viewModel.onCorreoChange("camila@empresa.cl")

        assertEquals("camila@empresa.cl", viewModel.estado.value.correo, "El correo no se actualizo")
        assertNull(viewModel.estado.value.ErrorContacto, "El error de contacto debe limpiarse")
    }

    @Test
    fun `onTelefonoChange actualiza el telefono en estado y limpia ErrorContacto`() = runTest {
        viewModel.guardar()
        viewModel.onTelefonoChange("987654321")

        assertEquals("987654321", viewModel.estado.value.telefono, "El telefono no se actualizo")
        assertNull(viewModel.estado.value.ErrorContacto, "El error de contacto debe limpiarse")
    }

    @Test
    fun `onEstadoChange actualiza el estado de contacto`() = runTest {
        val estadoContacto = mockk<EstadoContacto>(relaxed = true)
        viewModel.onEstadoChange(estadoContacto)

        assertEquals(estadoContacto, viewModel.estado.value.estado, "El estado de contacto no se actualizo")
    }

    @Test
    fun `onObservacionesChange actualiza las observaciones en estado`() = runTest {
        viewModel.onObservacionesChange("Contactar por las mañanas")

        assertEquals("Contactar por las mañanas", viewModel.estado.value.observaciones, "Las observaciones no se actualizaron")
    }

    @Test
    fun `onOrganizacionSeleccionada actualiza organizacionId, nombre y limpia ErrorOrganizacion`() = runTest {
        viewModel.guardar()
        val organizacion = OrganizacionEntity(id = 4L, nombre = "Alchemax", comuna = 1L)

        viewModel.onOrganizacionSeleccionada(organizacion)

        assertEquals(4L, viewModel.estado.value.organizacionId, "El ID de organización no coincide")
        assertEquals("Alchemax", viewModel.estado.value.organizacionSeleccionada, "El nombre de organización no coincide")
        assertNull(viewModel.estado.value.ErrorOrganizacion, "El error de organizacion debe limpiarse")
    }

    @Test
    fun `guardar muestra errores y no inserta cuando el formulario es invalido`() = runTest {
        var exito = false

        viewModel.guardar(onExito = { exito = true })
        advanceUntilIdle()

        assertEquals("Ingresa un nombre (mínimo 3 caracteres)", viewModel.estado.value.errorNombre)
        assertEquals("Campo obligatorio", viewModel.estado.value.errorCargo)
        assertEquals("Selecciona una organización", viewModel.estado.value.ErrorOrganizacion)
        assertEquals("Ingresa un correo o un teléfono", viewModel.estado.value.ErrorContacto)
        assertFalse(exito, "No debe guardar con formulario invalido")
        coVerify(exactly = 0) { contactoRepository.insertar(any()) }
    }

    @Test
    fun `guardar inserta nuevo contacto con exito`() = runTest {
        val org = OrganizacionEntity(id = 3L, nombre = "ActivaCorp", comuna = 1L)
        val area = mockk<AreaContacto>(relaxed = true)
        val estadoContacto = mockk<EstadoContacto>(relaxed = true)

        viewModel.onOrganizacionSeleccionada(org)
        viewModel.onNombreChange("  Valeria Muñoz  ")
        viewModel.onCargoChange("  Coordinadora RRHH  ")
        viewModel.onAreaChange(area)
        viewModel.onCorreoChange("  valeria@ejecutivo.cl  ")
        viewModel.onTelefonoChange("  912345678  ")
        viewModel.onEstadoChange(estadoContacto)
        viewModel.onObservacionesChange("  Contacto principal  ")

        var exito = false
        viewModel.guardar(onExito = { exito = true })
        advanceUntilIdle()

        coVerify(exactly = 1) {
            contactoRepository.insertar(
                match {
                    it.id == 0L &&
                            it.organizacionId == 3L &&
                            it.nombre == "Valeria Muñoz" &&
                            it.cargo == "Coordinadora RRHH" &&
                            it.area == area &&
                            it.correo == "valeria@ejecutivo.cl" &&
                            it.telefono == "912345678" &&
                            it.estado == estadoContacto &&
                            it.observaciones == "Contacto principal"
                }
            )
        }

        assertEquals("", viewModel.estado.value.nombre, "El nombre debe limpiarse tras guardar")
        assertEquals("", viewModel.estado.value.cargo, "El cargo debe limpiarse tras guardar")
        assertEquals(0L, viewModel.estado.value.organizacionId, "organizacionId debe resetearse a 0L")
        assertTrue(exito, "Debe ejecutarse con exito")
    }
}