package com.example.activacorp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.activacorp.ui.screen.CuentaScreen
import com.example.activacorp.ui.screen.IniciarScreen
import com.example.activacorp.ui.screen.RegistroScreen
import com.example.activacorp.viewmodel.UsuarioViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val UsuarioViewModel: UsuarioViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "registrar"
    ) {
        composable("registrar") {
            RegistroScreen(navController,UsuarioViewModel)
        }
        composable("login") {
            IniciarScreen(navController, UsuarioViewModel)
        }
        composable("cuenta") {
            CuentaScreen(UsuarioViewModel, navController)
        }

    }
}