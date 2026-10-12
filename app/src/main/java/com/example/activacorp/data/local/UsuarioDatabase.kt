package com.example.activacorp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [
    UsuarioEntity::class,
    EventoEntity::class,
    OrganizacionEntity::class,
    RegionEntity::class,
    ComunaEntity::class
                     ], version = 4)
abstract class UsuarioDatabase : RoomDatabase() {
    abstract fun UsuarioDao(): UsuarioDao
    abstract fun EventoDao(): EventoDao

    companion object {
        @Volatile
        private var INSTANCE: UsuarioDatabase? = null

        fun getDatabase(context: Context): UsuarioDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    UsuarioDatabase::class.java,
                    "usuario_database"
                ).fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
        }
    }
}