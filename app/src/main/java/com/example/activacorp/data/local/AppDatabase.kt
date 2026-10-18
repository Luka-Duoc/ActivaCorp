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
    ], version = 1, exportSchema = false
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
                                "INSERT INTO organizaciones (id, nombre, tipo, rubro, comuna, sitioWeb, publico, historial) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                                arrayOf(
                                    organizacion.id,
                                    organizacion.nombre,
                                    organizacion.tipo,
                                    organizacion.rubro,
                                    organizacion.comuna,
                                    organizacion.sitioWeb,
                                    organizacion.publico,
                                    organizacion.historial
                                )
                            )
                        }
                        OportunidadSeed.listaOportunidades.forEach { oportunidad ->
                            db.execSQL(
                                """
                            INSERT INTO oportunidades (
                                nombre, tipo, descripcion, fuente, fechaDeteccion, 
                                responsable, prioridad, potencial, estado, 
                                fechaActualizacion, observaciones
                            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                            """.trimIndent(),
                                        arrayOf(
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

                        }
                })
                    .fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
        }
    }
}