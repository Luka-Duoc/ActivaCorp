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
import androidx.compose.material.icons.filled.Replay
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.activacorp.viewmodel.SeguimientoViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.activacorp.model.TipoDeAccion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun formSeguimiento(
    navController: NavController,
    viewModel: SeguimientoViewModel = viewModel(),
    onListo:() -> Unit = {}
) {

    val estado by viewModel.estado.collectAsState()

    val usuariosDisponibles = remember(estado.usuariosDisponibles) {
        estado.usuariosDisponibles.filter { usuario ->
            usuario.rol.uppercase() in listOf("EJECUTIVO", "SUPERVISOR")
        }
    }

    val usuarioSeleccionado = usuariosDisponibles
        .find { it.id == estado.usuarioResponsable }
        ?.nombre ?: "Seleccionar responsable"

    val resultadoSeleccionado = remember(estado.resultado, estado.resultadoDisponible) {
        estado.resultadoDisponible
            ?.find { it.id == estado.resultado }
            ?.let { "Resultado #${it.id} - ${it.estadoFinal}" }
            ?: "Seleccionar resultado"
    }

    val oportunidadSeleccionada = remember(estado.oportunidadRelacionada, estado.oportunidadesDisponibles) {
        estado.oportunidadesDisponibles
            .find { it.id == estado.oportunidadRelacionada }
            ?.nombre ?: "Seleccionar oportunidad"
    }

    // DropMenu estados
    var expandidoTipoAccion by remember { mutableStateOf(false) }
    var expandidoAccionProxima by remember { mutableStateOf(false) }
    var usuarioSeleccionar by remember { mutableStateOf(false) }
    var resultadoExpandido by remember { mutableStateOf(false) }
    var oportunidadExpandida by remember { mutableStateOf(false) }



    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White
                ),
                title = {
                    Text(
                        if (estado.idEdicion != null) "Editar Seguimiento" else "Nuevo Seguimiento"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.limpiarFormulario()
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

            // UsuarioDisponible
            ExposedDropdownMenuBox(
                expanded = usuarioSeleccionar,
                onExpandedChange = { usuarioSeleccionar = it }
            ) {
                OutlinedTextField(
                    value = usuarioSeleccionado,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Usuario Responsable") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = usuarioSeleccionar) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = usuarioSeleccionar,
                    onDismissRequest = { usuarioSeleccionar = false }
                ) {
                    if (usuariosDisponibles.isEmpty()) {
                        Text("Sin responsables disponibles")
                    } else {
                        usuariosDisponibles.forEach { usuario ->
                            DropdownMenuItem(
                                text = { Text(usuario.nombre) },
                                onClick = {
                                    viewModel.onUsuarioResponsableChange(usuario.id)
                                    usuarioSeleccionar = false
                                }
                            )
                        }
                    }
                }
            }

            // Tipo de accion
            ExposedDropdownMenuBox(
                expanded = expandidoTipoAccion,
                onExpandedChange = { expandidoTipoAccion = it }
            ) {
                OutlinedTextField(
                    value = estado.tipoDeAccion.name,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo de acción") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoTipoAccion) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandidoTipoAccion,
                    onDismissRequest = { expandidoTipoAccion = false }
                ) {
                    TipoDeAccion.entries.forEach { accion ->
                        DropdownMenuItem(
                            text = { Text(accion.name) },
                            onClick = {
                                viewModel.onTipoAccionChange(accion)
                                expandidoTipoAccion = false
                            }
                        )
                    }
                }
            }

            // Fecha
            OutlinedTextField(
                value = estado.fecha,
                onValueChange = { viewModel.onFechaChange(it) },
                label = { Text("Fecha (10/12/2026)") },
                isError = estado.errorFecha != null,
                supportingText = {
                    if (estado.errorFecha != null) {
                        Text(
                            text = estado.errorFecha!!,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Hora
            OutlinedTextField(
                value = estado.hora,
                onValueChange = { viewModel.onHora(it) },
                label = { Text("Hora (10:30)") },
                isError = estado.errorHora != null,
                supportingText = {
                    if (estado.errorHora != null) {
                        Text(
                            text = estado.errorHora!!,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Resultado
            ExposedDropdownMenuBox(
                expanded = resultadoExpandido,
                onExpandedChange = { resultadoExpandido = it }
            ) {
                OutlinedTextField(
                    value = resultadoSeleccionado,
                    onValueChange = {},
                    readOnly = true,
                    singleLine = true,
                    label = { Text("Resultado asociado") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = resultadoExpandido) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = resultadoExpandido,
                    onDismissRequest = { resultadoExpandido = false }
                ) {
                    if (estado.resultadoDisponible.isEmpty()) {
                        Text("Sin resultados disponibles")
                    } else {
                        estado.resultadoDisponible?.forEach { res ->
                            DropdownMenuItem(
                                text = {
                                    Text("Resultado #${res.id} - ${res.estadoFinal.name} (${res.fechaRealizacion})")
                                },
                                onClick = {
                                    viewModel.onResultadoChange(res.id)
                                    resultadoExpandido = false
                                }
                            )
                        }
                    }
                }
            }

            // Proxima accion
            ExposedDropdownMenuBox(
                expanded = expandidoAccionProxima,
                onExpandedChange = { expandidoAccionProxima = it }
            ) {
                OutlinedTextField(
                    value = estado.proximaAccion?.name ?: "Seleccionar proxima acción",
                    onValueChange = {},
                    readOnly = true,
                    singleLine = true,
                    label = { Text("Proxima acción") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoAccionProxima)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandidoAccionProxima,
                    onDismissRequest = { expandidoAccionProxima = false }
                ) {
                    TipoDeAccion.entries.forEach { accion ->
                        DropdownMenuItem(
                            text = { Text(accion.name) },
                            onClick = {
                                viewModel.onProximaAccionChange(accion)
                                expandidoAccionProxima = false
                            }
                        )
                    }
                }
            }

            //Oportunidad
            ExposedDropdownMenuBox(
                expanded = oportunidadExpandida,
                onExpandedChange = { oportunidadExpandida = it }
            ) {
                OutlinedTextField(
                    value = oportunidadSeleccionada,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Oportunidad relacionada") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = oportunidadExpandida) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = oportunidadExpandida,
                    onDismissRequest = { oportunidadExpandida = false }
                ) {
                    estado.oportunidadesDisponibles.forEach { op ->
                        DropdownMenuItem(
                            text = { Text(op.nombre) },
                            onClick = {
                                viewModel.onOportunidadChange(op.id)
                                oportunidadExpandida = false
                            }
                        )
                    }
                }
            }

            // Fecha proximo seguimiento
            OutlinedTextField(
                value = estado.fechaProxSeguimiento ?: "",
                onValueChange = { viewModel.onFechaSeguimientoChange(it) },
                label = { Text("Fecha proximo seguimiento (10/12/2026)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Observaciones
            OutlinedTextField(
                value = estado.observaciones ?: "",
                onValueChange = { viewModel.onObservacionChange(it) },
                label = { Text("Observacion") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Button(
                onClick = {
                    viewModel.guardarSeguimiento(
                        onListo = {
                            onListo()
                            navController.popBackStack()
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                Text(if (estado.idEdicion != null) "Actualizar Seguimiento" else "Guardar Seguimiento")
            }

            estado.mensajeError?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )

            }
        }
    }

}