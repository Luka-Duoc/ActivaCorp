package com.example.activacorp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
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
import com.example.activacorp.viewmodel.OportunidadViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.activacorp.model.EstadoGestion
import com.example.activacorp.model.PotencialComercial
import com.example.activacorp.model.TipoOportunidad

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun crearOportunidades(
    navController: NavController,
    viewModel: OportunidadViewModel
) {

    val estado by viewModel.estado.collectAsState()

    val usuarioSeleccionado = estado.usuariosDisponibles
        .find { it.id == estado.responsable }
        ?.nombre ?: "Seleccionar responsable"

    var expandidoTipo by remember { mutableStateOf(false) }
    var expandidoPotencial by remember { mutableStateOf(false) }
    var expandidoEstado by remember { mutableStateOf(false) }
    var expandidoEvento by remember { mutableStateOf(false) }
    val eventoSeleccionado =
        estado.eventosDisponibles.find { it.id == estado.eventoId }?.nombre ?: "Seleccionar evento"
    var expandidoResponsable by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White
                ),
                title = { Text("Nueva Oportunidad") },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.cancelarEdicion()
                        navController.popBackStack()
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
            estado.mensajeError?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Nombre
            OutlinedTextField(
                value = estado.nombre,
                onValueChange = { viewModel.onNombreChange(it) },
                label = { Text("Nombre de la oprtunidad") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Seleccion tipo de oprtunidad
            ExposedDropdownMenuBox(
                expanded = expandidoTipo,
                onExpandedChange = { expandidoTipo = it }
            ) {
                OutlinedTextField(
                    value = estado.tipo.name,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo de oportunidad") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoTipo) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandidoTipo,
                    onDismissRequest = { expandidoTipo = false }
                ) {
                    TipoOportunidad.entries.forEach { tipoItem ->
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

            // Descripcion
            OutlinedTextField(
                value = estado.descripcion,
                onValueChange = { viewModel.onDescripcionChange(it) },
                label = { Text("Descripcion") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            // Fuente de deteccion
            OutlinedTextField(
                value = estado.fuente,
                onValueChange = { viewModel.onFuenteChange(it) },
                label = { Text("Fuente de deteccion") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Fecha deteccion
            OutlinedTextField(
                value = estado.fechaDeteccion,
                onValueChange = { viewModel.onFechaDeteccionChange(it) },
                label = { Text("Fecha de detección (ej: 2026-10-18)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // responsable
            ExposedDropdownMenuBox(
                expanded = expandidoResponsable,
                onExpandedChange = { expandidoResponsable = it }
            ) {
                OutlinedTextField(
                    value = usuarioSeleccionado,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Responsable") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoResponsable) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandidoResponsable,
                    onDismissRequest = { expandidoResponsable = false }
                ) {
                    if (estado.usuariosDisponibles.isEmpty()) {
                        DropdownMenuItem(
                            text = { Text("No hay usuarios registrados") },
                            onClick = { expandidoResponsable = false }
                        )
                    } else {
                        estado.usuariosDisponibles.forEach { usuario ->
                            DropdownMenuItem(
                                text = { Text(usuario.nombre) },
                                onClick = {
                                    viewModel.onResponsableChange(usuario.id)
                                    expandidoResponsable = false
                                }
                            )
                        }
                    }
                }
            }

            // Prioridad
            OutlinedTextField(
                value = estado.prioridad,
                onValueChange = { viewModel.onPrioridadChange(it) },
                label = { Text("Prioridad (Número)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Selector Potencial Comercial
            ExposedDropdownMenuBox(
                expanded = expandidoPotencial,
                onExpandedChange = { expandidoPotencial = it }
            ) {
                OutlinedTextField(
                    value = estado.potencial.name,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Potencial Comercial") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoPotencial) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandidoPotencial,
                    onDismissRequest = { expandidoPotencial = false }
                ) {
                    PotencialComercial.entries.forEach { potItem ->
                        DropdownMenuItem(
                            text = { Text(potItem.name) },
                            onClick = {
                                viewModel.onPotencialChange(potItem)
                                expandidoPotencial = false
                            }
                        )
                    }
                }
            }

            // Selector Estado de Gestión
            ExposedDropdownMenuBox(
                expanded = expandidoEstado,
                onExpandedChange = { expandidoEstado = it }
            ) {
                OutlinedTextField(
                    value = estado.estado.name,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Estado de Gestión") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoEstado) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expandidoEstado,
                    onDismissRequest = { expandidoEstado = false }
                ) {
                    EstadoGestion.entries.forEach { estItem ->
                        DropdownMenuItem(
                            text = { Text(estItem.name) },
                            onClick = {
                                viewModel.onEstadoGestionChange(estItem)
                                expandidoEstado = false
                            }
                        )
                    }
                }
            }

            // Observaciones
            OutlinedTextField(
                value = estado.observaciones,
                onValueChange = { viewModel.onObservacionesChange(it) },
                label = { Text("Observaciones") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2
            )

            // Campo de eventos
            ExposedDropdownMenuBox(
                expanded = expandidoEvento,
                onExpandedChange = { expandidoEvento = it }
            ) {
                OutlinedTextField(
                    value = eventoSeleccionado,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Evento Asociado") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoEvento) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandidoEvento,
                    onDismissRequest = { expandidoEvento = false }
                ) {
                    if (estado.eventosDisponibles.isEmpty()) {
                        DropdownMenuItem(
                            text = { Text("No hay eventos registrados") },
                            onClick = { expandidoEvento = false }
                        )
                    } else {
                        estado.eventosDisponibles.forEach { evento ->
                            DropdownMenuItem(
                                text = { Text(evento.nombre) },
                                onClick = {
                                    viewModel.onEventoIdChange(evento.id) // Guarda el ID en el ViewModel
                                    expandidoEvento = false              // Cierra el menú
                                }
                            )
                        }
                    }
                }
            }

            // Botón Guardar
            Button(
                onClick = {
                    viewModel.guardarOportunidad(
                        onExito = {
                            navController.popBackStack()
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                Text(if (estado.oportunidadIdEnEdicion != null) "Actualizar Oportunidad" else "Guardar Oportunidad")
            }
        }

    }
}
