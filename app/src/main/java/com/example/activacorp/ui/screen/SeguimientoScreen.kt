package com.example.activacorp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.activacorp.viewmodel.SeguimientoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeguimientoScreen(navController: NavController, viewModel: SeguimientoViewModel) {
    val estado by viewModel.estado.collectAsState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text("Seguimientos")
            })
        }

    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "Oportunidades confirmadas",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelLarge
                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(items = estado.gestiones, key = { it.nombre + it.fecha }) { seguimiento ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 12.dp)
                            .padding(bottom = 12.dp)
                    ) {
                        Card(modifier = Modifier.padding(all = 16.dp)) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        modifier = Modifier.padding(4.dp),
                                        text = "Oportunidad Asociada",
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                    Text(
                                        modifier = Modifier.padding(4.dp),
                                        text = seguimiento.nombre,
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                                HorizontalDivider(
                                    Modifier,
                                    DividerDefaults.Thickness,
                                    MaterialTheme.colorScheme.onSurface
                                )
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        modifier = Modifier.padding(4.dp),
                                        text = "Fecha del seguimiento",
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                    Text(
                                        modifier = Modifier.padding(4.dp),
                                        text = seguimiento.fecha,
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                                HorizontalDivider(
                                    Modifier,
                                    DividerDefaults.Thickness,
                                    MaterialTheme.colorScheme.onSurface
                                )
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        modifier = Modifier.padding(4.dp),
                                        text = "Hora acordada",
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                    Text(
                                        modifier = Modifier.padding(4.dp),
                                        text = seguimiento.hora,
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                                HorizontalDivider(
                                    Modifier,
                                    DividerDefaults.Thickness,
                                    MaterialTheme.colorScheme.onSurface
                                )
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        modifier = Modifier.padding(4.dp),
                                        text = "Tipo de Accion",
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                    Text(
                                        modifier = Modifier.padding(4.dp),
                                        text = seguimiento.tipoDeAccion.nombreMuestra,
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

    }

}