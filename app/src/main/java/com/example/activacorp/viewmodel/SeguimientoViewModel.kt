package com.example.activacorp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.activacorp.data.repository.OportunidadRepository
import com.example.activacorp.data.repository.ResultadoRepository
import com.example.activacorp.data.repository.SeguimientoRepository
import com.example.activacorp.data.repository.UsuarioRepository
import com.example.activacorp.model.SeguimientoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SeguimientoViewModel(
    private val seguimientoRepository: SeguimientoRepository
) : ViewModel() {
    private val _estado = MutableStateFlow(SeguimientoUiState())
    val estado: StateFlow<SeguimientoUiState> = _estado

    init {
        cargarSeguimientos()
    }

    fun cargarSeguimientos() {
        viewModelScope.launch {
            seguimientoRepository.obtenerSeguimientos().collect { lista -> _estado.update {
                it.copy(seguimientos = lista)
            } }
        }
    }


}