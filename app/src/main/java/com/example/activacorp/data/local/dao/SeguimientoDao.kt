package com.example.activacorp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.activacorp.data.local.entity.SeguimientoEntity
import com.example.activacorp.model.GestionesPartConfirmada
import kotlinx.coroutines.flow.Flow

@Dao
interface SeguimientoDao {

    @Insert
    suspend fun insertar(seguimiento: SeguimientoEntity)

    @Update
    suspend fun actualizar(seguimiento: SeguimientoEntity)

    @Delete
    suspend fun delete(seguimiento: SeguimientoEntity)

    @Query("select * from seguimientos")
    fun obtenerSeguimientos(): Flow<List<SeguimientoEntity>>

    @Query("""select o.nombre, 
s.fecha, 
s.hora, 
s.tipoDeAccion,
r.estadoFinal
 from seguimientos s 
join oportunidades o on o.id = s.oportunidadRelacionada join resultados r on r.id = s.resultado
where r.estadoFinal = 'PARTICIPACION_CONFIRMADA';""")
    fun obtenerGestionesDeParticipacionConfirmada(): Flow<List<GestionesPartConfirmada>>

}