package com.example.activacorp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import com.example.activacorp.navigation.AppNavigation
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import com.example.activacorp.ui.theme.BackgroundDark
import com.example.activacorp.ui.theme.PurpleAccent
import com.example.activacorp.ui.theme.PurplePrimary
import com.example.activacorp.ui.theme.SurfaceDark
import com.example.activacorp.ui.theme.TextGray
import com.example.activacorp.ui.theme.TextWhite
import com.example.activacorp.utils.CameraPermissionHelper
import com.example.activacorp.viewmodel.QrViewModel


class MainActivity : ComponentActivity() {

    private val qrViewModel: QrViewModel by viewModels()

    // Estado para controlar si tenemos permiso
    private var hasCameraPermission by mutableStateOf(false)

    // Registro para solicitar permisos
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
        if (isGranted) {
            Toast.makeText(this, "Permiso de cámara concedido", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Se necesita permiso de cámara para escanear QR", Toast.LENGTH_LONG).show()
        }
    }

    private val ColoresApp = darkColorScheme(
        primary = PurplePrimary,
        secondary = PurpleAccent,
        background = BackgroundDark,
        surface = SurfaceDark,
        onPrimary = TextWhite,
        onSecondary = TextWhite,
        onBackground = TextWhite,
        onSurface = TextWhite,
        onSurfaceVariant = TextGray,
        error = Color(0XFFB3261E)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = ColoresApp) {
                Surface {
                    AppNavigation()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Actualizar estado del permiso cuando la app se reanuda
        hasCameraPermission = CameraPermissionHelper.hasCameraPermission(this)
    }
}




