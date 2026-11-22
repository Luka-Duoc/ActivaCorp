package com.example.activacorp

import com.example.activacorp.data.repository.EventoPostRepository
import com.example.activacorp.viewmodel.EventoPostViewModel
import com.google.gson.JsonParser
import io.mockk.coEvery
import io.mockk.mockkConstructor
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@OptIn(ExperimentalCoroutinesApi::class)
class EventoPostViewModelTest {

    @JvmField
    @RegisterExtension
    val mainDispatcherRule = MainDispatcherRule()

    @BeforeEach
    fun setUp() {
        // Intercepta la creación interna de EventoPostRepository() dentro del ViewModel
        mockkConstructor(EventoPostRepository::class)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `fetchPost parsea el json de documents y actualiza postList`() = runTest {
        val jsonRespuesta = JsonParser.parseString(
            """
            {
              "documents": [
                {
                  "fields": {
                    "nombre": { "stringValue": "Feria Corporativa" },
                    "tipo": { "stringValue": "Feria" },
                    "horario": { "stringValue": "10:00 - 18:00" },
                    "fechaInicio": { "stringValue": "10/12/2026" },
                    "fechaTermino": { "stringValue": "12/12/2026" },
                    "direccion": { "stringValue": "Av. Las Condes 5000" },
                    "comuna": { "integerValue": "3" },
                    "asistentes": { "integerValue": "200" },
                    "costosEstimados": { "integerValue": "500000" },
                    "requisitos": { "stringValue": "Stand y credencial" },
                    "observaciones": { "stringValue": "Montaje temprano" },
                    "organizacion": { "integerValue": "2" }
                  }
                }
              ]
            }
            """.trimIndent()
        ).asJsonObject

        coEvery { anyConstructed<EventoPostRepository>().getEventosPosts() } returns jsonRespuesta

        // al instanciar el ViewModel se ejecuta init { fetchPost() }
        val viewModel = EventoPostViewModel()
        advanceUntilIdle()

        val lista = viewModel.postList.value
        assertEquals(1, lista.size, "Debe parsear 1 evento desde el JSON")
        assertEquals("Feria Corporativa", lista[0].nombre)
        assertEquals("Feria", lista[0].tipo)
        assertEquals("10:00 - 18:00", lista[0].horario)
        assertEquals(3L, lista[0].comuna)
        assertEquals(200, lista[0].asistentes)
        assertEquals(500000, lista[0].costosEstimados)
        assertEquals(2L, lista[0].organizacion)
    }

    @Test
    fun `fetchPost deja postList vacia cuando el json no trae documents`() = runTest {
        val jsonVacio = JsonParser.parseString("{}").asJsonObject
        coEvery { anyConstructed<EventoPostRepository>().getEventosPosts() } returns jsonVacio

        val viewModel = EventoPostViewModel()
        advanceUntilIdle()

        assertTrue(viewModel.postList.value.isEmpty(), "postList debe quedar vacia si no hay documentos")
    }

    @Test
    fun `fetchPost maneja excepcion de red sin romper y mantiene postList vacia`() = runTest {
        coEvery { anyConstructed<EventoPostRepository>().getEventosPosts() } throws RuntimeException("Sin conexión")

        val viewModel = EventoPostViewModel()
        advanceUntilIdle()

        assertTrue(viewModel.postList.value.isEmpty(), "postList debe mantenerse vacía ante un error de API")
    }
}