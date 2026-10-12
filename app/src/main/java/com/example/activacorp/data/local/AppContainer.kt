package com.example.activacorp.data.local

import android.content.Context

interface AppContainer {
    val usuarioRepository: UsuarioRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val usuarioRepository: UsuarioRepository by lazy {
        OfflineUsuarioRepository(AppDatabase.getDatabase(context).UsuarioDao())
    }
}