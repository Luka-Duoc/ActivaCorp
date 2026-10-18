package com.example.activacorp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.collectAsState
import com.example.activacorp.viewmodel.UsuarioViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.activacorp.model.RolUsuario
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
    var claveVisible by remember { mutableStateOf(false) }
    var eventoSeleccionado by remember(listaEventos) {
        mutableStateOf(
            listaEventos.firstOrNull()?.nombre ?: "Sin eventos disponibles"
        )
    }
    val pagerState = rememberPagerState(pageCount = { 3 })
    Scaffold(bottomBar = {
        val coroutineScope = rememberCoroutineScope()
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = darkPurple,
            contentColor = Color.White,
            divider = {},
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    height = 2.dp,
                    color = MaterialTheme.colorScheme.scrim
                )
            }) {
            Tab(selected = pagerState.currentPage == 0, text = { Text("Cuenta") }, onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(0)
                }
            }, icon = {
                Icon(
                    imageVector = Icons.Default.Person, contentDescription = "Cuenta"
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
            Tab(selected = pagerState.currentPage == 2, text = {
                Text("Oportunidades")
            }, onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(2)
                }
            }, icon = {
                Icon(
                    imageVector = Icons.Default.Campaign, contentDescription = "Oportunidades"
                )
            })
        }
    }) { innerPadding ->
        HorizontalPager(
            state = pagerState, userScrollEnabled = false, contentPadding = innerPadding
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
                            .background(MaterialTheme.colorScheme.background)
                            .padding(24.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Encabezado
                        Surface(
                            shape = CircleShape,
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

                        val rolEnum = try {
                            if (estado.rol.isNotBlank()) RolUsuario.valueOf(estado.rol.uppercase()) else null
                        } catch (e: IllegalArgumentException) {
                            null
                        }

                        // 2. Mostrar Nombre
                        Text(
                            text = estado.nombre.ifBlank { "Usuario Desconocido" },
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        // 3. Mostrar el Título del Rol en lugar del texto crudo
                        Text(
                            text = rolEnum?.titulo ?: "Cargo no asignado",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )

                        // 4. Mostrar la descripción debajo (Opcional pero recomendado)
                        if (rolEnum != null) {
                            Text(
                                text = rolEnum.descripcion,
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.Gray
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Indicador visual de estado (activo/inactivo)
                        SuggestionChip(
                            onClick = { },
                            label = { Text(if (estado.activo) "Cuenta Activa" else "Cuenta Inactiva") },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = if (estado.activo) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer,
                                labelColor = if (estado.activo) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onErrorContainer
                            ),
                            icon = {
                                Icon(
                                    imageVector = if (estado.activo) Icons.Default.CheckCircle else Icons.Default.Cancel,
                                    contentDescription = "Estado de la cuenta",
                                    modifier = Modifier.size(16.dp)
                                )
                            })

                        Spacer(modifier = Modifier.height(24.dp))

                        // Datos del usuario
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    text = "Información de la Cuenta",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                                // ID del usuario formateado
                                ItemDatoCuenta(
                                    icono = Icons.Default.Badge,
                                    titulo = "ID de Empleado",
                                    valor = "#${estado.id.toString().padStart(4, '0')}"
                                )

                                // Correo
                                ItemDatoCuenta(
                                    icono = Icons.Default.Email,
                                    titulo = "Correo Electrónico",
                                    valor = estado.correo.ifBlank { "No registrado" })

                                // Contraseña oculta
                                ItemDatoCuenta(
                                    icono = Icons.Default.Lock,
                                    titulo = "Contraseña",
                                    valor = if (claveVisible) estado.clave else "••••••••",
                                    trailingIcon = {
                                        IconButton(onClick = { claveVisible = !claveVisible }) {
                                            Icon(
                                                imageVector = if (claveVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                                contentDescription = if (claveVisible) "Ocultar contraseña" else "Mostrar contraseña",
                                                tint = Color.Gray
                                            )
                                        }
                                    })
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))

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
                            }, colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            ), modifier = Modifier.fillMaxWidth()
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
                                        DropdownMenuItem(text = {
                                            Text(
                                                text = "No hay eventos registrados aún",
                                                color = Color.LightGray
                                            )
                                        }, onClick = { expandido = false })
                                    } else {
                                        listaEventos.forEach { evento ->
                                            DropdownMenuItem(text = {
                                                Text(
                                                    text = "${evento.nombre} (${evento.fechaInicio})",
                                                    color = Color.White
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
                        Button(
                            onClick = {
                                navController.navigate("eventos")
                            }, modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Crear Eventos")
                        }
                    }

                    2 -> Scaffold(topBar = {
                        TopAppBar(
                            title = {
                            Text(text = "Hola, Ejecutivo")
                        }, actions = {
                            IconButton(onClick = { }) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = "Alertas"
                                )
                            }
                        }, colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background
                        )
                        )
                    }, floatingActionButton = {
                        FloatingActionButton(
                            onClick = { navController.navigate("oportunidades") },
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Registrar Nueva Oportunidad"
                            )
                        }
                    }) { innerPadding ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .padding(horizontal = 16.dp)
                        ) {
                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Resumen de Gestión",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(IntrinsicSize.Max),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Card(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(12.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = "--",
                                            style = MaterialTheme.typography.headlineMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Text(
                                            text = "Pendientes",
                                            textAlign = TextAlign.Center,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                Card(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(12.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = "--",
                                            style = MaterialTheme.typography.headlineMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Text(
                                            text = "Prioridad Alta",
                                            textAlign = TextAlign.Center,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                Card(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(12.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = "--",
                                            style = MaterialTheme.typography.headlineMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Text(
                                            text = "Confirmadas",
                                            textAlign = TextAlign.Center,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(32.dp))

                            Text(
                                text = "Requieren tu atención",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                contentPadding = (innerPadding)
                            ) {
                                items(3) { index ->
                                    Card(
                                        modifier = Modifier.width(220.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                                        )
                                    ) {
                                        Column(modifier = Modifier.padding(16.dp)) {
                                            Text(
                                                text = "Nombre de oportunidad",
                                                style = MaterialTheme.typography.titleSmall,
                                                fontWeight = FontWeight.Bold,
                                                maxLines = 1
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = "Organización asociada",
                                                style = MaterialTheme.typography.bodySmall,
                                                maxLines = 1,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                            Spacer(modifier = Modifier.height(12.dp))
                                            SuggestionChip(
                                                onClick = { },
                                                label = { Text("Prioridad Alta") },
                                                colors = SuggestionChipDefaults.suggestionChipColors(
                                                    containerColor = MaterialTheme.colorScheme.errorContainer,
                                                    labelColor = MaterialTheme.colorScheme.onErrorContainer
                                                )
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            Button(
                                onClick = { navController.navigate("oportunidades") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp),
                                shape = MaterialTheme.shapes.medium
                            ) {
                                Text(
                                    text = "Ver todas las oportunidades",
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
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
    valor: String,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
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
                    text = titulo, style = MaterialTheme.typography.labelMedium, color = Color.Gray
                )
                Text(
                    text = valor,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        if (trailingIcon != null) {
            trailingIcon()
        }
    }
}