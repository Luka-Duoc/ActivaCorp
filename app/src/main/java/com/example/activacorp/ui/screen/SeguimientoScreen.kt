package com.example.activacorp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.activacorp.viewmodel.SeguimientoViewModel

@Composable
fun SeguimientoScreen(navController: NavController, viewModel: SeguimientoViewModel) {
    val estado by viewModel.estado.collectAsState()

    Text("Seguimientos...")
    estado.seguimientos.forEach { seguimiento ->
        Box() {
            Column() {
                Text(seguimiento.id.toString())
                Text(seguimiento.observaciones.toString())
            }
        }
    }

}