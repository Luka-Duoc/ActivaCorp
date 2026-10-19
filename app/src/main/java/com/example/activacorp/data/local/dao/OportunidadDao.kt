package com.example.activacorp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.activacorp.data.local.entity.OportunidadEntity
import com.example.activacorp.model.OportunidadConOrganizacion
import kotlinx.coroutines.flow.Flow

@Dao
interface OportunidadDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(org: List<OportunidadEntity>)

    @Update
    suspend fun actualizar(oportunidad: OportunidadEntity)

    @Delete
    suspend fun eliminar(oportunidad: OportunidadEntity)

    @Query("SELECT * FROM oportunidades")
    fun obtenerTodas(): Flow<List<OportunidadEntity>>

    @Query("SELECT COUNT(*) from oportunidades where estado = 'CONTACTO_PENDIENTE'")
    fun contarPendientes(): Flow<Int>

    @Query("SELECT COUNT(*) from oportunidades where prioridad > 3")
    fun contarPrioridadAlta(): Flow<Int>

    @Query("SELECT COUNT(*) from oportunidades where estado = 'PARTICIPACION_CONFIRMADA'")
    fun contarConfirmadas(): Flow<Int>

    @Query("select o.nombre as nombreOportunidad, org.nombre as nombreOrganizacion from oportunidades o join eventos e on o.eventoId = e.id join organizaciones org on org.id = e.organizacion where o.prioridad = 5")
    fun obtenerOpPrioridadAlta(): Flow<List<OportunidadConOrganizacion>>

}