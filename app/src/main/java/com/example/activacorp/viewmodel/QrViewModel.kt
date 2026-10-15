package com.example.activacorp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.activacorp.data.repository.QrRepository
import com.example.activacorp.model.QrResult

class QrViewModel : ViewModel() {
    private val repository = QrRepository()

    private val _qrResult = MutableLiveData<QrResult?>()
    val qrResult: LiveData<QrResult?> = _qrResult

    fun onQrDetected(content: String) {
        _qrResult.value = repository.processQrContent(content)
    }

    fun clearResult() {
        _qrResult.value = null
    }
}