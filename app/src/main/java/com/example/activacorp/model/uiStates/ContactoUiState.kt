package com.example.activacorp.model.uiStates

import com.example.activacorp.data.local.entity.ContactoEntity
import com.example.activacorp.data.local.entity.OrganizacionEntity
import com.example.activacorp.model.enums.AreaContacto
import com.example.activacorp.model.enums.EstadoContacto

data class ContactoUiState (
    val contactos: List<ContactoEntity> = emptyList(),
    val contactoIdEdicion: Long? = null,
    val nombre: String = "",
    val cargo: String = "",
    val area: AreaContacto = AreaContacto.EVENTOS,
    val correo: String = "",
    val telefono: String = "",
    val estado: EstadoContacto = EstadoContacto.NUEVO,
    val observaciones: String = "",

    //DropMenus
    val organizaciones: List<OrganizacionEntity> = emptyList(),
    val organizacionId: Long = 0L,
    val organizacionSeleccionada: String = "",

    //Validacion
    val errorNombre: String? = null,
    val errorCargo: String? = null,
    val ErrorOrganizacion: String? = null,
    val ErrorContacto: String? = null,
    val mensajeError: String? = null,
    val exitoSave: Boolean = false,

    )