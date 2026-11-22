package com.example.activacorp

import com.example.activacorp.data.remote.ApiService
import com.example.activacorp.data.remote.RetroFitInstance
import com.example.activacorp.data.repository.EventoPostRepository
import com.google.gson.JsonParser
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class EventoPostRepositoryTest {

    private lateinit var repository: EventoPostRepository

    @BeforeEach
    fun setUp() {
        mockkObject(RetroFitInstance)
        repository = EventoPostRepository()
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `getEventosPosts delega la peticion a RetroFitInstance api y retorna el JsonObject`() = runTest {
        val jsonEsperado = JsonParser.parseString("""{"documents": []}""").asJsonObject
        val apiMock = mockk<ApiService>()

        every { RetroFitInstance.api } returns apiMock
        coEvery { apiMock.getEventosPosts() } returns jsonEsperado

        val resultado = repository.getEventosPosts()

        assertEquals(jsonEsperado, resultado)
        coVerify(exactly = 1) { apiMock.getEventosPosts() }
    }


}