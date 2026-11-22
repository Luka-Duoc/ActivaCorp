package com.example.activacorp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.activacorp.data.repository.OportunidadRepository
import com.example.activacorp.data.repository.ResultadoRepository
import com.example.activacorp.data.repository.UsuarioRepository
import com.example.activacorp.model.SeguimientoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SeguimientoViewModel(
    private val oportunidadRepository: OportunidadRepository,
    private val resultadoRepository: ResultadoRepository,
    private val usuarioRepository: UsuarioRepository,
) : ViewModel() {
    private val _estado = MutableStateFlow(SeguimientoUiState())
    val estado: StateFlow<SeguimientoUiState> = _estado

}