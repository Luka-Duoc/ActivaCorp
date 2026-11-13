package com.example.activacorp.ui.screen
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.activacorp.viewmodel.EventoViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


fun formatearFechas(milisegundos: Long): String {
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    return format.format(Date(milisegundos))
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventoScreen(
    viewModel: EventoViewModel,
    navController: NavController
) {
    val estado by viewModel.estado.collectAsState()

    //Controlan la vista del calendario
    var mostrarDialogoInicio by remember { mutableStateOf(false) }
    var mostrarDialogoTermino by remember { mutableStateOf(false) }

    // Almacenan la fecha
    val datePickerStateInicio = rememberDatePickerState()
    val datePickerStateTermino = rememberDatePickerState()

    var expandidoOrganizacion by remember { mutableStateOf(false) }

    val organizacionSeleccionadaNombre = estado.organizaciones
        .find { it.id == estado.organizacion }?.nombre ?: "Seleccionar organización *"

    val listaTipo = listOf(
        "Feria de Emprendimiento",
        "Actividad Deportiva",
        "Evento Cultural",
        "Operativo Social",
        "Fiscalización y Control"
    )
    var expandidoTipo by remember { mutableStateOf(false) }
    var expandidoComuna by remember { mutableStateOf(false) }

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 32.dp) // Contenedor
        ) {

        // Titulo
            item {
                Text(
                    text = "Gestion de Eventos",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Formulario de registro
            item {
                Card (
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(if (estado.eventoIdEnEdicion != null) "Actualizar Evento" else "Guardar Evento",
                            style = MaterialTheme.typography.labelMedium
                        )
                        // Divizor
                        HorizontalDivider()

                        // Datos
                        OutlinedTextField(
                            value = estado.nombre,
                            onValueChange = { viewModel.onNombreChange(it) },
                            label = { Text("Nombre del Evento *") },
                            isError = estado.errorNombre != null,
                            supportingText = {
                                if (estado.errorNombre != null) {
                                    Text(
                                        text = estado.errorNombre!!,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            ExposedDropdownMenuBox(
                                expanded = expandidoTipo,
                                onExpandedChange = { expandidoTipo = it },
                                modifier = Modifier.weight(1f)
                            ) {
                                OutlinedTextField(
                                    value = estado.tipo,
                                    onValueChange = {},
                                    label = { Text("Tipo ") },
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoTipo)
                                    },
                                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                                    modifier = Modifier
                                        .menuAnchor()
                                        .fillMaxWidth(),
                                    singleLine = true
                                )
                                ExposedDropdownMenu(
                                    expanded = expandidoTipo,
                                    onDismissRequest = { expandidoTipo = false }
                                ) {
                                    listaTipo.forEach { opcion ->
                                        DropdownMenuItem(
                                            text = { Text(opcion) },
                                            onClick = {
                                                viewModel.onTipoChange(opcion)
                                                expandidoTipo = false
                                            }
                                        )
                                    }
                                }
                            }
                            OutlinedTextField(
                                value = TextFieldValue(
                                    text = estado.horario,
                                    selection = TextRange(estado.horario.length)
                                ),
                                onValueChange = { viewModel.onHorarioChange( it ) },
                                label = { Text("Horario (Inicio-Fin)") },
                                isError = estado.errorHora != null,
                                supportingText = {
                                    if (estado.errorHora != null) {
                                        Text(
                                            text = estado.errorHora!!,
                                            color = MaterialTheme.colorScheme.error
                                        )
                                    }
                                },
                                placeholder = { Text("Ej: 17:50 - 22:00") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                        }

                        // Fechas

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Fecha Inicio
                            Box(modifier = Modifier.weight(1f)) {
                                OutlinedTextField(
                                    value = estado.fechaInicio,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = { Text("Inicio *") },
                                    isError =estado.errorFecha != null,
                                    supportingText = {
                                        if (estado.errorFecha != null) {
                                            Text(
                                                text = estado.errorFecha!!,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }
                                    },
                                    trailingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.DateRange,
                                            contentDescription = "Seleccionar fecha de inicio"
                                        )
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true
                                )
                                Box(
                                    modifier = Modifier
                                        .matchParentSize()
                                        .clickable { mostrarDialogoInicio = true }
                                )
                            }

                            // Fecha Término
                            Box(modifier = Modifier.weight(1f)) {
                                OutlinedTextField(
                                    value = estado.fechaTermino,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = { Text("Término *") },
                                    isError =estado.errorFecha != null,
                                    supportingText = {
                                        if (estado.errorFecha != null) {
                                            Text(
                                                text = estado.errorFecha!!,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }
                                    },
                                    trailingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.DateRange,
                                            contentDescription = "Seleccionar fecha de término"
                                        )
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true
                                )
                                Box(
                                    modifier = Modifier
                                        .matchParentSize()
                                        .clickable { mostrarDialogoTermino = true }
                                )
                            }
                        }

                        // Ubicacion
                        OutlinedTextField(
                            value = estado.direccion,
                            onValueChange = { viewModel.onDireccionChange(it) },
                            label = { Text("Direccion") },
                            isError = estado.errorDireccion != null,
                            supportingText = {
                                if (estado.errorDireccion != null) {
                                    Text(
                                        text = estado.errorDireccion!!,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        // Comuna
                        ExposedDropdownMenuBox(
                            expanded = expandidoComuna,
                            onExpandedChange = { expandidoComuna = it },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = estado.comunaSeleccionada,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Comuna *") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoComuna)
                                },
                                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                            )

                            ExposedDropdownMenu(
                                expanded = expandidoComuna,
                                onDismissRequest = { expandidoComuna = false }
                            ) {
                                estado.comunas.forEach { comunaItem ->
                                    DropdownMenuItem(
                                        text = { Text(comunaItem.nombre) },
                                        onClick = {
                                            viewModel.onComunaSeleccionada(comunaItem)
                                            expandidoComuna = false
                                        }
                                    )
                                }
                            }
                        }

                        // Asistencia y costos
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = estado.cantAsistentes,
                                onValueChange = { viewModel.onCantAsistentesChange(it) },
                                label = { Text("Asistentes") },
                                // Teclado numerico
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )

                            OutlinedTextField(
                                value = estado.costosReferenciales,
                                onValueChange = { viewModel.onCostosReferencialesChange(it) },
                                label = { Text("Costos Estimado ($)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                        }

                        // Requisitos y observaciones
                        OutlinedTextField(
                            value = estado.requisitos,
                            onValueChange = { viewModel.onRequisitosChange(it) },
                            label = { Text("Requisitos") },
                            isError = estado.errorRequisitos != null,
                            supportingText = {
                                if (estado.errorRequisitos != null) {
                                    Text(
                                        text = estado.errorRequisitos!!,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = estado.observaciones,
                            onValueChange = { viewModel.onObservacionesChange(it) },
                            label = { Text("Observaciones") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Organizaciones
                        ExposedDropdownMenuBox(
                            expanded = expandidoOrganizacion,
                            onExpandedChange = { expandidoOrganizacion = it },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = organizacionSeleccionadaNombre,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Organización *") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoOrganizacion)
                                },
                                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                            )

                            ExposedDropdownMenu(
                                expanded = expandidoOrganizacion,
                                onDismissRequest = { expandidoOrganizacion = false }
                            ) {
                                if (estado.organizaciones.isEmpty()) {
                                    DropdownMenuItem(
                                        text = { Text("No hay organizaciones registradas") },
                                        onClick = {
                                            viewModel.cargarOrganizacion()
                                            expandidoOrganizacion = false
                                        }
                                    )
                                } else {
                                    estado.organizaciones.forEach { orgItem ->
                                        DropdownMenuItem(
                                            text = { Text(orgItem.nombre) },
                                            onClick = {
                                                viewModel.onOrganizacionChange(orgItem.id)
                                                expandidoOrganizacion = false
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        // Resultado de validaciones: si error no es null, se muestra en rojo
                        estado.mensajeError?.let { msg ->
                            Text(
                                text = msg,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        // Boton de guardado
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Si  se esta editando, mostramos la opción de cancelar
                            if (estado.eventoIdEnEdicion != null) {
                                TextButton(
                                    onClick = { viewModel.cancelarEdicion() }
                                ) {
                                    Text("Cancelar")
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                            }

                            Button(
                                onClick = { viewModel.guardarEvento() }
                            ) {
                                Text(if (estado.eventoIdEnEdicion != null) "Actualizar Evento" else "Guardar Evento")
                            }
                            Button(
                                onClick = { navController.navigate("cuenta") },
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Text( "Volver a la cuenta?")
                            }
                        }
                    }
                }
            }
    }

    // Dialogo Fecha Inicio
    if (mostrarDialogoInicio) {
        DatePickerDialog(
            onDismissRequest = { mostrarDialogoInicio = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerStateInicio.selectedDateMillis?.let { milis ->
                            viewModel.onFechaInicioChange(formatearFechas(milis))
                        }
                        mostrarDialogoInicio = false
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoInicio = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerStateInicio)
        }
    }

    // Dialogo Fecha Término
    if (mostrarDialogoTermino) {
        DatePickerDialog(
            onDismissRequest = { mostrarDialogoTermino = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerStateTermino.selectedDateMillis?.let { milis ->
                            viewModel.onFechaTerminoChange(formatearFechas(milis))
                        }
                        mostrarDialogoTermino = false
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoTermino = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerStateTermino)
        }
    }

}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VistaEventoCard(
    navController: NavController,
    viewModel: EventoViewModel,
    rol: String
) {

    val estado by viewModel.estado.collectAsState()
    val marketing = remember(rol) {
        rol.contains("MARKETING", ignoreCase = true)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = androidx.compose.ui.graphics.Color.Black,
                    titleContentColor = androidx.compose.ui.graphics.Color.White,
                ),
                title = {
                    Text( if(estado.eventoIdEnEdicion != null) "Editar Evento" else "Nuevo Evento" )
                }
            )
        },
        floatingActionButton = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FloatingActionButton(
                    onClick = { navController.navigate("cuenta") }
                ) {
                    Icon(Icons.Default.Replay, contentDescription = "Volver atras")
                }

                // Redireccion crear Evento
                if (!marketing) {
                    FloatingActionButton(
                        onClick = { navController.navigate("eventos") }
                    ) {
                        Icon(Icons.Default.CreateNewFolder, contentDescription = "Crear Evento")
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            estado.eventos.forEach { item ->
                EventoCard(
                    evento = item,
                    comunas = estado.comunas,
                    onEditar = {
                        viewModel.editar(item)
                        navController.navigate("eventos")
                    },
                    onEliminar = {
                        viewModel.eliminarEvento(item)
                    },
                    rol = rol
                )
            }
        }
    }

}