package com.example.activacorp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.activacorp.data.repository.ResultadoRepository
import com.example.activacorp.model.uiStates.ResultadoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ResultadoViewModel(private val resultadoRepository: ResultadoRepository): ViewModel() {
    private val _estado = MutableStateFlow(ResultadoUiState())
    val estado: StateFlow<ResultadoUiState> = _estado

    init {
        obtenerResultados()
    }

    fun obtenerResultados() {
        viewModelScope.launch {
            resultadoRepository.obtenerResultados().collect { lista -> _estado.update {
                it.copy(resultados = lista)
            } }
        }
    }

}