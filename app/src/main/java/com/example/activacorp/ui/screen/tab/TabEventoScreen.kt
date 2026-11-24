package com.example.activacorp.ui.screen.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.activacorp.viewmodel.EventoPostViewModel
import com.example.activacorp.viewmodel.EventoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun tabEventos(
    navController: NavController,
    eventoViewModel: EventoViewModel,
    rol: String,
    eventoPostViewModel: EventoPostViewModel = viewModel()
) {
    val marketing = remember(rol) {
        rol.contains("MARKETING", ignoreCase = true)
    }

    val estadoEvento by eventoViewModel.estado.collectAsState()
    val listaEventos = estadoEvento.eventos
    val listaEventosPost by eventoPostViewModel.postList.collectAsState()

    var expandido by remember { mutableStateOf(false) }
    var eventoSeleccionado by remember(listaEventos, listaEventosPost) {
        mutableStateOf(
            listaEventos.firstOrNull()?.nombre
                ?: listaEventosPost.firstOrNull()?.nombre
                ?: "Sin eventos disponibles"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                        text = "Eventos",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

                Text(
                    text = "Lista de eventos registrados",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.LightGray,
                    textAlign = TextAlign.Center
                )
                ExposedDropdownMenuBox(
                    expanded = expandido,
                    onExpandedChange = { expandido = !expandido },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = eventoSeleccionado,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            TrailingIcon(
                                expanded = expandido
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface
                        ),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = expandido,
                        onDismissRequest = { expandido = false },
                        modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                    ) {
                        if (listaEventos.isEmpty() && listaEventosPost.isEmpty()) {
                            DropdownMenuItem(text = {
                                Text(
                                    text = "No hay eventos registrados aún",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }, onClick = { expandido = false })
                        } else {
                            listaEventos.forEach { evento ->
                                DropdownMenuItem(text = {
                                    Text(
                                        text = "${evento.nombre} (${evento.fechaInicio})",
                                        color = MaterialTheme.colorScheme.onSurface,
                                    )
                                }, onClick = {
                                    eventoSeleccionado = evento.nombre
                                    expandido = false
                                })
                            }
                            listaEventosPost.forEach { evento ->
                                DropdownMenuItem(text = {
                                    Text(
                                        text = "${evento.nombre} (${evento.fechaInicio})",
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }, onClick = {
                                    eventoSeleccionado = evento.nombre
                                    expandido = false
                                })
                            }
                        }
                    }
                }
            }
            if (!marketing) {
                Button(
                    onClick = {
                        navController.navigate("eventos")
                    }, modifier = Modifier
                        .fillMaxWidth(0.9F)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text("Crear Eventos")
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Button(
                onClick = {
                    navController.navigate("vistaEventos")
                }, modifier = Modifier
                    .fillMaxWidth(0.9F)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text("Ver todos los Eventos")
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun vistaEventos() {
    var text by remember { mutableStateOf("")}

    Column(
        modifier = Modifier
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.onPrimaryContainer),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = "Buscar eventos",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge
        )
        OutlinedTextField(modifier = Modifier.padding(16.dp).fillMaxWidth(), value=text, onValueChange = {text=it},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            },
            shape = CircleShape,
            placeholder ={Text("Nombre del evento")})
    }
}