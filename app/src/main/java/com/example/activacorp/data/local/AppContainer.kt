package com.example.activacorp.data.local

import android.content.Context
import com.example.activacorp.data.repository.ComunaRepository
import com.example.activacorp.data.repository.ContactoRepository
import com.example.activacorp.data.repository.EventoRepository
import com.example.activacorp.data.repository.offlineRepositories.OfflineComunaRepository
import com.example.activacorp.data.repository.offlineRepositories.OfflineContactoRepository
import com.example.activacorp.data.repository.offlineRepositories.OfflineEventoRepository
import com.example.activacorp.data.repository.offlineRepositories.OfflineOportunidadRepository
import com.example.activacorp.data.repository.offlineRepositories.OfflineOrganizacionRepository
import com.example.activacorp.data.repository.offlineRepositories.OfflineRegionRepository
import com.example.activacorp.data.repository.offlineRepositories.OfflineResultadoRepository
import com.example.activacorp.data.repository.offlineRepositories.OfflineSeguimientoRepository
import com.example.activacorp.data.repository.offlineRepositories.OfflineUsuarioRepository
import com.example.activacorp.data.repository.OportunidadRepository
import com.example.activacorp.data.repository.OrganizacionRepository
import com.example.activacorp.data.repository.RegionRepository
import com.example.activacorp.data.repository.ResultadoRepository
import com.example.activacorp.data.repository.SeguimientoRepository
import com.example.activacorp.data.repository.UsuarioRepository

interface AppContainer {
    val usuarioRepository: UsuarioRepository
    val eventoRepository: EventoRepository
    val oportunidadRepository: OportunidadRepository
    val organizacionRepository: OrganizacionRepository

    val comunaRepository: ComunaRepository
    val regionRepository: RegionRepository
    val contactoRepository: ContactoRepository

    val seguimientoRepository: SeguimientoRepository

    val resultadoRepository: ResultadoRepository

}

class AppDataContainer(private val context: Context) : AppContainer {

    private val database: AppDatabase by lazy {
        AppDatabase.getDatabase(context)
    }
    override val usuarioRepository: UsuarioRepository by lazy {
        OfflineUsuarioRepository(database.UsuarioDao())
    }
    override val eventoRepository: EventoRepository by lazy {
        OfflineEventoRepository(database.EventoDao())
    }
    override val oportunidadRepository: OportunidadRepository by lazy {
        OfflineOportunidadRepository(database.OportunidadDao())
    }
    override val organizacionRepository: OrganizacionRepository by lazy {
        OfflineOrganizacionRepository(database.OrganizacionDao())
    }
    override val comunaRepository: ComunaRepository by lazy {
        OfflineComunaRepository(database.ComunaDao())
    }
    override val regionRepository: RegionRepository by lazy {
        OfflineRegionRepository(database.RegionDao())
    }
    override val contactoRepository: ContactoRepository by lazy {
        OfflineContactoRepository(database.ContactoDao())
    }

    override val seguimientoRepository: SeguimientoRepository by lazy {
        OfflineSeguimientoRepository(database.SeguimientoDao())
    }

    override val resultadoRepository: ResultadoRepository by lazy {
        OfflineResultadoRepository(database.ResultadoDao())
    }

}