package com.example.activacorp.ui.screen.tab

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.activacorp.data.local.entity.UsuarioEntity
import com.example.activacorp.model.RolUsuario
import com.example.activacorp.viewmodel.UsuarioViewModel

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
        modifier = Modifier.fillMaxWidth(0.9F),
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
        modifier = Modifier.fillMaxWidth(0.85F),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
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
        modifier = Modifier.fillMaxWidth(0.85F),
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
        modifier = Modifier.fillMaxWidth(0.85F),
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