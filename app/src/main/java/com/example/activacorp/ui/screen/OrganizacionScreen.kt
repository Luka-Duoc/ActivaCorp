package com.example.activacorp.ui.screen

import android.widget.ListAdapter
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.example.activacorp.viewmodel.OrganizacionViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.activacorp.data.local.entity.OrganizacionEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrganizacionScreen(
    navController: NavController,
    viewModel: OrganizacionViewModel
) {
    val estado by viewModel.estado.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White
                ),
                title = {
                    Text("Lista de organizaciones")
                }
            )
        },
        floatingActionButton = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Redireccion a cuenta
                FloatingActionButton(
                    onClick = { navController.navigate("cuenta") }
                ) {
                    Icon(Icons.Default.Person, contentDescription = "Ir a la cuenta")
                }
                // Redireccion crear organizacion
                FloatingActionButton(
                    onClick = { navController.navigate("CreacionOrganizacion") }
                ) {
                    Icon(Icons.Default.CreateNewFolder, contentDescription = "Crear Organizacion")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding),
        ) {
            estado.organizaciones.forEach { item ->
                val nombreComuna = estado.comunas
                    .find { it.id == item.comuna }?.nombre ?: "Sin comuna"

                val nombreEvento = estado.listaEventos
                    .find { it.id == item.eventoId }?.nombre ?: "Sin evento asignado"

                cardOrganizacion(
                    organizacion = item,
                    nombreComuna = nombreComuna,
                    nombreEvento = nombreEvento,
                    onEditar = {
                        viewModel.prepararEdicion(item)
                        navController.navigate("CreacionOrganizacion")
                    }
                )
            }
        }
    }
}

@Composable
fun cardOrganizacion(
    organizacion: OrganizacionEntity,
    nombreComuna: String,
    nombreEvento: String,
    onEditar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .padding(bottom = 12.dp)
    ) {
        Card(
            modifier = Modifier
                .padding(all = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(all = 20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = organizacion.nombre,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = organizacion.tipo.name,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(
                    onClick = onEditar
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar Organizacion",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            HorizontalDivider()

            // Evento Asociado
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Evento Asociado",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = nombreEvento,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Rubro y sitio web
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Rubro",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = organizacion.rubro,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = "Sitio Web",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = organizacion.sitioWeb?.takeIf { it.isNotBlank() } ?: "Sin sitio web",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // Comuna y publico
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Comuna
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Comuna",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = nombreComuna.takeIf { it.isNotBlank() } ?: "Sin comuna",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // Publico
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = "Público objetivo",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = organizacion.publico.takeIf { it.isNotBlank() } ?: "Sin especificar",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // Historial
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Historial",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = organizacion.historial,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

