package com.example.activacorp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.activacorp.viewmodel.UsuarioViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun CuentaScreen(
    viewModel: UsuarioViewModel
) {

    val estado by viewModel.estado.collectAsState()

    Column(Modifier.padding(16.dp)) {
        Text("Cuenta", style = MaterialTheme.typography.headlineMedium)
        Text("Nombre: ${estado.nombre}")
        Text("Correo: ${estado.correo}")
    }
}