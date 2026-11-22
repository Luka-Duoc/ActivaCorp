package com.example.activacorp

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import com.example.activacorp.data.local.SessionDataStore
import com.example.activacorp.data.local.entity.UsuarioEntity
import com.example.activacorp.data.repository.UsuarioRepository
import com.example.activacorp.ui.screen.IniciarScreen
import com.example.activacorp.viewmodel.UsuarioViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class IniciarScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: UsuarioViewModel

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val sessionDataStore = SessionDataStore(context)

        runBlocking {
            sessionDataStore.limpiarSesion()
        }

        val fakeRepo = object : UsuarioRepository {
            override fun obtenerUsuarios(): Flow<List<UsuarioEntity>> = flowOf(emptyList())
            override suspend fun insertarUsuario(usuario: UsuarioEntity) {}
            override suspend fun actualizarUsuario(usuario: UsuarioEntity) {}
            override suspend fun eliminarUsuario(usuario: UsuarioEntity) {}
            override suspend fun buscarCorreo(correo: String): UsuarioEntity? = null
            override suspend fun login(correo: String, clave: String): UsuarioEntity? {
                return if (correo == "luka@ejecutivo.cl" && clave == "123456") {
                    UsuarioEntity(
                        id = 1L,
                        nombre = "Luka Ortiz",
                        correo = "luka@ejecutivo.cl",
                        clave = "123456",
                        rol = "EJECUTIVO"
                    )
                } else {
                    null
                }
            }
            override suspend fun limpiarSesion() {}
            override suspend fun eliminarEjecutivo(usuario: UsuarioEntity) {}
        }
        viewModel = UsuarioViewModel(fakeRepo, sessionDataStore)
    }

    @Test
    fun renderizaTodosLosElementosDeLaPantalla() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            IniciarScreen(navController = navController, viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("Iniciar Sesion").assertIsDisplayed()
        composeTestRule.onNodeWithText("Correo electronico").assertIsDisplayed()
        composeTestRule.onNodeWithText("Contraseña").assertIsDisplayed()
        composeTestRule.onNodeWithText("Ingresar").assertIsDisplayed()
        composeTestRule.onNodeWithText("¿No tienes cuenta? Registrate aqui").assertIsDisplayed()
    }

    @Test
    fun alternaVisibilidadDeContrasenaAlHacerClicEnElIcono() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            IniciarScreen(navController = navController, viewModel = viewModel)
        }

        composeTestRule.onNodeWithContentDescription("Mostrar clave").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Mostrar clave").performClick()
        composeTestRule.onNodeWithContentDescription("Ocultar clave").assertIsDisplayed()
    }

    @Test
    fun muestraErrorCuandoSePresionaIngresarConCamposVacios() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            IniciarScreen(navController = navController, viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("Ingresar").performClick()

        composeTestRule.waitUntil(timeoutMillis = 3_000) {
            composeTestRule
                .onAllNodesWithText("Ingresa correo y contraseña", substring = true)
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onNodeWithText("Ingresa correo y contraseña", substring = true)
            .assertIsDisplayed()
    }

    @Test
    fun iniciaSesionYNavegaACuentaCuandoCredencialesSonValidas() {
        lateinit var navController: NavHostController

        composeTestRule.setContent {
            navController = rememberNavController()
            NavHost(navController = navController, startDestination = "login") {
                composable("login") {
                    IniciarScreen(navController = navController, viewModel = viewModel)
                }
                composable("cuenta") {}
            }
        }

        composeTestRule.onNodeWithText("Correo electronico").performTextInput("luka@ejecutivo.cl")
        composeTestRule.onNodeWithText("Contraseña").performTextInput("123456")

        composeTestRule.onNodeWithText("Ingresar").performClick()

        composeTestRule.waitUntil(timeoutMillis = 3_000) {
            navController.currentBackStackEntry?.destination?.route == "cuenta"
        }

        assertEquals("cuenta", navController.currentBackStackEntry?.destination?.route)
    }

    @Test
    fun navegaARegistrarAlPresionarElBotonNoTienesCuenta() {
        lateinit var navController: NavHostController

        composeTestRule.setContent {
            navController = rememberNavController()
            NavHost(navController = navController, startDestination = "login") {
                composable("login") {
                    IniciarScreen(navController = navController, viewModel = viewModel)
                }
                composable("registrar") {}
            }
        }

        composeTestRule.onNodeWithText("¿No tienes cuenta? Registrate aqui").performClick()
        composeTestRule.waitForIdle()

        assertEquals("registrar", navController.currentBackStackEntry?.destination?.route)
    }

}