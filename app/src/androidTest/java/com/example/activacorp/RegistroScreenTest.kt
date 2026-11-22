package com.example.activacorp
import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
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
import com.example.activacorp.ui.screen.RegistroScreen
import com.example.activacorp.viewmodel.UsuarioViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import junit.framework.TestCase.assertEquals

class RegistroScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: UsuarioViewModel

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val sessionDataStore = SessionDataStore(context)

        val fakeRepo = object : UsuarioRepository {
            override fun obtenerUsuarios(): Flow<List<UsuarioEntity>> = flowOf(emptyList())
            override suspend fun insertarUsuario(usuario: UsuarioEntity) {}
            override suspend fun actualizarUsuario(usuario: UsuarioEntity) {}
            override suspend fun eliminarUsuario(usuario: UsuarioEntity) {}
            override suspend fun buscarCorreo(correo: String): UsuarioEntity? = null
            override suspend fun login(correo: String, clave: String): UsuarioEntity? = null
            override suspend fun limpiarSesion() {}
            override suspend fun eliminarEjecutivo(usuario: UsuarioEntity) {}
        }
        viewModel = UsuarioViewModel(fakeRepo, sessionDataStore)
    }

    @Test
    fun renderizaTodosLosElementosDeLaPantalla() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            RegistroScreen(navController = navController, viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("Crear Cuenta").assertIsDisplayed()
        composeTestRule.onNodeWithText("Nombre completo").assertIsDisplayed()
        composeTestRule.onNodeWithText("Correo corporativo").assertIsDisplayed()
        composeTestRule.onNodeWithText("Contraseña").assertIsDisplayed()
        composeTestRule.onNodeWithText("Registrarse").assertIsDisplayed()
        composeTestRule.onNodeWithText("¿Ya tienes cuenta? Inicia sesión aquí").assertIsDisplayed()
    }

    @Test
    fun alternaVisibilidadDeContrasenaAlHacerClicEnElIcono() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            RegistroScreen(navController = navController, viewModel = viewModel)
        }

        composeTestRule.onNodeWithContentDescription("Mostrar clave").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Mostrar clave").performClick()
        composeTestRule.onNodeWithContentDescription("Ocultar clave").assertIsDisplayed()
    }

    @Test
    fun muestraErroresCuandoSePresionaRegistrarseConCamposVacios() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            RegistroScreen(navController = navController, viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("Registrarse").performClick()
        composeTestRule.onNodeWithText("obligatorio", substring = true, ignoreCase = true).assertIsDisplayed()
    }

    @Test
    fun registraUsuarioYNavegaALoginCuandoLosDatosSonValidos() {
        lateinit var navController: NavHostController

        composeTestRule.setContent {
            navController = rememberNavController()
            NavHost(navController = navController, startDestination = "registrar") {
                composable("registrar") {
                    RegistroScreen(navController = navController, viewModel = viewModel)
                }
                composable("login") {}
            }
        }

        composeTestRule.onNodeWithText("Nombre completo").performTextInput("Luka Ortiz")
        composeTestRule.onNodeWithText("Correo corporativo").performTextInput("luka@ejecutivo.cl")
        composeTestRule.onNodeWithText("Contraseña").performTextInput("123456")

        composeTestRule.onNodeWithText("Registrarse").performClick()
        composeTestRule.waitForIdle()

        assertEquals("login", navController.currentBackStackEntry?.destination?.route)
    }
}