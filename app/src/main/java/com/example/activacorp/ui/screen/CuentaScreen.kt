package com.example.activacorp.ui.screen

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.example.activacorp.data.local.entity.UsuarioEntity
import com.example.activacorp.model.RolUsuario
import com.example.activacorp.viewmodel.EventoViewModel
import com.example.activacorp.viewmodel.OportunidadViewModel
import com.example.activacorp.viewmodel.OrganizacionViewModel
import kotlinx.coroutines.launch

// Estructura para las pestañas
private data class ItemNav(
    val titulo: String,
    val icono: ImageVector
)

private val itemsMenu = listOf(
    ItemNav("Cuenta", Icons.Default.Person),
    ItemNav("Eventos", Icons.Default.Event),
    ItemNav("Oportunidades", Icons.Default.Campaign),
    ItemNav("Organizaciones", Icons.Default.Business)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CuentaScreen(
    viewModel: UsuarioViewModel,
    navController: NavController,
    eventoViewModel: EventoViewModel,
    oportunidadViewModel: OportunidadViewModel,
    organizacionViewModel: OrganizacionViewModel
) {
    val estadoGlobal by viewModel.estado.collectAsState() // control de estado para el renderizado dinamico.

    val rolEnum = remember(estadoGlobal.rol) {
        try {
            if (estadoGlobal.rol.isNotBlank()) RolUsuario.valueOf(estadoGlobal.rol.uppercase()).titulo else "sin rol"
        } catch (e: IllegalArgumentException) {
            "Sin rol"
        }
    }

    val pagerState = rememberPagerState(pageCount = { 4 })
    val coroutineScope = rememberCoroutineScope()

    Scaffold(bottomBar = {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color(0xFF301934),
            contentColor = Color.White,
            divider = {},
            indicator = { tabPositions ->
                if (pagerState.currentPage < tabPositions.size) {
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        height = 2.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }) {
            // Iteracion
            itemsMenu.forEachIndexed { index, item ->
                Tab(
                    selected = pagerState.currentPage == index,
                    text = { Text(item.titulo) },
                    icon = { Icon(imageVector = item.icono, contentDescription = item.titulo) },
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }

        }
    }) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            contentPadding = innerPadding,
            beyondViewportPageCount = 0
        ) { page ->
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when (page) {
                    0 -> tabCuenta(viewModel, navController)
                    1 -> tabEventos(navController, eventoViewModel)
                    2 -> tabOportunidades(oportunidadViewModel, navController, rolEnum, estadoGlobal.id)
                    3 -> tabOrganizaciones(
                        navController,
                        eventoViewModel,
                        oportunidadViewModel,
                        organizacionViewModel,
                        rolEnum
                    )
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

@Composable
fun tabCuenta(
    viewModel: UsuarioViewModel,
    navController: NavController
) {
    var claveVisible by remember { mutableStateOf(false) }
    var mostrarDialogoEliminar by remember { mutableStateOf(false) } // Confirmar Eliminacion
    val estado by viewModel.estado.collectAsState()

    val rolEnum = remember(estado.rol) {
        try {
            if (estado.rol.isNotBlank()) RolUsuario.valueOf(estado.rol.uppercase()) else null
        } catch (e: IllegalArgumentException) {
            null
        }
    }

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
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp) // Espaciado más compacto
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
                valor = estado.correo.ifBlank { "No registrado" }
            )

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
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Boton Escáner QR
    OutlinedButton(
        onClick = { navController.navigate("qrCamara") },
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Text("Escanear Código QR")
    }

    // Botón Cerrar Sesion
    Button(
        onClick = {
            viewModel.cerrarSesion()
            navController.navigate("login") {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error,
            contentColor = MaterialTheme.colorScheme.onError
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Text("Cerrar Sesión")
    }

    // Boton Borrar Cuenta
    OutlinedButton(
        onClick = { mostrarDialogoEliminar = true },
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.error
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.error),
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Borrar Cuenta",
            tint = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Borrar Cuenta")
    }


    if (mostrarDialogoEliminar) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoEliminar = false },
            title = { Text("¿Eliminar cuenta?") },
            text = {
                Text("Esta acción es permanente y se borrarán todos tus datos asociados. ¿Estás seguro de que deseas continuar?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        mostrarDialogoEliminar = false
                        val entidadUsuario = UsuarioEntity(
                            id = estado.id,
                            nombre = estado.nombre,
                            correo = estado.correo,
                            clave = estado.clave,
                            rol = estado.rol
                        )
                        viewModel.eliminar(
                            usuario = entidadUsuario,
                            onEliminar = {
                                viewModel.cerrarSesion()
                                navController.navigate("login") {
                                    popUpTo(0) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    Text("Eliminar definitivamente", color = Color.White)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { mostrarDialogoEliminar = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun tabEventos(
    navController: NavController,
    eventoViewModel: EventoViewModel
) {
    val estadoEvento by eventoViewModel.estado.collectAsState()
    val listaEventos = estadoEvento.eventos
    var expandido by remember { mutableStateOf(false) }
    var eventoSeleccionado by remember(listaEventos) {
        mutableStateOf(
            listaEventos.firstOrNull()?.nombre ?: "Sin eventos disponibles"
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
                        text = "Eventos",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }

                Text(
                    text = "Lista de eventos registrados",
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
                        if (listaEventos.isEmpty()) {
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
            Button(
                onClick = {
                    navController.navigate("eventos")
                }, modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear Eventos")
            }
            Spacer(modifier = Modifier.height(6.dp))
            Button(
                onClick = {
                    navController.navigate("vistaEventos")
                }, modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver todos los Eventos")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun tabOportunidades(
    oportunidadViewModel: OportunidadViewModel,
    navController: NavController,
    rol: String,
    id: Long
    ) {

    val estadoOportunidad by oportunidadViewModel.estado.collectAsState()

    // Validacion de roles
    val esEjecutivo = remember(rol) {
        rol.equals("EJECUTIVO COMERCIAL", ignoreCase = true)
    }
    val esSupervisor = remember(rol) {
        rol.equals("SUPERVISOR COMERCIAL", ignoreCase = true)
    }
    Scaffold(topBar = {
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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                cardResumenGestion(
                    cantidad = estadoOportunidad.cantPendientes,
                    etiqueta = "Pendientes",
                    modifier = Modifier.weight(1f)
                )
                cardResumenGestion(
                    cantidad = estadoOportunidad.cantPrioridadAlta,
                    etiqueta = "Prioridad Alta",
                    modifier = Modifier.weight(1f)
                )
                cardResumenGestion(
                    cantidad = estadoOportunidad.cantConfirmadas,
                    etiqueta = "Confirmadas",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Requieren tu atención",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                if (estadoOportunidad.oportunidadesAltas.isEmpty()) {
                    item {
                        Text(
                            text = "No hay oportunidades de prioridad alta en este momento.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                } else {
                    items(
                        items = estadoOportunidad.oportunidadesAltas,
                        key = { it.nombreOportunidad + it.nombreOrganizacion }
                    ) { oppAlta ->
                        Card(
                            modifier = Modifier.width(220.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = oppAlta.nombreOportunidad,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = oppAlta.nombreOrganizacion,
                                    style = MaterialTheme.typography.bodySmall,
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis,
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
            }

            Spacer(modifier = Modifier.weight(1f))

            if (esEjecutivo) {
                modalOportunidades {
                    oportunidadesAsignadas(oportunidadViewModel, id)
            }

            }
            else if (esSupervisor) {
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

@Composable
fun cardResumenGestion(
    cantidad: Int,
    etiqueta: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(110.dp),
        shape = RoundedCornerShape(12.dp),
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
                text = cantidad.toString(),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = etiqueta,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun tabOrganizaciones(
    navController: NavController,
    eventoViewModel: EventoViewModel,
    oportunidadViewModel: OportunidadViewModel,
    organizacionViewModel: OrganizacionViewModel,
    rol: String
    ) {
    val estadoOrganizacion by organizacionViewModel.estado.collectAsState()
    val estadoEvento by eventoViewModel.estado.collectAsState()
    val estadoOportunidad by oportunidadViewModel.estado.collectAsState()

    val esSupervisor = remember(rol) {
        rol.equals("Supervisor Comercial", ignoreCase = true)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(
            top = 16.dp,
            bottom = 80.dp
        )
    ) {
        item {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Organizaciones",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                if (esSupervisor) {
                    IconButton(
                        onClick = { navController.navigate("CreacionOrganizacion") },
                        modifier = Modifier.background(
                            MaterialTheme.colorScheme.primaryContainer,
                            CircleShape
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Agregar Organización",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Explora las organizaciones, sus eventos y oportunidades asociadas.",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        if (estadoOrganizacion.organizaciones.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay organizaciones registradas.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            items(
                items = estadoOrganizacion.organizaciones,
                key = { it.id }
            ) { organizacion ->


                val eventosAsociados = remember(estadoEvento.eventos, organizacion.id) {
                    estadoEvento.eventos.filter { it.organizacion == organizacion.id }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        // Título, Tipo y Botones
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 8.dp)
                            ) {
                                Text(
                                    text = organizacion.nombre,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                SuggestionChip(
                                    onClick = {},
                                    label = {
                                        Text(
                                            organizacion.tipo.name.replace(
                                                "_",
                                                " "
                                            ), maxLines = 1
                                        )
                                    }
                                )
                            }

                            Row {
                                IconButton(
                                    onClick = {
                                        organizacionViewModel.prepararEdicion(organizacion)
                                        navController.navigate("CreacionOrganizacion")
                                    }
                                ) {
                                    Icon(
                                        Icons.Default.Edit,
                                        contentDescription = "Editar",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        organizacionViewModel.eliminarOrganizacion(organizacion)
                                    }
                                ) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Eliminar",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Eventos Activos (${eventosAsociados.size})",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        if (eventosAsociados.isEmpty()) {
                            Text(
                                text = "No hay eventos registrados para esta organización.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        } else {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                eventosAsociados.forEach { evento ->

                                    val cantidadOportunidades = remember(
                                        estadoOportunidad.oportunidades,
                                        evento.id
                                    ) {
                                        estadoOportunidad.oportunidades.count { it.eventoId == evento.id }
                                    }

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(
                                                MaterialTheme.colorScheme.surface,
                                                RoundedCornerShape(8.dp)
                                            )
                                            .padding(12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = evento.nombre,
                                                style = MaterialTheme.typography.bodyMedium,
                                                fontWeight = FontWeight.Medium,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                            Text(
                                                text = evento.fechaInicio,
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }

                                        val tieneOportunidades = cantidadOportunidades > 0

                                        Box(
                                            modifier = Modifier
                                                .background(
                                                    if (tieneOportunidades) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                                                    RoundedCornerShape(8.dp)
                                                )
                                                .padding(
                                                    horizontal = 8.dp,
                                                    vertical = 4.dp
                                                )
                                        ) {
                                            Text(
                                                text = "$cantidadOportunidades Op.",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = if (tieneOportunidades) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun modalOportunidades(contenido: @Composable () -> Unit) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()


    Button(
        onClick = { showBottomSheet = true },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = "Ver mis oportunidades asignadas",
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 24.dp)
            ) {
                // Título de cabecera dentro del modal
                Text(
                    text = "Oportunidades Asignadas",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // Contenedor del contenido
                Box(
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .fillMaxWidth()
                ) {
                    contenido()
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón de cerrar
                OutlinedButton(
                    onClick = {
                        coroutineScope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false // Destruye el modal
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cerrar")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun oportunidadesAsignadas(viewModel: OportunidadViewModel, id: Long) {
    // Ventana desplegable que muestra las oportunidades asignadas

    // Solo se ejecuta cuando cambia el id
    androidx.compose.runtime.LaunchedEffect(id) {
        viewModel.obtenerOportunidadesPorUsuario(id)
    }

    val estadoOportunidades by viewModel.estado.collectAsState()
    val oportunidades = estadoOportunidades.oportunidadUsuario

    if (oportunidades.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No tiene oportunidades asignadas actualmente",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }

    else { LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = oportunidades,
                key = { it.nombre + it.tipo } // Clave para optimizar recomposición y scroll
            ) { op ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = op.nombre,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = op.tipo,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = op.descripcion,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}