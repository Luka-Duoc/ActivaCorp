package com.example.activacorp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.activacorp.data.repository.EventoPostRepository
import com.example.activacorp.model.EventoPost
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventoPostViewModel: ViewModel() {

    private val repository = EventoPostRepository()

    private val _postList = MutableStateFlow<List<EventoPost>>(emptyList())

    val postList: StateFlow<List<EventoPost>> = _postList

    init {
        fetchPost()
    }

    private fun fetchPost() {
        viewModelScope.launch {
            try {
                val json = repository.getEventosPosts() // Trae los datos a travez del repositorio
                val documents = json.getAsJsonArray("documents") // Extrae los datos

                _postList.value = documents?.map { element -> // Recorre cada documento y lo convierte en un objeto
                    val fields = element.asJsonObject.getAsJsonObject("fields")
                    EventoPost(
                        nombre = fields?.getAsJsonObject("nombre")?.get("stringValue")?.asString ?: "",
                        tipo = fields?.getAsJsonObject("tipo")?.get("stringValue")?.asString ?: "",
                        horario = fields?.getAsJsonObject("horario")?.get("stringValue")?.asString ?: "",
                        fechaInicio = fields?.getAsJsonObject("fechaInicio")?.get("stringValue")?.asString ?: "",
                        fechaTermino = fields?.getAsJsonObject("fechaTermino")?.get("stringValue")?.asString ?: "",
                        direccion = fields?.getAsJsonObject("direccion")?.get("stringValue")?.asString ?: "",
                        comuna = fields?.getAsJsonObject("comuna")?.get("integerValue")?.asLong ?: 0L,
                        asistentes = fields?.getAsJsonObject("asistentes")?.get("integerValue")?.asInt ?: 0,
                        costosEstimados = fields?.getAsJsonObject("costosEstimados")?.get("integerValue")?.asInt ?: 0,
                        requisitos = fields?.getAsJsonObject("requisitos")?.get("stringValue")?.asString ?: "",
                        observaciones = fields?.getAsJsonObject("observaciones")?.get("stringValue")?.asString ?: "",
                        organizacion = fields?.getAsJsonObject("organizacion")?.get("integerValue")?.asLong ?: 0L
                    )
                } ?: emptyList()

            } catch (e: Exception) {
                println("Error al consumir la api: ${e.localizedMessage}")
            }
        }
    }
}