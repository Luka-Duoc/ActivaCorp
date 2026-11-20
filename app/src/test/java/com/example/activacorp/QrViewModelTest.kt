package com.example.activacorp
import com.example.activacorp.viewmodel.QrViewModel
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import kotlin.test.junit5.JUnit5Asserter.assertEquals

class QrViewModelTest {
    @Test
    fun `onQrDetected toma un codigo QR y modifica el valor de qrResult`() = runTest {
        val viewModel = QrViewModel()
        val qr = "textoQR"
        viewModel.onQrDetected(qr)
        assertEquals("No se asigno QR", qr, viewModel.qrResult.value)
    }

    @Test
    fun `clearResult anula el valor de qrResult`() = runTest {
        val viewModel = QrViewModel()
        val qr = "textoQR"
        viewModel.onQrDetected(qr)
        viewModel.clearResult()
        assertEquals("No se ha borrado el QR", null, viewModel.qrResult.value)
    }

}