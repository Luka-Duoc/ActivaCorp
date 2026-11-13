package com.example.activacorp.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.activacorp.data.local.entity.UsuarioEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "session_user")

class SessionDataStore(private val context: Context) {
    companion object {
        private val USER_ID = longPreferencesKey("user_id")
        private val USER_NOMBRE = stringPreferencesKey("user_nombre")
        private val USER_CORREO = stringPreferencesKey("user_correo")
        private val USER_CLAVE = stringPreferencesKey("user_clave")
        private val USER_ROL = stringPreferencesKey("user_rol")
    }

    // Flujo que valida la session
    val sesionUsuario: Flow<UsuarioEntity?> = context.dataStore.data.map { prefs ->
        val id = prefs[USER_ID] ?: 0L
        val rol = prefs[USER_ROL] ?: ""
        if (id != 0L && rol.isNotBlank()) {
            UsuarioEntity(
                id = id,
                nombre = prefs[USER_NOMBRE] ?: "",
                correo = prefs[USER_CORREO] ?: "",
                clave = prefs[USER_CLAVE] ?: "",
                rol = rol
            )
        } else {
            null
        }
    }

    suspend fun guardarSesion(usuario: UsuarioEntity) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID] = usuario.id
            prefs[USER_NOMBRE] = usuario.nombre
            prefs[USER_CORREO] = usuario.correo
            prefs[USER_CLAVE] = usuario.clave
            prefs[USER_ROL] = usuario.rol
        }
    }

    suspend fun limpiarSesion() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }

}
