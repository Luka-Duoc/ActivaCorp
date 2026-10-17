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
        primary = Color(0XFF6750A4),
        onPrimary = Color.White,
        background = Color(0XFF121212),
        surface = Color(0XFF1E1E1E),
        onSurface = Color.White,
        error = Color(0XFFB3261E)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verificar permiso inicial
        hasCameraPermission = CameraPermissionHelper.hasCameraPermission(this)

        setContent {
            MaterialTheme (colorScheme = ColoresApp){
                Surface {

                    AppNavigation()
                }
            }
        }

        // Observa los resultados del QR
        qrViewModel.qrResult.observe(this) { qrResult ->
            qrResult?.let { result ->
                Toast.makeText(this, "Código QR Detectado: ${result.content}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Actualizar estado del permiso cuando la app se reanuda
        hasCameraPermission = CameraPermissionHelper.hasCameraPermission(this)
    }
}




