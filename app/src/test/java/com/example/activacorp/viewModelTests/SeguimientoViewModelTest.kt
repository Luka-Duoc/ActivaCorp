package com.example.activacorp.viewModelTests

import com.example.activacorp.MainDispatcherRule
import com.example.activacorp.data.local.entity.OportunidadEntity
import com.example.activacorp.data.local.entity.ResultadoEntity
import com.example.activacorp.data.local.entity.SeguimientoEntity
import com.example.activacorp.data.local.entity.UsuarioEntity
import com.example.activacorp.data.repository.OportunidadRepository
import com.example.activacorp.data.repository.ResultadoRepository
import com.example.activacorp.data.repository.SeguimientoRepository
import com.example.activacorp.data.repository.UsuarioRepository
import com.example.activacorp.model.GestionesPartConfirmada
import com.example.activacorp.model.enums.EstadoGestion
import com.example.activacorp.model.enums.PotencialComercial
import com.example.activacorp.model.enums.TipoDeAccion
import com.example.activacorp.model.enums.TipoOportunidad
import com.example.activacorp.viewmodel.SeguimientoViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import kotlin.test.junit5.JUnit5Asserter.assertEquals
import kotlin.test.junit5.JUnit5Asserter.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
class SeguimientoViewModelTest {
    @JvmField
    @RegisterExtension
    val mainDispatcherRule = MainDispatcherRule()

    @RelaxedMockK
    private lateinit var seguimientoRepository: SeguimientoRepository

    @RelaxedMockK
    private lateinit var usuarioRepository: UsuarioRepository

    @RelaxedMockK
    private lateinit var oportunidadRepository: OportunidadRepository

    @RelaxedMockK
    private lateinit var resultadoRepository: ResultadoRepository

    private lateinit var viewModel: SeguimientoViewModel

    @BeforeEach
    fun setUp() {
        viewModel = SeguimientoViewModel(
            seguimientoRepository,
            usuarioRepository,
            oportunidadRepository,
            resultadoRepository
        )

    }

    @Test
    fun `cargarSeguimientos llama al repository y devuelve seguimientos`() = runTest {
        val accion = TipoDeAccion.PRIMER_CONTACTO
        val seguimientos = listOf(
            SeguimientoEntity(
                id = 1,
                oportunidadRelacionada = 1,
                usuarioResponsable = 1,
                tipoDeAccion = accion,
                fecha = "13/09/2026",
                hora = "15:00",
                proximaAccion = accion,
                resultado = 1,
                fechaProxSeguimiento = "14/10/2026",
                observaciones = "Ninguna"
            )
        )
        every { seguimientoRepository.obtenerSeguimientos() } returns flowOf(seguimientos)
        viewModel.cargarSeguimientos()
        advanceUntilIdle()
        assertNotEquals("No se han cargado seguimientos", null, viewModel.estado.value.seguimientos)
    }

    @Test
    fun `cargarOportunidades llama al repository y devuelve oportunidades`() = runTest {

        val tipo = TipoOportunidad.ACTIVIDAD_MUNICIPAL
        val potencial = PotencialComercial.ALTO
        val estado = EstadoGestion.PROPUESTA_ENVIADA

        val oportunidades = listOf(
            OportunidadEntity(
                id = 1L,
                eventoId = 1L,
                nombre = "Oportunidad X",
                descripcion = "Desc",
                fechaDeteccion = "2026-10-12",
                responsable = 1L,
                prioridad = 5,
                tipo = tipo,
                potencial = potencial,
                estado = estado
            )
        )
        every { oportunidadRepository.obtenerTodas() } returns flowOf(oportunidades)
        viewModel.cargarOportunidades()
        advanceUntilIdle()
        assertNotEquals(
            "No se cargaron oportunidades",
            null,
            viewModel.estado.value.oportunidadesDisponibles
        )
    }

    @Test
    fun `cargarResultados llama al repository y devuelve resultados`() = runTest {
        val estado = EstadoGestion.PARTICIPACION_CONFIRMADA
        val resultados = listOf(
            ResultadoEntity(
                id = 1L,
                fechaRealizacion = "2026-10-12",
                estadoFinal = estado,
                cantidadEstimadaAsistentes = 15,
                cantidadContactosGenerados = 10,
                cantidadPersonasInteresadas = 5,
                cantidadInscripciones = 20,
                observaciones = "Ninguna",
                evaluacionGeneral = "Perfecto",
                aprendizajes = "No."
            )
        )
        every { resultadoRepository.obtenerResultados() } returns flowOf(resultados)
        viewModel.cargarResultados()
        advanceUntilIdle()
        assertNotEquals("No se cargaron resultados", null, viewModel.estado.value.resultado)
    }

    @Test
    fun `obtenerGestionesPartConfirmada llama al repository, si encuentra datos los copia a gestiones`() =
        runTest {
            val accion = TipoDeAccion.PRIMER_CONTACTO
            val estado = EstadoGestion.PROPUESTA_ENVIADA
            val gestiones = listOf(
                GestionesPartConfirmada("Gestion X", "10/10/2026", "05:16", accion,estado)
            )

            every { seguimientoRepository.obtenerGestionesPartConfirmada()} returns flowOf(gestiones)
            viewModel.obtenerGestionesPartConfirmada()
            advanceUntilIdle()
            assertNotEquals("No se cargaron gestiones", null, viewModel.estado.value.gestiones)
        }
    @Test
    fun `cargarUsuarios llama al repository y devuelve usuarios`() = runTest{
        val usuarios = listOf(
            UsuarioEntity(nombre = "Usuario", correo="correo@gmail.com")
        )
        every { usuarioRepository.obtenerUsuarios() } returns flowOf(usuarios)
        viewModel.cargarUsuarios()
        advanceUntilIdle()
        assertNotEquals("No se cargaron usuarios", null, viewModel.estado.value.usuariosDisponibles)
    }

    @Test
    fun `onOportunidadChange`() = runTest{
        val oportunidadInicial = viewModel.estado.value.oportunidadRelacionada
        val oportunidadDato = 1L
        viewModel.onOportunidadChange(oportunidadDato)
        val oportunidadFinal = viewModel.estado.value.oportunidadRelacionada
        assertNotEquals("No se ha podido cambiar la oportunidad relacionada", oportunidadInicial, oportunidadFinal)
    }

    @Test
    fun `onUsuarioResponsableChange`() = runTest {
        val usuarioInicial = viewModel.estado.value.usuarioResponsable
        val usuarioDato = 1L
        viewModel.onUsuarioResponsableChange(usuarioDato)
        val usuarioFinal = viewModel.estado.value.usuarioResponsable
        assertNotEquals("No se pudo cambiar al usuario responsable", usuarioInicial, usuarioFinal)
    }

    @Test
    fun `onTipoAccionChange`() = runTest{
        val accionInicial = viewModel.estado.value.tipoDeAccion
        val accionDato = TipoDeAccion.CIERRE
        viewModel.onTipoAccionChange(accionDato)
        val accionFinal = viewModel.estado.value.tipoDeAccion
        assertNotEquals("No se pudo cambiar el tipo de accion", accionInicial, accionFinal)
    }

    @Test
    fun `onFechaChange`() = runTest {
        val fechaInicial = viewModel.estado.value.fecha
        val fechaDato = "10/10/2010"
        viewModel.onFechaChange(fechaDato)
        val fechaFinal = viewModel.estado.value.fecha
        assertNotEquals("No se pudo cambiar fecha", fechaInicial, fechaFinal)
    }

    @Test
    fun `onHora`() = runTest{
        val horaInicial = viewModel.estado.value.hora
        val horaDato = "05:00"
        viewModel.onHora(horaDato)
        val horaFinal = viewModel.estado.value.hora
        assertNotEquals("No se pudo cambiar la hora", horaInicial, horaFinal)
    }

    @Test
    fun `onResultadoChange`() = runTest {
        val resultadoInicial = viewModel.estado.value.resultado
        val resultadoDato = 1L
        viewModel.onResultadoChange(resultadoDato)
        val resultadoFinal = viewModel.estado.value.resultado
        assertNotEquals("No se pudo cambiar el resultado", resultadoInicial, resultadoFinal)
    }

    @Test
    fun `onProximaAccionChange`() = runTest {
        val proximaAccionInicial = viewModel.estado.value.proximaAccion
        val proximaAccionDato = TipoDeAccion.ACTIVACION_CANCELADA
        viewModel.onProximaAccionChange(proximaAccionDato)
        val proximaAccionFinal = viewModel.estado.value.proximaAccion
        assertNotEquals("Mp se pudo cambiar la proxima accion", proximaAccionInicial, proximaAccionFinal)
    }

    @Test
    fun `onFechaSeguimientoChange`() = runTest{
        val fechaSeguimientoInicial = viewModel.estado.value.fechaProxSeguimiento
        val fechaProximoSeguimientoDato = "19/10/2019"
        viewModel.onFechaSeguimientoChange(fechaProximoSeguimientoDato)
        val fechaSeguimientoFinal = viewModel.estado.value.fechaProxSeguimiento
        assertNotEquals("No se pudo cambiar la fecha de seguimiento", fechaSeguimientoInicial, fechaSeguimientoFinal)
    }

    @Test
    fun `onObservacionChange`() = runTest{
        val observacionesInicial = viewModel.estado.value.observaciones
        val observacionesDato = "Te observo."
        viewModel.onObservacionChange(observacionesDato)
        val observacionesFinal = viewModel.estado.value.observaciones
        assertNotEquals("No se pudo modificar las observaciones", observacionesInicial, observacionesFinal)
    }

    @Test
    fun `eliminar busca el registro en el repository y procede a borrarlo`() = runTest{
        val seguimiento = SeguimientoEntity(
            id = 1,
            oportunidadRelacionada = 1,
            usuarioResponsable = 1,
            tipoDeAccion = TipoDeAccion.EN_EVALUACION,
            fecha = "13/09/2026",
            hora = "15:00",
            proximaAccion = TipoDeAccion.LLAMADA,
            resultado = 1L,
            fechaProxSeguimiento = "14/10/2026",
            observaciones = "Ninguna"
        )

        val seguimientosFlow = MutableStateFlow(listOf(seguimiento))

        every { seguimientoRepository.obtenerSeguimientos() } returns seguimientosFlow

        coEvery { seguimientoRepository.eliminar(seguimiento) } coAnswers {
            seguimientosFlow.value = emptyList()
        }

        viewModel = SeguimientoViewModel(seguimientoRepository, usuarioRepository, oportunidadRepository, resultadoRepository)
        advanceUntilIdle()

        val seguimientoAgregado = viewModel.estado.value.seguimientos[0]
        viewModel.eliminar(seguimientoAgregado)
        advanceUntilIdle()

        assertEquals("No se pudo borrar el registro de seguimiento", emptyList<SeguimientoEntity>(), viewModel.estado.value.seguimientos)
        coVerify { seguimientoRepository.eliminar(seguimientoAgregado)}

    }
}