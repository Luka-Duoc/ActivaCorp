package com.example.activacorp.navigation

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
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
import com.example.activacorp.ui.screen.OportunidadScreen
import com.example.activacorp.viewmodel.OportunidadViewModel
import com.example.activacorp.ui.screen.crearOportunidades

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val UsuarioViewModel: UsuarioViewModel = viewModel()
    val QrViewModel: QrViewModel = viewModel()
    val eventosViewModel: EventoViewModel = viewModel()
    val OportunidadViewModel: OportunidadViewModel = viewModel()

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

    NavHost(
        navController = navController,
        startDestination = "oportunidades"
    ) {
        composable("home") {
            HomeScreen(navController)
        }

        composable("oportunidades") {
            OportunidadScreen(navController, OportunidadViewModel)
        }
        composable("CreacionOportunidad") {
            crearOportunidades(navController, OportunidadViewModel)
        }

        composable("registrar") {
            RegistroScreen(navController,UsuarioViewModel)
        }
        composable("login") {
            IniciarScreen(navController, UsuarioViewModel)
        }
        composable("cuenta") {
            CuentaScreen(UsuarioViewModel, navController)
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

    }
}