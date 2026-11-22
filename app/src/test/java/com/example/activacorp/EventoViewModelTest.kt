package com.example.activacorp

import com.example.activacorp.data.local.entity.ComunaEntity
import com.example.activacorp.data.local.entity.EventoEntity
import com.example.activacorp.data.local.entity.OrganizacionEntity
import com.example.activacorp.data.repository.ComunaRepository
import com.example.activacorp.data.repository.EventoRepository
import com.example.activacorp.data.repository.OrganizacionRepository
import com.example.activacorp.viewmodel.EventoViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import kotlin.test.junit5.JUnit5Asserter.assertEquals

@ExtendWith(MockKExtension::class)
@OptIn(ExperimentalCoroutinesApi::class)
class EventoViewModelTest {
    @JvmField
    @RegisterExtension
    val mainDispatcherRule = MainDispatcherRule()

    @RelaxedMockK
    private lateinit var eventoRepository: EventoRepository

    @RelaxedMockK
    private lateinit var comunaRepository: ComunaRepository

    @RelaxedMockK
    private lateinit var organizacionRepository: OrganizacionRepository

    private lateinit var viewModel: EventoViewModel

    @Test
    fun `obtenerTodos llama al repositorio y devuelve los datos`() = runTest {
        val fakeEventos = listOf(
            EventoEntity(
                1,
                nombre = "organizacion1",
                fechaInicio = "hoy",
                fechaTermino = "manana",
                comuna = 1,
                organizacion = 1
            )
        )

        coEvery { eventoRepository.obtenerTodos() } returns flowOf(fakeEventos)

        viewModel = EventoViewModel(eventoRepository, comunaRepository, organizacionRepository)

        assertEquals("Hay coincidencia", fakeEventos, viewModel.estado.value.eventos)

    }

    @Test
    fun `cargarOrganizaciones llama al repositorio para mostrar los datos`() = runTest {
        val fakeOrganizaciones = listOf(
            OrganizacionEntity(id = 1, nombre = "organizacion 1", comuna = 1)
        )

        coEvery { organizacionRepository.obtenerTodas() } returns flowOf(fakeOrganizaciones)

        advanceUntilIdle()

        viewModel = EventoViewModel(eventoRepository, comunaRepository, organizacionRepository)

        assertEquals("Hay coincidencia", fakeOrganizaciones, viewModel.estado.value.organizaciones)

    }

    @Test
    fun `onNombreChange actualiza el nombre en estado y anula errorNombre`() = runTest {
        viewModel = EventoViewModel(eventoRepository, comunaRepository, organizacionRepository)
        viewModel.onNombreChange("Lukitas")
        assertEquals("Hay coincidencia", "Lukitas", viewModel.estado.value.nombre)
    }

    @Test
    fun `onTipoChange actualiza el tipo en estado`() = runTest {
        viewModel = EventoViewModel(eventoRepository, comunaRepository, organizacionRepository)
        viewModel.onTipoChange("Tipazo")
        assertEquals("Hay coincidencia", "Tipazo", viewModel.estado.value.tipo)
    }

    @Test
    fun `onFechaInicioChange actualiza la fecha de inicio en estado y anula errorFecha`() =
        runTest {
            viewModel = EventoViewModel(eventoRepository, comunaRepository, organizacionRepository)
            viewModel.onFechaInicioChange("01/01/1969")
            assertEquals("Hay coincidencia", "01/01/1969", viewModel.estado.value.fechaInicio)
        }

    @Test
    fun `onFechaTerminoChange actualiza la fecha de termino en estado y anula errorFecha`() =
        runTest {
            viewModel = EventoViewModel(eventoRepository, comunaRepository, organizacionRepository)

            viewModel.onFechaTerminoChange("01/01/2000")
            assertEquals("Hay coincidencia", "01/01/2000", viewModel.estado.value.fechaTermino)
        }

    @Test
    fun `onDireccionChange actualiza la direccion en estado y anula errorDireccion`() = runTest {
        viewModel = EventoViewModel(eventoRepository, comunaRepository, organizacionRepository)
        viewModel.onDireccionChange("calle 123")
        assertEquals("Hay coincidencia", expected = "calle 123", viewModel.estado.value.direccion)
    }

    @Test
    fun `onCantAsistentesChange actualiza la cantidad de asistentes en estado guardando solo digitos`() =
        runTest {
            viewModel = EventoViewModel(eventoRepository, comunaRepository, organizacionRepository)
            viewModel.onCantAsistentesChange("123")
            assertEquals(
                "Hay coincidencia",
                expected = "123",
                actual = viewModel.estado.value.cantAsistentes
            )
        }

    @Test
    fun `onCostosReferencialesChange actualiza los costos referenciales en estado guardando solo digitos`() =
        runTest {
            viewModel = EventoViewModel(eventoRepository, comunaRepository, organizacionRepository)
            viewModel.onCostosReferencialesChange("15000")
            assertEquals(
                "Hay coincidencia",
                expected = "15000",
                actual = viewModel.estado.value.costosReferenciales
            )
        }

    @Test
    fun `onRequisitosChange actualiza los requisitos en estado y anula errorRequisitos`() =
        runTest {
            viewModel = EventoViewModel(eventoRepository, comunaRepository, organizacionRepository)
            viewModel.onRequisitosChange("Nuevos requisitos")
            assertEquals(
                "Hay coincidencia",
                expected = "Nuevos requisitos",
                actual = viewModel.estado.value.requisitos
            )

        }

    @Test
    fun `onObservacionesChange actualiza las observaciones en estado`() = runTest {
        viewModel = EventoViewModel(eventoRepository, comunaRepository, organizacionRepository)
        viewModel.onObservacionesChange("Observacion 1")
        assertEquals(
            message = "Hay coincidencia",
            expected = "Observacion 1",
            actual = viewModel.estado.value.observaciones
        )
    }

    @Test
    fun `onOrganizacionChange actualiza el id de organizacion en estado`() = runTest {
        val id: Long = 5
        viewModel = EventoViewModel(eventoRepository, comunaRepository, organizacionRepository)
        viewModel.onOrganizacionChange(id)
        assertEquals(
            message = "Hay coincidencia",
            expected = id,
            actual = viewModel.estado.value.organizacion
        )

    }

    @Test
    fun `onComunaSeleccionada actualiza el id y nombre de comuna en estado`() = runTest {

        val comuna = ComunaEntity(101, 1, nombre = "Puente Alto")
        viewModel = EventoViewModel(eventoRepository, comunaRepository, organizacionRepository)

        viewModel.onComunaSeleccionada(comuna)

        assertEquals("Hay coincidencia", comuna.id, viewModel.estado.value.comuna)
        assertEquals("Hay coincidencia", comuna.nombre, viewModel.estado.value.comunaSeleccionada)

    }

    @Test
    fun `eliminarEvento toma un evento y lo borra`() = runTest {

        // Crear un eventoMock.

        val evento = EventoEntity(
            nombre = "Evento X",
            fechaInicio = "Hoy",
            fechaTermino = "Desconocida",
            comuna = 1L,
            organizacion = 1L
        )

        // crear un MutableStateFlow para simular la llamada a base de datos.

        val eventosFlow = MutableStateFlow(listOf(evento))


        // Usamos every porque obtenerTodos no es una funcion suspend. Se le manda a retornar el MutableStateFlow creado.

        every { eventoRepository.obtenerTodos() } returns eventosFlow

        /* Usamos ahora coEvery para llamar al repositorio mockeado y
        eliminar el evento mock. Se le entrega a eventosFlow una
        lista vacia, como ocurriria al borrar  el evento del repositorio.*/

        coEvery { eventoRepository.eliminar(evento) } coAnswers {
            eventosFlow.value = emptyList()
        }

        /* Instanciamos al viewModel y usamos advanceUntilIdle para asegurarnos de
        que el repositorio mock tome efecto. */

        viewModel = EventoViewModel(eventoRepository, comunaRepository, organizacionRepository)
        advanceUntilIdle()

        /* Tomamos el primer registro de los eventos, ahora presentes en el estado. */

        val eventoAgregado = viewModel.estado.value.eventos[0]

        // llamamos a eliminarEvento en el viewModel.

        viewModel.eliminarEvento(eventoAgregado)
        advanceUntilIdle()

        // Comprobamos que el repositorio ya no existe.

        assertEquals(
            "No se ha podido eliminar el evento",
            emptyList<EventoEntity>(),
            viewModel.estado.value.eventos
        )
        coVerify { eventoRepository.eliminar(eventoAgregado) }
    }

}