package com.example.activacorp

import com.example.activacorp.data.local.entity.OportunidadEntity
import com.example.activacorp.data.local.entity.UsuarioEntity
import com.example.activacorp.data.repository.EventoRepository
import com.example.activacorp.data.repository.OportunidadRepository
import com.example.activacorp.data.repository.UsuarioRepository
import com.example.activacorp.model.EstadoGestion
import com.example.activacorp.model.OportunidadConEjecutivo
import com.example.activacorp.model.PotencialComercial
import com.example.activacorp.model.TipoOportunidad
import com.example.activacorp.viewmodel.OportunidadViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import kotlin.test.assertNotNull
import kotlin.test.junit5.JUnit5Asserter.assertEquals
import kotlin.test.junit5.JUnit5Asserter.assertNotEquals

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
class OportunidadViewModelTest {
    @JvmField
    @RegisterExtension
    val mainDispatcherRule = MainDispatcherRule()

    @RelaxedMockK
    private lateinit var oportunidadRepository: OportunidadRepository

    @RelaxedMockK
    private lateinit var eventoRepository: EventoRepository

    @RelaxedMockK
    private lateinit var usuarioRepository: UsuarioRepository

    private lateinit var viewModel: OportunidadViewModel

    @BeforeEach
    fun setUp() {
        viewModel = OportunidadViewModel(oportunidadRepository, eventoRepository, usuarioRepository)
    }

    @Test
    fun `obtenerUsuarios llama al repositorio y devuelve los datos`() = runTest {

        val usuarios = listOf(
            UsuarioEntity(1, nombre="Usuario1"),
            UsuarioEntity(2, nombre="Usuario2")
        )

        every { usuarioRepository.obtenerUsuarios() } returns flowOf(usuarios)

        viewModel.cargarUsuarios()

        advanceUntilIdle()

        assertNotEquals("Desmentir coincidencia", null, viewModel.estado.value.usuariosDisponibles)
        println(viewModel.estado.value.usuariosDisponibles)
    }

    @Test
    fun `si falla obtenerUsuarios entonces modifica mensajeError y este deja de ser nulo`() =
        runTest {
            every { usuarioRepository.obtenerUsuarios() } returns flow { throw RuntimeException("No hay usuarios") }
            viewModel.cargarUsuarios()
            advanceUntilIdle()
            val estadoActual = viewModel.estado.value.mensajeError
            assertNotNull(estadoActual)
        }

    @Test
    fun `onNombreChange actualiza el nombre en estado y anula el errorNombre`() = runTest {
        viewModel.onNombreChange("Juanito")
        assertEquals("Hallar coincidencia", "Juanito", viewModel.estado.value.nombre)
    }

    @Test
    fun `onDescripcionChange actualiza la descripcion en estado y anula errorDescripcion`() =
        runTest {
            viewModel.onDescripcionChange("Nueva descripcion")
            assertEquals(
                "Hallar coincidencia", "Nueva descripcion", viewModel.estado.value.descripcion
            )
        }

    @Test
    fun `onFechaDeteccionChange actualiza la fecha de deteccion en estado y anula errorFechaDescripcion`() =
        runTest {
            viewModel.onFechaDeteccionChange("01/01/2001")
            assertEquals("Hallar coincidencia", "01/01/2001", viewModel.estado.value.fechaDeteccion)
        }

    @Test
    fun `onPrioridadChange actualiza prioridad en estado y anula errorPrioridad`() = runTest {
        viewModel.onPrioridadChange("3")
        assertEquals("Hallar coincidencia", "3", viewModel.estado.value.prioridad)
    }

    @Test
    fun `onTipochange actualiza el tipo en estado`() = runTest {
        val tipoCambiado = mockk<TipoOportunidad>(relaxed = true)

        viewModel.onTipoChange(tipoCambiado)

        assertEquals(
            message = "Hallar coincidencia",
            expected = tipoCambiado,
            actual = viewModel.estado.value.tipo
        )
    }

    @Test
    fun `onFuenteChange actualiza la fuente en estado y anula el errorFuente`() = runTest {
        val fuente = "Fuente X"
        viewModel.onFuenteChange(fuente)
        assertEquals("Hallar coincidencia", fuente, viewModel.estado.value.fuente)
    }

//    @Test
//    fun `onPotencialChange actualiza el potencial en estado`() = runTest {
//
//    }
//
//    @Test
//    fun `onEstadoGestionChange actualiza el estadoGestion en estado`() = runTest {
//
//    }
//
//    @Test
//    fun `onObservacionesChange actualiza las observaciones en estado`() = runTest {
//
//    }
//
//    @Test
//    fun `onEventoIdChange actualiza el eventoid en estado`() = runTest {
//
//    }
//
//    @Test
//    fun `onResponsableChange actualiza el responsable en estado`() = runTest {
//
//    }

}