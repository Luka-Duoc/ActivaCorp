package com.example.activacorp.ui.screen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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

@Composable
fun EventoScreen(
    viewModel: EventoViewModel
) {
    val estado by viewModel.estado.collectAsState()

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
                            OutlinedTextField(
                                value = estado.tipo,
                                onValueChange = { viewModel.onTipoChange(it) },
                                label = { Text("Tipo") },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = estado.horario,
                                onValueChange = { viewModel.onHorarioChange(it) },
                                label = { Text("Horario") },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                        }

                        // Fechas

                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = estado.fechaInicio,
                                onValueChange = { viewModel.onFechaInicioChange(it) },
                                label = { Text("Inicio (DD/MM/YYYY) *") },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = estado.fechaTermino,
                                onValueChange = { viewModel.onFechaTerminoChange(it) },
                                label = { Text("Término *") },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                        }

                        // Ubicacion
                        OutlinedTextField(
                            value = estado.direccion,
                            onValueChange = { viewModel.onDireccionChange(it) },
                            label = { Text("Direccion") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

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
                        Button(
                            onClick = { viewModel.guardarEvento() },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Guardar Evento")
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
            // Iterar sobre la lista
            items(
                items = estado.eventos,
                key = { it.id } // Identificador para cada evento
            ) { evento ->
                ItemEventoCard(evento)
            }
        }
    }
}

// Recibe la entidad evento y creacion de tarjeta base para cada evento
@Composable
private fun ItemEventoCard(evento: EventoEntity) {

    Card(
        modifier = Modifier.fillMaxWidth(),
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
