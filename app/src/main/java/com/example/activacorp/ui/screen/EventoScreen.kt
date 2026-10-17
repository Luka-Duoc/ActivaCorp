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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.activacorp.data.local.entity.EventoEntity
import com.example.activacorp.viewmodel.EventoViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    viewModel: EventoViewModel
) {
    val estado by viewModel.estado.collectAsState()

    //Controlan la vista del calendario
    var mostrarDialogoInicio by remember { mutableStateOf(false) }
    var mostrarDialogoTermino by remember { mutableStateOf(false) }

    // Almacenan la fecha
    val datePickerStateInicio = rememberDatePickerState()
    val datePickerStateTermino = rememberDatePickerState()
    var eventoSeleccionadoParaDetalle by remember { mutableStateOf<EventoEntity?>(null) }

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
                        Text(
                            text = "Registrar nuevo Evento",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )

                        // Divizor
                        HorizontalDivider()

                        // Datos
                        OutlinedTextField(
                            value = estado.nombre,
                            onValueChange = { viewModel.onNombreChange(it) },
                            label = { Text("Nombre del Evento *") },
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
                                value = estado.horario,
                                onValueChange = { viewModel.onHorarioChange(it) },
                                label = { Text("Horario") },
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
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = estado.observaciones,
                            onValueChange = { viewModel.onObservacionesChange(it) },
                            label = { Text("Observaciones") },
                            modifier = Modifier.fillMaxWidth()
                        )

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
                        }
                    }
                }
            }

        // Titulo seccion de listado
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Eventos registrados (${estado.eventos.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        // Renderizado condicional

        if (estado.eventos.isEmpty()) {
            item {
                Text(
                    text = "No hay eventos registrados.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            items(
                items = estado.eventos,
                key = { it.id }
            ) { evento ->
                ItemEventoCard(
                    evento = evento,
                    onClick = { eventoSeleccionadoParaDetalle = it }
                )
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

    eventoSeleccionadoParaDetalle?.let { evento ->
        DetalleEvento(
            evento = evento,
            comunas = estado.comunas,
            onDismiss = { eventoSeleccionadoParaDetalle = null },
            onEliminar = { e ->
                viewModel.eliminarEvento(e) {
                    eventoSeleccionadoParaDetalle = null
                }
            },
            onEditar = { ev ->
                viewModel.editar(ev)
                eventoSeleccionadoParaDetalle = null // Cierra el modal para que el usuario vea el formulario para editar
            }
        )
    }

}


@Composable
private fun ItemEventoCard(evento: EventoEntity, onClick: (EventoEntity) -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(evento) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Columna de titulo de evento
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Event,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = evento.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            // Fechas
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${evento.fechaInicio} al ${evento.fechaTermino}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Si hay direccion se muestra un icono con la direccion

            if (evento.direccion.isNotBlank()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = evento.direccion,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (evento.observaciones.isNotBlank()) {
                Text(
                    text = evento.observaciones,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

}