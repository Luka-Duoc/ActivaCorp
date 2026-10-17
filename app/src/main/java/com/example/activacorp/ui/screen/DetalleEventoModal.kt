package com.example.activacorp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.activacorp.data.local.entity.ComunaEntity
import com.example.activacorp.data.local.entity.EventoEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleEvento(
    evento: EventoEntity,
    onDismiss: () -> Unit,
    comunas: List<ComunaEntity>,
    onEliminar: (EventoEntity) -> Unit
) {
    val nombreComuna = comunas.firstOrNull { it.id == evento.comuna }?.nombre ?: "Comuna no especificada"


    // Ventana inferior para mostrar los detalles del evento
    ModalBottomSheet(
        onDismiss
    ) {
        Column(
           modifier = Modifier
               .fillMaxWidth()
               .padding(horizontal = 20.dp, vertical = 12.dp)
               .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Titulo y Tipo de evento
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = evento.nombre,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    if (evento.tipo.isNotBlank()) {
                        Text(
                            text = evento.tipo,
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Cerrar")
                }
            }
            HorizontalDivider()

            // Fechas
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.CalendarMonth, contentDescription = null, tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${evento.fechaInicio} al ${evento.fechaTermino} de ${evento.horario.ifBlank { "Sin horario" }}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Cortos
            if (evento.direccion.isNotBlank()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Ubicacion: ${evento.direccion} | Comuna: ${nombreComuna}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
                // Asistentes y cortos
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.People, contentDescription = null, tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Aforo: ${evento.cantAsistentes} | Costo Ref: ${evento.costosReferenciales}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // Evento
                if (evento.requisitos.isNotBlank()) {
                    Text(
                        text = "Requisitos:",
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = evento.requisitos,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // Observaciones
                if (evento.observaciones.isNotBlank()) {
                    Text(
                        text = "Observaciones:",
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = evento.observaciones,
                        style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = {onEliminar(evento)},
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Eliminar Evento")
            }
        }
    }
}

