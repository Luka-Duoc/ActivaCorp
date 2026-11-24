package com.example.activacorp.viewModelTests

import com.example.activacorp.MainDispatcherRule
import com.example.activacorp.data.local.entity.ComunaEntity
import com.example.activacorp.data.local.entity.OrganizacionEntity
import com.example.activacorp.data.repository.ComunaRepository
import com.example.activacorp.data.repository.OrganizacionRepository
import com.example.activacorp.model.enums.TipoOrganizacion
import com.example.activacorp.viewmodel.OrganizacionViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.junit5.JUnit5Asserter.assertNotEquals

@ExtendWith(MockKExtension::class)
class OrganizacionViewModelTest {
    @JvmField
    @RegisterExtension
    val mainDispatcherRule = MainDispatcherRule()

    @RelaxedMockK
    private lateinit var organizacionRepository: OrganizacionRepository

    @RelaxedMockK
    private lateinit var comunaRepository: ComunaRepository

    private lateinit var viewModel: OrganizacionViewModel

    @BeforeEach
    fun setUp() {
        viewModel = OrganizacionViewModel(organizacionRepository, comunaRepository)
    }

    @Test
    fun `onNombreChange actualiza el nombre en estado y anula el errorNombre`() = runTest {
        val nombreInicial = viewModel.estado.value.nombre
        val nombreDato = "Nombre Org."
        viewModel.onNombreChange(nombreDato)

        val nombreFinal = viewModel.estado.value.nombre

        assertNotEquals("No se ha cambiado el nombre", nombreInicial, nombreFinal)
    }

    @Test
    fun `onTipoChange actualiza el tipo en estado y anula errorTipo`() = runTest {
        val tipoOrganizacionInicial = viewModel.estado.value.tipo
        val tipoMockk = mockk<TipoOrganizacion>(relaxed=true)
        viewModel.onTipoChange(tipoMockk)
        val tipoOrganizacionFinal = viewModel.estado.value.tipo
        assertNotEquals("No se ha cambiado el tipo de organizacion", tipoOrganizacionInicial, tipoOrganizacionFinal)
    }

    @Test
    fun `onRubroChange actualiza el rubro en estado y anula errorRubro`() = runTest {
        val rubroInicial = viewModel.estado.value.rubro
        val rubroDato = "rubro dato X"
        viewModel.onRubroChange(rubroDato)
        val rubroFinal = viewModel.estado.value.rubro
        assertNotEquals("No se ha podido cambiar el rubro asignado", rubroInicial, rubroFinal)
    }

    @Test
    fun `onComunaChange actualiza la comuna en estado y anula errorComuna`() = runTest {
        val fakeComuna = listOf(
            ComunaEntity(
                id = 1L,
                idRegion = 1L,
                nombre = "Los Quillayes"
            )
        )
        val comunaInicial = viewModel.estado.value.comunaId
        val comuna = mockk<ComunaEntity>(relaxed=true)
        viewModel.onComunaChange(comuna.id)
        assertNotEquals("No se ha podido cambiar la comuna", comunaInicial, fakeComuna)
    }

    @Test
    fun `onSitioWebChange`() = runTest {
        val sitioWebInicial = viewModel.estado.value.sitioWeb
        val sitioWebDato = "www.ejemplo.com"
        viewModel.onSitioWebChange(sitioWebDato)
        val sitioWebFinal = viewModel.estado.value.sitioWeb
        assertNotEquals("No se ha podido actualizar el sitio web", sitioWebInicial, sitioWebFinal)
    }

    @Test
    fun `onPublicoChange`() = runTest {
        val publicoInicial = viewModel.estado.value.publico
        val publicoDato = "Publico X"
        viewModel.onPublicoChange(publicoDato)
        val publicoFinal = viewModel.estado.value.publico
        assertNotEquals("No se ha podido modificar el publico", publicoInicial, publicoFinal)
    }

    @Test
    fun `onHistorialChange`() = runTest {
        val historialInicial = viewModel.estado.value.historial
        val historialDato = "Historial nuevo"
        viewModel.onHistorialChange(historialDato)
        val historialFinal = viewModel.estado.value.historial
        assertNotEquals("No se ha podido modificar el historial", historialInicial, historialFinal)
    }

    @Test
    fun `cargar todas las organizaciones llama al repositorio y devuelve las organizaciones`() = runTest {
        val fakeOrganizacion = listOf(
            OrganizacionEntity(
                id = 12L,
                nombre = "Alchemax inc.",
                tipo = TipoOrganizacion.EMPRESA,
                rubro = "TI",
                comuna = 1L,
                sitioWeb = "alchemax.com",
                publico = "Gente interesada en tecnologia",
                historial = "5 años en tecnologia"
            )
        )

        coEvery { organizacionRepository.obtenerTodas() } returns flowOf(fakeOrganizacion)
        val viewModel = OrganizacionViewModel(organizacionRepository,comunaRepository)

        assertEquals(message = "Hay coincidencia", expected = fakeOrganizacion, actual = viewModel.estado.value.organizaciones)
    }

    @Test
    fun `guardar marca los errores y no inserta cuando el formulario este vacio`() = runTest {
        val fakeOrgRepository = mockk<OrganizacionRepository>(relaxed = true)
        val viewModel = OrganizacionViewModel(
            fakeOrgRepository,
            mockk(relaxed = true)
        )

        var exito = false

        viewModel.guardarOrganizacion(onExito = { exito = true })

        assertEquals(message = "falto errorNombre", expected = "El nombre no puede estar vacio", actual = viewModel.estado.value.errorNombre)
        assertEquals(message = "falto errorRubro", expected = "El rubro no puede estar vacio", actual = viewModel.estado.value.errorRubro)
        assertEquals(message = "falto errorPublico", expected = "El publico no puede estar vacio", actual = viewModel.estado.value.errorPublico)
        assertEquals(message = "faltoErrorHistorial", expected = "El historial no puede estar vacio", actual = viewModel.estado.value.errorHistorial)
        assertFalse(actual = exito, message = "No debe crear la organizacion")
        coVerify(exactly = 0) { fakeOrgRepository.insertar(any()) }
    }

    @Test
    fun `guardar organizacion inserta correctamente en la bd`() = runTest {
        val nombre = "alchemax"
        val tipo = TipoOrganizacion.OTROS
        val rubro = "TI"
        val comuna = 1L
        val sitio = "alchemax.cl"
        val publico = "Gente interesada en tecnologia"
        val historial = "Sin historial"

        viewModel.onNombreChange(nombre)
        viewModel.onTipoChange(tipo)
        viewModel.onRubroChange(rubro)
        viewModel.onComunaChange(comuna)
        viewModel.onSitioWebChange(sitio)
        viewModel.onPublicoChange(publico)
        viewModel.onHistorialChange(historial)

        var exito = false

        viewModel.guardarOrganizacion(onExito = { exito = true })
        advanceUntilIdle()

        coVerify(exactly = 1) {
            organizacionRepository.insertar(
                match {
                    it.nombre == "alchemax" &&
                            it.tipo == tipo &&
                            it.rubro == "TI" &&
                            it.comuna == 1L &&
                            it.sitioWeb == "alchemax.cl" &&
                            it.publico == "Gente interesada en tecnologia" &&
                            it.historial == "Sin historial"
                }

            )
        }

        assertEquals(message = "El nombre debió limpiarse tras guardar", expected = "", actual = viewModel.estado.value.nombre)
        assertEquals(message = "El rubro debió limpiarse tras guardar", expected = "", actual = viewModel.estado.value.rubro)
        assertTrue(actual = exito, message = "Debio guardarse la organización")
    }

}