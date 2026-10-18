package com.example.activacorp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.activacorp.data.local.dao.ComunaDao
import com.example.activacorp.data.local.dao.EventoDao
import com.example.activacorp.data.local.dao.OportunidadDao
import com.example.activacorp.data.local.dao.OrganizacionDao
import com.example.activacorp.data.local.dao.RegionDao
import com.example.activacorp.data.local.dao.UsuarioDao
import com.example.activacorp.data.local.entity.ComunaEntity
import com.example.activacorp.data.local.entity.EventoEntity
import com.example.activacorp.data.local.entity.OportunidadEntity
import com.example.activacorp.data.local.entity.OrganizacionEntity
import com.example.activacorp.data.local.entity.RegionEntity
import com.example.activacorp.data.local.entity.UsuarioEntity
import com.example.activacorp.data.local.seed.ComunaSeed
import com.example.activacorp.data.local.seed.EventoSeed
import com.example.activacorp.data.local.seed.OportunidadSeed
import com.example.activacorp.data.local.seed.OrganizacionSeed
import com.example.activacorp.data.local.seed.RegionSeed


@Database(
    entities = [
        UsuarioEntity::class,
        OportunidadEntity::class,
        EventoEntity::class,
        OrganizacionEntity::class,
        RegionEntity::class,
        ComunaEntity::class,
    ], version = 4, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun UsuarioDao(): UsuarioDao
    abstract fun EventoDao(): EventoDao
    abstract fun ComunaDao(): ComunaDao
    abstract fun RegionDao(): RegionDao
    abstract fun OrganizacionDao(): OrganizacionDao

    abstract fun OportunidadDao(): OportunidadDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        db.execSQL("PRAGMA foreign_keys = OFF;")

                        RegionSeed.listaRegiones.forEach { region ->
                            db.execSQL(
                                "INSERT INTO regiones (id, nombre) VALUES (?, ?)",
                                arrayOf(region.id, region.nombre)
                            )
                        }
                        ComunaSeed.listaComunas.forEach { comuna ->
                            db.execSQL(
                                "INSERT INTO comunas (id, idRegion,nombre) VALUES (?, ?, ?)",
                                arrayOf(comuna.id, comuna.idRegion, comuna.nombre)
                            )
                        }
                        OrganizacionSeed.listaOrganizaciones.forEach { organizacion ->
                            db.execSQL(
                                """
                               INSERT INTO organizaciones (id, nombre, tipo, rubro, comuna, sitioWeb, publico, historial) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                                """.trimIndent(),
                                arrayOf(
                                    organizacion.id,
                                    organizacion.nombre,
                                    organizacion.tipo.name,
                                    organizacion.rubro,
                                    organizacion.comuna,
                                    organizacion.sitioWeb,
                                    organizacion.publico,
                                    organizacion.historial
                                )
                            )
                        }
                        EventoSeed.listaEventos.forEach { evento ->
                            db.execSQL(
                                """
                                INSERT INTO eventos (
                                    id, nombre, tipo, fechaInicio, fechaTermino, horario,
                                    direccion, comuna, organizacion, cantAsistentes,
                                    requisitos, costosReferenciales, observaciones
                                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                                """.trimIndent(),
                                arrayOf(
                                    evento.id,
                                    evento.nombre,
                                    evento.tipo,
                                    evento.fechaInicio,
                                    evento.fechaTermino,
                                    evento.horario,
                                    evento.direccion,
                                    evento.comuna,
                                    evento.organizacion,
                                    evento.cantAsistentes,
                                    evento.requisitos,
                                    evento.costosReferenciales,
                                    evento.observaciones
                                )
                            )
                        }
                        OportunidadSeed.listaOportunidades.forEach { oportunidad ->
                            db.execSQL(
                                """
                                INSERT INTO oportunidades (
                                    eventoId, nombre, tipo, descripcion, fuente, fechaDeteccion, 
                                    responsable, prioridad, potencial, estado, 
                                    fechaActualizacion, observaciones
                                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                                """.trimIndent(),
                                arrayOf(
                                    1L,
                                    oportunidad.nombre,
                                    oportunidad.tipo.name,
                                    oportunidad.descripcion,
                                    oportunidad.fuente,
                                    oportunidad.fechaDeteccion,
                                    oportunidad.responsable,
                                    oportunidad.prioridad,
                                    oportunidad.potencial.name,
                                    oportunidad.estado.name,
                                    oportunidad.fechaActualizacion,
                                    oportunidad.observaciones
                                )
                            )
                        }

                        db.execSQL("PRAGMA foreign_keys = ON;")

                        }
                })
                    .fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
        }
    }
}