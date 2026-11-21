package com.example.activacorp

import com.example.activacorp.data.local.SessionDataStore
import com.example.activacorp.data.local.entity.UsuarioEntity
import com.example.activacorp.data.repository.UsuarioRepository
import com.example.activacorp.model.RolUsuario
import com.example.activacorp.viewmodel.UsuarioViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import kotlin.test.assertFalse
import kotlin.test.assertTrue
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

    @Test
    fun  `guardar no inserta al usuario si el formulario no es valido`() = runTest {
        var callbackEjecutado = false

        viewModel.guardar(onListo = { callbackEjecutado = true })
        advanceUntilIdle()
        assertFalse(callbackEjecutado, "Error en formulario")
        coVerify(exactly = 0) { usuarioRepository.buscarCorreo(any()) }
        coVerify(exactly = 0) { usuarioRepository.insertarUsuario(any()) }

    }

    @Test
    fun `guardar muestra error de correo duplicado y no inserta si el correo ya existe en BD`() = runTest {
        val correoRepetido = "test@ejecutivo.cl"
        viewModel.onNombreChange("Ana Gómez")
        viewModel.onCorreoChange(correoRepetido)
        viewModel.onClaveChange("segura123")

        coEvery { usuarioRepository.buscarCorreo(correoRepetido) } returns UsuarioEntity(
            id = 10L,
            nombre = "Usuario Previo",
            correo = correoRepetido,
            clave = "otraClave",
            rol = "EJECUTIVO"
        )

        var callbackEjecutado = false

        viewModel.guardar(onListo = { callbackEjecutado = true })
        advanceUntilIdle()

        if (RolUsuario.asignarCorreo(correoRepetido) != null) {
            assertEquals(
                message = "No se mostró el error de correo duplicado",
                expected = "Este correo ya esta registrado",
                actual =  viewModel.estado.value.errores.correo
            )
            assertFalse(callbackEjecutado, "No debió navegar ni ejecutar onListo con correo duplicado")
            coVerify(exactly = 0) { usuarioRepository.insertarUsuario(any()) }
        }
    }

    @Test
    fun `guardar inserta el nuevo usuario, limpia el formulario cuando el registro es exitoso`() = runTest {
        val correoNuevo = "nuevo@ejecutivo.cl"
        viewModel.onNombreChange("María Silva")
        viewModel.onCorreoChange(correoNuevo)
        viewModel.onClaveChange("password123")

        coEvery { usuarioRepository.buscarCorreo(correoNuevo) } returns null

        var callbackEjecutado = false

        viewModel.guardar(onListo = { callbackEjecutado = true })
        advanceUntilIdle()

        if (RolUsuario.asignarCorreo(correoNuevo) != null) {
            coVerify(exactly = 1) {
                usuarioRepository.insertarUsuario(
                    match {
                        it.nombre == "María Silva" &&
                                it.correo == correoNuevo &&
                                it.clave == "password123"
                    }
                )
            }
            assertEquals(message = "El nombre debió limpiarse tras guardar", expected =  "", actual = viewModel.estado.value.nombre)
            assertEquals( message = "El correo debió limpiarse tras guardar", expected =  "", actual = viewModel.estado.value.correo)
            assertTrue(actual = callbackEjecutado, message = "Debió ejecutarse onListo al completar el registro")
        }
    }

}