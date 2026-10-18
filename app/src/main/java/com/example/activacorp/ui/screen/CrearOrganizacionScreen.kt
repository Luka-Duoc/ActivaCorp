package com.example.activacorp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.example.activacorp.viewmodel.OrganizacionViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.activacorp.model.TipoOrganizacion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreacionOrganizacionScreen(
    navController: NavController,
    viewModel: OrganizacionViewModel
) {
    val estado by viewModel.estado.collectAsState()

    // Para evaluar el estado de la lista desplegable
    var expandidoTipo by remember { mutableStateOf(false) }
    var expandidoComuna by remember { mutableStateOf(false) }

    var expandidoEvento by remember { mutableStateOf(false) }

    val comunaSeleccionada = estado.comunas
        .find { it.id == estado.comunaId }?.nombre ?: "Seleccionar comuna"

    val eventoSeleccionado = estado.listaEventos
        .find { it.id == estado.eventoId }?.nombre ?: "Seleccionar evento"

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White
                ),
                title = {
                    Text(if (estado.idEnEdicion != null) "Editar organizacion" else "Crear organizacion")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.resetFormulario()
                            navController.navigate("cuenta")
                        }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
           modifier = Modifier
               .fillMaxSize()
               .padding(innerPadding)
               .verticalScroll(rememberScrollState())
               .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Nombre Organizacion
            OutlinedTextField(
                value = estado.nombre,
                onValueChange = { viewModel.onNombreChange(it) },
                label = { Text("Nombre de la organizacion") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Tipo Organizacion
            ExposedDropdownMenuBox(
                expanded = expandidoTipo,
                onExpandedChange = { expandidoTipo = it }
            ) {
                OutlinedTextField(
                    value = estado.tipo.name,
                    onValueChange = {},
                    label = { Text("Tipo de organizacion") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoTipo) },
                    modifier = Modifier.fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandidoTipo,
                    onDismissRequest = { expandidoTipo = false }
                ) {
                    TipoOrganizacion.entries.forEach { tipoItem ->
                        DropdownMenuItem(
                            text = { Text(tipoItem.name) },
                            onClick = {
                                viewModel.onTipoChange(tipoItem)
                                expandidoTipo = false
                            }
                        )
                    }
                }

            }

            // Rubro
            OutlinedTextField(
                value = estado.rubro,
                onValueChange = { viewModel.onRubroChange(it) },
                label = { Text("Rubro") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Selector comuna

            ExposedDropdownMenuBox(
                expanded = expandidoComuna,
                onExpandedChange = { expandidoComuna = it }
            ) {
                OutlinedTextField(
                    value = comunaSeleccionada,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Comuna") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoComuna) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandidoComuna,
                    onDismissRequest = { expandidoComuna = false }
                ) {
                    if (estado.comunas.isEmpty()) {
                        DropdownMenuItem(
                            text = { Text("No hay comunas registradas") },
                            onClick = { expandidoComuna = false }
                        )
                    } else {
                        estado.comunas.forEach { comunaItem ->
                            DropdownMenuItem(
                                text = { Text(comunaItem.nombre) },
                                onClick = {
                                    viewModel.onComunaChange(comunaItem.id)
                                    expandidoComuna = false
                                }
                            )
                        }
                    }
                }
            }

            // Sitio Web
            OutlinedTextField(
                value = estado.sitioWeb ?: "",
                onValueChange = { viewModel.onSitioWebChange(it) },
                label = { Text("Sitio Web") },
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true
            )

            // Publico objetivo
            OutlinedTextField(
                value = estado.publico,
                onValueChange = { viewModel.onPublicoChange(it) },
                label = { Text("Publico objetivo") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Historial
            OutlinedTextField(
                value = estado.historial,
                onValueChange = { viewModel.onHistorialChange(it) },
                label = { Text("Historial") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )


            // Mensaje
            estado.mensajeError?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Botón Guardar / Actualizar
            Button(
                onClick = {
                    viewModel.guardarOrganizacion(
                        onExito = {
                            navController.popBackStack()
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                Text(if (estado.idEnEdicion != null) "Actualizar Organización" else "Guardar Organización")
            }

        }
    }
}