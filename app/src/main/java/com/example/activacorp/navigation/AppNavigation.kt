package com.example.activacorp.navigation

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.activacorp.ui.screen.CuentaScreen
import com.example.activacorp.ui.screen.EventoScreen
import com.example.activacorp.ui.screen.HomeScreen
import com.example.activacorp.ui.screen.IniciarScreen
import com.example.activacorp.ui.screen.QrScannerScreen
import com.example.activacorp.ui.screen.RegistroScreen
import com.example.activacorp.viewmodel.EventoViewModel
import com.example.activacorp.viewmodel.QrViewModel
import com.example.activacorp.viewmodel.UsuarioViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.activacorp.data.local.AppDataContainer
import com.example.activacorp.data.local.SessionDataStore
import com.example.activacorp.ui.screen.CreacionOrganizacionScreen
import com.example.activacorp.ui.screen.OportunidadScreen
import com.example.activacorp.ui.screen.OrganizacionScreen
import com.example.activacorp.ui.screen.VistaEventoCard
import com.example.activacorp.viewmodel.OportunidadViewModel
import com.example.activacorp.ui.screen.crearOportunidades
import com.example.activacorp.viewmodel.OrganizacionViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    // Instanciar las dependencias
    val container = remember { AppDataContainer(context.applicationContext) }
    val sessionDataStore = remember { SessionDataStore(context.applicationContext) }

    // Instanciar los viewModel inicializando los repository
    val usuarioViewModel: UsuarioViewModel = viewModel {
        UsuarioViewModel(
            usuarioRepository = container.usuarioRepository,
            sessionDataStore = sessionDataStore
        )
    }
    val QrViewModel: QrViewModel = viewModel()
    val eventosViewModel: EventoViewModel = viewModel {
        EventoViewModel(
            eventoRepository = container.eventoRepository,
            comunaRepository = container.comunaRepository,
            organizacionRepository = container.organizacionRepository
        )
    }
    val oportunidadViewModel: OportunidadViewModel = viewModel {
        OportunidadViewModel(
            oportunidadRepository = container.oportunidadRepository,
            usuarioRepository = container.usuarioRepository,
            eventoRepository = container.eventoRepository
        )
    }
    val organizacionViewModel: OrganizacionViewModel = viewModel {
        OrganizacionViewModel(
            organizacionRepository = container.organizacionRepository,
            comunaRepository = container.comunaRepository
        )
    }

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcherPermisoCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { concedido ->
        hasCameraPermission = concedido
    }
    val estadoUsuario by usuarioViewModel.estado.collectAsState()
    val sesionVerificada by usuarioViewModel.sesionVerificada.collectAsState()

    if (!sesionVerificada) return

    val rutaInicial = if (estadoUsuario.id != 0L && estadoUsuario.rol.isNotBlank()) {
        "cuenta"
    } else {
        "home"
    }

    NavHost(
        navController = navController,
        startDestination = rutaInicial
    ) {
        composable("home") {
            HomeScreen(navController)
        }

        composable("oportunidades") {
            OportunidadScreen(navController, oportunidadViewModel, rol = estadoUsuario.rol)
        }
        composable("CreacionOportunidad") {
            crearOportunidades(
                navController = navController,
                viewModel = oportunidadViewModel,
                rol = estadoUsuario.rol
            )
        }

        composable("registrar") {
            RegistroScreen(navController,usuarioViewModel)
        }
        composable("login") {
            IniciarScreen(navController, usuarioViewModel)
        }
        composable("cuenta") {
            CuentaScreen(
                usuarioViewModel,
                navController,
                eventoViewModel = eventosViewModel,
                oportunidadViewModel = oportunidadViewModel,
                organizacionViewModel = organizacionViewModel
            )
        }
        composable("qrCamara") {
            QrScannerScreen(
                viewModel = QrViewModel,
                eventoViewModel = eventosViewModel,
                hasCameraPermission = hasCameraPermission,
                onRequestPermission = {
                    launcherPermisoCamara.launch(Manifest.permission.CAMERA)
                },
                onEventoRegistrado = {
                    navController.popBackStack()
                }
            )
        }
        composable("eventos") {
            EventoScreen(eventosViewModel, navController)
        }
        composable("vistaEventos") {
            VistaEventoCard(navController = navController, viewModel = eventosViewModel, rol = estadoUsuario.rol)
        }

        composable("CreacionOrganizacion") {
            CreacionOrganizacionScreen(
                navController,
                organizacionViewModel
            )
        }
        composable("organizacion") {
            OrganizacionScreen(navController, organizacionViewModel)
        }

    }
}