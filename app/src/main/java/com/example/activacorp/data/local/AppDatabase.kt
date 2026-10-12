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
                     ], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun UsuarioDao(): UsuarioDao
    abstract fun EventoDao(): EventoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
        }
    }
}