package com.example.activacorp.data.local

import android.content.Context
import com.example.activacorp.data.repository.ComunaRepository
import com.example.activacorp.data.repository.ContactoRepository
import com.example.activacorp.data.repository.EventoRepository
import com.example.activacorp.data.repository.OfflineComunaRepository
import com.example.activacorp.data.repository.OfflineContactoRepository
import com.example.activacorp.data.repository.OfflineEventoRepository
import com.example.activacorp.data.repository.OfflineOportunidadRepository
import com.example.activacorp.data.repository.OfflineOrganizacionRepository
import com.example.activacorp.data.repository.OfflineRegionRepository
import com.example.activacorp.data.repository.OfflineResultadoRepository
import com.example.activacorp.data.repository.OfflineSeguimientoRepository
import com.example.activacorp.data.repository.OfflineUsuarioRepository
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