package com.example.activacorp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.activacorp.viewmodel.UsuarioViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.activacorp.viewmodel.EventoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CuentaScreen(
    viewModel: UsuarioViewModel,
    navController: NavController,
    eventoViewModel: EventoViewModel = viewModel()
) {
    val estado by viewModel.estado.collectAsState()
    val estadoEvento by eventoViewModel.estado.collectAsState()
    val darkPurple = Color(0xFF301934)
    val listaEventos = estadoEvento.eventos

    var expandido by remember { mutableStateOf(false) }
    var eventoSeleccionado by remember(listaEventos) {
        mutableStateOf(
            listaEventos.firstOrNull()?.nombre ?: "Sin eventos disponibles"
        )
    }
    val pagerState = rememberPagerState(pageCount = { 2 })
    Scaffold(bottomBar = {
        val coroutineScope = rememberCoroutineScope()
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = darkPurple, contentColor = Color.White,
            divider = {},
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    height = 2.dp,
                    color = MaterialTheme.colorScheme.scrim
                )
            }
        ) {
            Tab(
                selected = pagerState.currentPage == 0,
                text = { Text("Cuenta") },
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                }, icon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Cuenta"
                    )
                })
            Tab(
                selected = pagerState.currentPage == 1,
                text = {
                    Text("Eventos")
                },
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                },
                icon = { Icon(imageVector = Icons.Default.Event, contentDescription = "Eventos") })
        }
    }) { paddingValues ->
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            contentPadding = paddingValues
        ) { page ->
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when (page) {
                    0 -> Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black)
                            .padding(24.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Encabezado
                        Surface(
                            shape = CircleShape, // Forma circular
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(80.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Avatar de usuario",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxSize()
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Perfil de Usuario",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        //Datos del usuario
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp), // Redondear esquinas
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)) // Color de la carta
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    text = "Informacion Personal",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )

                                HorizontalDivider(color = Color.DarkGray)

                                // Nombre
                                ItemDatoCuenta(
                                    icono = Icons.Default.Person,
                                    titulo = "Nombre",
                                    valor = estado.nombre.ifBlank { "No registrado" }
                                )

                                // Correo
                                ItemDatoCuenta(
                                    icono = Icons.Default.Email,
                                    titulo = "Correo Electronico",
                                    valor = estado.correo.ifBlank { "No registrado" }
                                )

                                ItemDatoCuenta(
                                    icono = Icons.Default.Badge,
                                    titulo = "Cargo asignado",
                                    valor = estado.rol.ifBlank { "Cargo no asignado" }
                                )

                            }
                        }
                        Button(
                            onClick = {
                                // Limpiar el estado
                                viewModel.cerrarSesion()

                                // Navegar a IniciarScreen
                                navController.navigate("login") {
                                    popUpTo(0) {
                                        inclusive = true
                                    } // Limpia todas las pantallas anteriores
                                    launchSingleTop = true
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Cerrar Sesión", color = Color.White)
                        }
                    }

                    1 -> Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
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
                                    text = "Eventos y oportunidades",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                            }

                            Text(
                                text = "Selecciona un evento para ver su descripción",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.LightGray
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
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = expandido
                                        )
                                    },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedTextColor = Color.White,
                                        unfocusedTextColor = Color.White,
                                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                                        unfocusedBorderColor = Color.Gray,
                                        focusedContainerColor = Color(0xFF2A2A2A),
                                        unfocusedContainerColor = Color(0xFF2A2A2A)
                                    ),
                                    modifier = Modifier
                                        .menuAnchor()
                                        .fillMaxWidth()
                                )

                                ExposedDropdownMenu(
                                    expanded = expandido,
                                    onDismissRequest = { expandido = false },
                                    modifier = Modifier.background(Color(0xFF2A2A2A))
                                ) {
                                    if (listaEventos.isEmpty()) {
                                        DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = "No hay eventos registrados aún",
                                                    color = Color.LightGray
                                                )
                                            },
                                            onClick = { expandido = false }
                                        )
                                    } else {
                                        listaEventos.forEach { evento ->
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        text = "${evento.nombre} (${evento.fechaInicio})",
                                                        color = Color.White
                                                    )
                                                },
                                                onClick = {
                                                    eventoSeleccionado = evento.nombre
                                                    expandido = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        Button(
                            onClick = {
                                navController.navigate("eventos")
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Crear Eventos")
                        }
                    }
                }

            }
        }
    }
}

@Composable
private fun ItemDatoCuenta(
    icono: ImageVector,
    titulo: String,
    valor: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icono,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Column {
            Text(
                text = titulo,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )
            Text(
                text = valor,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    }
}