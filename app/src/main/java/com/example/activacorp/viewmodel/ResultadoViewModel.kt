package com.example.activacorp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.activacorp.data.repository.ResultadoRepository
import com.example.activacorp.model.ResultadoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ResultadoViewModel(resultadoRepository: ResultadoRepository): ViewModel() {
    private val _estado = MutableStateFlow(ResultadoUiState())
    val estado: StateFlow<ResultadoUiState> = _estado
}