package com.example.activacorp.navigation

import android.Manifest
import androidx.compose.runtime.Composable
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

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val UsuarioViewModel: UsuarioViewModel = viewModel()
    val QrViewModel: QrViewModel = viewModel()
    val eventosViewModel: EventoViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(navController)
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
            QrScannerScreen(QrViewModel, true) { }
        }
        composable("eventos") {
            EventoScreen(eventosViewModel)
        }

    }
}