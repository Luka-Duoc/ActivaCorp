package com.example.activacorp
import com.example.activacorp.viewmodel.QrViewModel
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import kotlin.test.junit5.JUnit5Asserter.assertEquals

@ExtendWith(InstantTaskExecutorExtension::class)
class QrViewModelTest {
    private lateinit var viewModel: QrViewModel

    @BeforeEach
    fun setUp() {
        viewModel = QrViewModel()
    }

    @Test
    fun `onQrDetected toma un codigo QR y modifica el valor de qrResult`() = runTest{
        val qr = "textoQR"
        viewModel.onQrDetected(qr)
        assertEquals("No se asigno QR", qr, viewModel.qrResult.value?.content)
    }

    @Test
    fun `clearResult anula el valor de qrResult`() = runTest {
        val qr = "textoQR"
        viewModel.onQrDetected(qr)
        viewModel.clearResult()
        assertEquals("No se ha borrado el QR", null, viewModel.qrResult.value)
    }

}