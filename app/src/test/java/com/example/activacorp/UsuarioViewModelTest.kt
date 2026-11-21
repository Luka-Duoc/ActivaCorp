package com.example.activacorp

import com.example.activacorp.data.local.SessionDataStore
import com.example.activacorp.data.repository.UsuarioRepository
import com.example.activacorp.viewmodel.UsuarioViewModel
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import kotlin.test.junit5.JUnit5Asserter.assertEquals
import kotlin.test.junit5.JUnit5Asserter.assertNotEquals

@ExtendWith(MockKExtension::class)
class UsuarioViewModelTest {
    @JvmField
    @RegisterExtension
    val mainDispatcherRule = MainDispatcherRule()

    @RelaxedMockK
    private lateinit var usuarioRepository: UsuarioRepository

    @RelaxedMockK
    private lateinit var sessionDataStore: SessionDataStore

    private lateinit var viewModel: UsuarioViewModel

    @BeforeEach
    fun setUp() {
        viewModel = UsuarioViewModel(usuarioRepository, sessionDataStore)
    }

    @Test
    fun `onNombreChange actualiza el nombre en estado`() = runTest {
        val nombreInicial = viewModel.estado.value.nombre
        val nombreDato = "Lucas"
        viewModel.onNombreChange(nombreDato)

        val nombreFinal = viewModel.estado.value.nombre
        assertNotEquals("No se pudo cambiar el nombre", nombreInicial, nombreFinal)
    }

    @Test
    fun `onCorreoChange actualiza el correo en estado`() = runTest {
        val correoInicial = viewModel.estado.value.correo
        val correoDato = "correo@gmail.com"
        viewModel.onCorreoChange(correoDato)

        val correoFinal = viewModel.estado.value.correo
        assertNotEquals("No se pudo cambiar el correo", correoInicial, correoFinal)
    }

    @Test
    fun `onClaveChange actualiza la clave en estado`() = runTest {
        val claveInicial = viewModel.estado.value.clave
        val claveDato = "clave123456"
        viewModel.onClaveChange(claveDato)
        val claveFinal = viewModel.estado.value.clave
        assertNotEquals("No se pudo cambiar la clave", claveInicial, claveFinal)
    }

}