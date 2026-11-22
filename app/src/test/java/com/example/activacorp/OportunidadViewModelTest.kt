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
import org.junit.jupiter.api.Assertions.assertTrue
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
    fun `cargarUsuarios llama al repositorio y devuelve los datos`() = runTest {

        val usuarios = listOf(
            UsuarioEntity(1, nombre = "Usuario1"),
            UsuarioEntity(2, nombre = "Usuario2")
        )

        every { usuarioRepository.obtenerUsuarios() } returns flowOf(usuarios)

        viewModel.cargarUsuarios()

        advanceUntilIdle()

        assertNotEquals(
            "Error al cargar usuarios",
            null,
            viewModel.estado.value.usuariosDisponibles
        )
        println(viewModel.estado.value.usuariosDisponibles)
    }

    @Test
    fun `si falla cargarUsuarios entonces modifica mensajeError y este deja de ser nulo`() =
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

    @Test
    fun `onPotencialChange actualiza el potencial en estado`() = runTest {
        val potencial = mockk<PotencialComercial>(relaxed = true)
        viewModel.onPotencialChange(potencial);
        assertEquals(
            "El potencial no se ha modificado",
            potencial,
            viewModel.estado.value.potencial
        );
    }

    @Test
    fun `onEstadoGestionChange actualiza el estadoGestion en estado`() = runTest {
        val estadoGestion = mockk<EstadoGestion>(relaxed = true)
        viewModel.onEstadoGestionChange(estadoGestion)
        assertEquals(
            "El estadoGestion no se ha modificado",
            estadoGestion,
            viewModel.estado.value.estado
        )
    }

    @Test
    fun `onObservacionesChange actualiza las observaciones en estado`() = runTest {
        val observacion = "Observacion de prueba"
        viewModel.onObservacionesChange(observacion)
        assertEquals(
            "La observacion no se ha agregado",
            observacion,
            viewModel.estado.value.observaciones
        )
    }

    @Test
    fun `onEventoIdChange actualiza el eventoid en estado`() = runTest {
        val eventoId: Long = 1L
        viewModel.onEventoIdChange(eventoId)
        assertEquals("No se ha cambiado el id de evento", eventoId, viewModel.estado.value.eventoId)
    }

    @Test
    fun `onResponsableChange actualiza el responsable en estado`() = runTest {
        val responsableId: Long = 1L
        viewModel.onResponsableChange(responsableId)
        assertEquals(
            "No se ha modificado al responsable",
            responsableId,
            viewModel.estado.value.responsable
        )
    }

    @Test
    fun `prepararEdicion actualiza los datos de la oportunidad que recibe`() = runTest {
        val tipoOportunidadMock = mockk<TipoOportunidad>(relaxed = true)
        val potencialMock = mockk<PotencialComercial>(relaxed = true)
        val estadoGestionMock = mockk<EstadoGestion>(relaxed = true)
        val oportunidadMock = OportunidadEntity(
            id = 404L,
            nombre = "Oportunidad de prueba",
            prioridad = 1,
            tipo = tipoOportunidadMock,
            potencial = potencialMock,
            estado = estadoGestionMock
        )
        viewModel.prepararEdicion(oportunidadMock)
        assertEquals(
            "No se ha recibido el id de la oportunidad a modificar",
            oportunidadMock.id,
            viewModel.estado.value.oportunidadIdEnEdicion
        )
    }

    @Test
    fun `obtenerOportunidadesPorUsuario `() = runTest {
        val oportunidadMock = OportunidadConEjecutivo(
            nombre = "Oportunidad de prueba",
            descripcion = "Ninguna descripcion relevante."
        )
        val oportunidades = listOf(
            oportunidadMock
        )

        every { oportunidadRepository.obtenerOportunidadesPorUsuario(505L) } returns flow { oportunidades }


        val oportunidadesPorUsuario = viewModel.estado.value.oportunidadUsuario



        assertEquals(
            "Error al encontrar oportunidades al usuario dado",
            oportunidades,
            oportunidadesPorUsuario
        )

    }

}