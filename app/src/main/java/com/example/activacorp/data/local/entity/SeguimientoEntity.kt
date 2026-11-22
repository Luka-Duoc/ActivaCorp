package com.example.activacorp.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.activacorp.model.TipoDeAccion

@Entity(
    tableName = "seguimientos", foreignKeys = [
        ForeignKey(
            entity = OportunidadEntity::class,
            parentColumns = ["id"],
            childColumns = ["oportunidadRelacionada"],
            onDelete = ForeignKey.CASCADE
        ),
    ForeignKey(
        entity = UsuarioEntity::class,
        parentColumns = ["id"],
        childColumns = ["usuarioResponsable"],
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = ResultadoEntity::class,
        parentColumns = ["id"],
        childColumns = ["resultado"],
        onDelete = ForeignKey.CASCADE
    )
    ], indices = [Index(value = ["oportunidadRelacionada"]), Index(value = ["usuarioResponsable"]), Index(value = ["resultado"])]
)
data class SeguimientoEntity(
    @PrimaryKey(true)
    val id: Long = 0,
    val oportunidadRelacionada: Long,
    val usuarioResponsable: Long,
    val tipoDeAccion: TipoDeAccion,
    val fecha: String,
    val hora: String,
    val resultado: Long?,
    val proximaAccion: TipoDeAccion,
    val fechaProxSeguimiento: String?,
    val observaciones: String?
)
