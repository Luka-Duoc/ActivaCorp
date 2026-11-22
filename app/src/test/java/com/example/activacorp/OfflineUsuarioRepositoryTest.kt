package com.example.activacorp
import com.example.activacorp.data.local.dao.UsuarioDao
import com.example.activacorp.data.local.entity.UsuarioEntity
import com.example.activacorp.data.repository.OfflineUsuarioRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OfflineUsuarioRepositoryTest {

    private val usuarioDao: UsuarioDao = mockk(relaxed = true)

    private lateinit var repository: OfflineUsuarioRepository

    @BeforeEach
    fun setUp() {
        repository = OfflineUsuarioRepository(usuarioDao)
    }

    @Test
    fun `obtenerUsuarios devuelve el estado emitido por el dao`() = runTest {
        val usuariosFake = listOf(
            UsuarioEntity(1L, "Luka", "luka@ejecutivo.cl", "123456", "EJECUTIVO"),
            UsuarioEntity(2L, "nacho", "nacho@supervisor.cl", "654321", "SUPERVISOR")
        )
        every { usuarioDao.obtenerUsuarios() } returns flowOf(usuariosFake)

        val resultado = repository.obtenerUsuarios().first()

        assertEquals(usuariosFake, resultado)
    }

    @Test
    fun `insertarUsuario llama a insertar en el dao`() = runTest {
        val nuevoUsuario = UsuarioEntity(0L, "Carlos", "carlos@ejecutivo.cl", "123456", "EJECUTIVO")

        repository.insertarUsuario(nuevoUsuario)

        coVerify(exactly = 1) { usuarioDao.insertar(nuevoUsuario) }
    }

    @Test
    fun `buscarCorreo retorna el usuario cuando existe en el dao`() = runTest {
        val usuarioEsperado = UsuarioEntity(5L, "nacho", "nacho@supervisor.cl", "654321", "SUPERVISOR")
        coEvery { usuarioDao.buscarCorreo("nacho@supervisor.cl") } returns usuarioEsperado

        val resultado = repository.buscarCorreo("nacho@supervisor.cl")

        assertEquals(usuarioEsperado, resultado)
    }

    @Test
    fun `login retorna al usuario cuando las credenciales coinciden y retorna null cuando fallan`() = runTest {
        val usuarioValido = UsuarioEntity(1L, "Luka", "luka@ejecutivo.cl", "123456", "EJECUTIVO")
        coEvery { usuarioDao.login("luka@ejecutivo.cl", "123456") } returns usuarioValido
        coEvery { usuarioDao.login("luka@ejecutivo.cl", "mal") } returns null

        assertEquals(usuarioValido, repository.login("luka@ejecutivo.cl", "123456"))
        assertNull(repository.login("luka@ejecutivo.cl", "mal"))
    }

    @Test
    fun `eliminarUsuario llama al dao para borrar el registro`() = runTest {
        val usuario = UsuarioEntity(1L, "nacho", "nacho@ejecutivo.cl", "123456", "EJECUTIVO")

        repository.eliminarUsuario(usuario)

        coVerify(exactly = 1) { usuarioDao.eliminar(usuario) }
    }
}