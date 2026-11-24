package com.example.activacorp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.graphics.Color
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
import com.example.activacorp.viewmodel.ContactoViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.activacorp.model.enums.AreaContacto
import com.example.activacorp.model.enums.EstadoContacto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun creacionContactoScreen(
    navController: NavController,
    viewModel: ContactoViewModel
) {
    val estado by viewModel.estado.collectAsState()

    var expandidoOrganizacion by remember { mutableStateOf(false) }
    var expandidoArea by remember { mutableStateOf(false) }
    var expandidoEstado by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White
                ),
                title = {
                    Text( if(estado.contactoIdEdicion != null) "Editar Contacto" else "Registrar Contacto")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.nuevoContacto()
                            navController.popBackStack()
                        }
                    ) {
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
            OutlinedTextField(
                value = estado.nombre,
                onValueChange = { viewModel.onNombreChange(it) },
                label = { Text("Nombre completo") },
                isError = estado.errorNombre != null,
                supportingText = { estado.errorNombre?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Organización
            ExposedDropdownMenuBox(
                expanded =  expandidoOrganizacion,
                onExpandedChange = { expandidoOrganizacion = it }
            ) {
                OutlinedTextField(
                    value = estado.organizacionSeleccionada.ifBlank { "Selecciona una organización" },
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Organización") },
                    isError = estado.ErrorOrganizacion != null,
                    supportingText = { estado.ErrorOrganizacion?.let { Text(it) } },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoOrganizacion) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandidoOrganizacion,
                    onDismissRequest = { expandidoOrganizacion = false }
                ) {
                    if (estado.organizaciones.isEmpty()) {
                        DropdownMenuItem(
                            text = { Text("No hay organizaciones registradas") },
                            onClick = { expandidoOrganizacion = false }
                        )
                    } else {
                        estado.organizaciones.forEach { org ->
                            DropdownMenuItem(
                                text = { Text(org.nombre) },
                                onClick = {
                                    viewModel.onOrganizacionSeleccionada(org)
                                    expandidoOrganizacion = false
                                }
                            )
                        }
                    }
                }
            }

            // Cargo
            OutlinedTextField(
                value = estado.cargo,
                onValueChange = { viewModel.onCargoChange(it) },
                label = { Text("Cargo") },
                isError = estado.errorCargo != null,
                supportingText = { estado.errorCargo?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Area (DropMenu)
            ExposedDropdownMenuBox(
                expanded = expandidoArea,
                onExpandedChange = { expandidoArea = it }
            ) {
                OutlinedTextField(
                    value = estado.area.name,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Área") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoArea) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandidoArea,
                    onDismissRequest = { expandidoArea = false }
                ) {
                    AreaContacto.entries.forEach { area ->
                        DropdownMenuItem(
                            text = { Text(area.name) },
                            onClick = {
                                viewModel.onAreaChange(area)
                                expandidoArea = false
                            }
                        )
                    }
                }
            }
            // Correo
            OutlinedTextField(
                value = estado.correo,
                onValueChange = { viewModel.onCorreoChange(it) },
                label = { Text("Correo") },
                isError = estado.ErrorContacto != null,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Telefono (Mostrar error correo o telefono)
            OutlinedTextField(
                value = estado.telefono,
                onValueChange = { viewModel.onTelefonoChange(it) },
                label = { Text("Telefono") },
                isError = estado.ErrorContacto != null,
                supportingText = { estado.ErrorContacto?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Estado(Drop)
            ExposedDropdownMenuBox(
                expanded = expandidoEstado,
                onExpandedChange = { expandidoEstado = it }
            ) {
                OutlinedTextField(
                    value = estado.estado.name,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Estado") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded =  expandidoEstado) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandidoEstado,
                    onDismissRequest = { expandidoEstado = false }
                ) {
                    EstadoContacto.entries.forEach { est ->
                        DropdownMenuItem(
                            text = { Text(est.name) },
                            onClick = {
                                viewModel.onEstadoChange(est)
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
                minLines = 3
            )

            // Mensaje error
            estado.mensajeError?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Guardar / Actualizar
            Button(
                onClick = { viewModel.guardar(onExito = {
                    navController.popBackStack()
                }) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                Text(if (estado.contactoIdEdicion != null) "Actualizar contacto" else "Registrar contacto")
            }
        }
    }
}