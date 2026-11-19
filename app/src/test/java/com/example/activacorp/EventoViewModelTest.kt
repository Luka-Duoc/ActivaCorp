package com.example.activacorp

import com.example.activacorp.data.local.entity.ComunaEntity
import com.example.activacorp.data.local.entity.EventoEntity
import com.example.activacorp.data.local.entity.OrganizacionEntity
import com.example.activacorp.data.repository.ComunaRepository
import com.example.activacorp.data.repository.EventoRepository
import com.example.activacorp.data.repository.OrganizacionRepository
import com.example.activacorp.viewmodel.EventoViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import kotlin.test.junit5.JUnit5Asserter.assertEquals

class EventoViewModelTest {
    @JvmField
    @RegisterExtension
    val mainDispatcherRule = MainDispatcherRule()

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

        val fakeEventoRepository = mockk<EventoRepository>()
        val fakeComunaRepository = mockk<ComunaRepository>(relaxed = true)
        val fakeOrganizacionRepository = mockk<OrganizacionRepository>(relaxed = true)

        coEvery { fakeEventoRepository.obtenerTodos() } returns flowOf(fakeEventos)
        val viewModel =
            EventoViewModel(fakeEventoRepository, fakeComunaRepository, fakeOrganizacionRepository)

        assertEquals("Hay coincidencia", fakeEventos, viewModel.estado.value.eventos)

    }

    @Test
    fun `cargarOrganizaciones llama al repositorio para mostrar los datos`() = runTest {
        val fakeOrganizaciones = listOf(
            OrganizacionEntity(id = 1, nombre = "organizacion 1", comuna = 1)
        )

        val fakeOrganizacionesRepository = mockk<OrganizacionRepository>()
        val fakeComunaRepository = mockk<ComunaRepository>(relaxed = true)
        val fakeEventoRepository = mockk<EventoRepository>(relaxed = true)

        coEvery { fakeOrganizacionesRepository.obtenerTodas() } returns flowOf(fakeOrganizaciones)

        val viewModel = EventoViewModel(
            fakeEventoRepository,
            fakeComunaRepository,
            fakeOrganizacionesRepository
        )

        assertEquals("Hay coincidencia", fakeOrganizaciones, viewModel.estado.value.organizaciones)

    }

    @Test
    fun `onNombreChange actualiza el nombre en estado y anula errorNombre`() = runTest {
        val fakeOrganizacionRepository = mockk<OrganizacionRepository>(relaxed = true)
        val fakeEventoRepository = mockk<EventoRepository>(relaxed = true)
        val fakeComunaRepository = mockk<ComunaRepository>(relaxed = true)

        val viewModel =
            EventoViewModel(fakeEventoRepository, fakeComunaRepository, fakeOrganizacionRepository)

        viewModel.onNombreChange("Lukitas")
        assertEquals("Hay coincidencia", "Lukitas", viewModel.estado.value.nombre)


    }

    @Test
    fun `onTipoChange actualiza el tipo en estado`() = runTest {
        val fakeOrganizacionRepository = mockk<OrganizacionRepository>(relaxed = true)
        val fakeEventoRepository = mockk<EventoRepository>(relaxed = true)
        val fakeComunaRepository = mockk<ComunaRepository>(relaxed = true)

        val viewModel =
            EventoViewModel(fakeEventoRepository, fakeComunaRepository, fakeOrganizacionRepository)

        viewModel.onTipoChange("Tipazo")
        assertEquals("Hay coincidencia", "Tipazo", viewModel.estado.value.tipo)
    }

    @Test
    fun `onFechaInicioChange actualiza la fecha de inicio en estado y anula errorFecha`() =
        runTest {
            val fakeOrganizacionRepository = mockk<OrganizacionRepository>(relaxed = true)
            val fakeComunaRepository = mockk<ComunaRepository>(relaxed = true)
            val fakeEventoRepository = mockk<EventoRepository>(relaxed = true)

            val viewModel = EventoViewModel(
                fakeEventoRepository,
                fakeComunaRepository,
                fakeOrganizacionRepository
            )

            viewModel.onFechaInicioChange("01/01/1969")
            assertEquals("Hay coincidencia", "01/01/1969", viewModel.estado.value.fechaInicio)
        }

    @Test
    fun `onFechaTerminoChange actualiza la fecha de termino en estado y anula errorFecha`() =
        runTest {
            val fakeOrganizacionRepository = mockk<OrganizacionRepository>(relaxed = true)
            val fakeEventoRepository = mockk<EventoRepository>(relaxed = true)
            val fakeComunaRepository = mockk<ComunaRepository>(relaxed = true)

            val viewModel = EventoViewModel(
                fakeEventoRepository,
                fakeComunaRepository,
                fakeOrganizacionRepository
            )

            viewModel.onFechaTerminoChange("01/01/2000")
            assertEquals("Hay coincidencia", "01/01/2000", viewModel.estado.value.fechaTermino)
        }

    @Test
    fun `onDireccionChange actualiza la direccion en estado y anula errorDireccion`() = runTest {
        val fakeOrganizacionRepository = mockk<OrganizacionRepository>(relaxed = true)
        val fakeComunaRepository = mockk<ComunaRepository>(relaxed = true)
        val fakeEventoRepository = mockk<EventoRepository>(relaxed = true)

        val viewModel = EventoViewModel(
            fakeEventoRepository,
            fakeComunaRepository,
            fakeOrganizacionRepository
        )

        viewModel.onDireccionChange("calle 123")
        assertEquals("Hay coincidencia", expected = "calle 123", viewModel.estado.value.direccion)
    }

    @Test
    fun `onCantAsistentesChange actualiza la cantidad de asistentes en estado guardando solo digitos`() =
        runTest {
            val fakeOrganizacionRepository = mockk<OrganizacionRepository>(relaxed = true)
            val fakeComunaRepository = mockk<ComunaRepository>(relaxed = true)
            val fakeEventoRepository = mockk<EventoRepository>(relaxed = true)

            val viewModel = EventoViewModel(
                fakeEventoRepository,
                fakeComunaRepository,
                fakeOrganizacionRepository
            )

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
            val viewModel = EventoViewModel(
                mockk<EventoRepository>(relaxed = true),
                mockk<ComunaRepository>(relaxed = true),
                mockk<OrganizacionRepository>(relaxed = true)
            )

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
            val viewModel = EventoViewModel(
                mockk<EventoRepository>(relaxed = true),
                mockk<ComunaRepository>(relaxed = true),
                mockk<OrganizacionRepository>(relaxed = true)
            )

            viewModel.onRequisitosChange("Nuevos requisitos")
            assertEquals(
                "Hay coincidencia",
                expected = "Nuevos requisitos",
                actual = viewModel.estado.value.requisitos
            )

        }

    @Test
    fun `onObservacionesChange actualiza las observaciones en estado`() = runTest {
        val viewModel = EventoViewModel(
            mockk<EventoRepository>(relaxed = true),
            mockk<ComunaRepository>(relaxed = true),
            mockk<OrganizacionRepository>(relaxed = true)
        )

        viewModel.onObservacionesChange("Observacion 1")
        assertEquals(
            message = "Hay coincidencia",
            expected = "Observacion 1",
            actual = viewModel.estado.value.observaciones
        )
    }

    @Test
    fun `onOrganizacionChange actualiza el id de organizacion en estado`() = runTest {
        val viewModel = EventoViewModel(
            mockk<EventoRepository>(relaxed = true),
            mockk<ComunaRepository>(relaxed = true),
            mockk<OrganizacionRepository>(relaxed = true)
        )

        val id: Long = 5

        viewModel.onOrganizacionChange(id)
        assertEquals(
            message = "Hay coincidencia",
            expected = id,
            actual = viewModel.estado.value.organizacion
        )

    }

    @Test
    fun `onComunaSeleccionada actualiza el id y nombre de comuna en estado`() = runTest{

        val comuna: ComunaEntity = ComunaEntity(101, 1, nombre = "Puente Alto")

        val viewModel = EventoViewModel(mockk<EventoRepository>(relaxed=true), mockk<ComunaRepository>(relaxed=true), mockk<OrganizacionRepository>(relaxed=true))

        viewModel.onComunaSeleccionada(comuna)

        assertEquals("Hay coincidencia", comuna.id, viewModel.estado.value.comuna)
        assertEquals("Hay coincidencia", comuna.nombre, viewModel.estado.value.comunaSeleccionada)

    }

}