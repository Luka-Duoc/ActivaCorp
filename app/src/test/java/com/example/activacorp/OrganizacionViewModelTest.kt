package com.example.activacorp

import com.example.activacorp.data.local.entity.ComunaEntity
import com.example.activacorp.data.repository.ComunaRepository
import com.example.activacorp.data.repository.OrganizacionRepository
import com.example.activacorp.model.TipoOrganizacion
import com.example.activacorp.viewmodel.OrganizacionViewModel
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
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
        val comunaInicial = viewModel.estado.value.comunaId
        val comuna = mockk<ComunaEntity>(relaxed=true)
        viewModel.onComunaChange(comuna.id)
        val comunaFinal = viewModel.estado.value.comunaId
        assertNotEquals("No se ha podido cambiar la comuna", comunaInicial, comunaFinal)
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

}