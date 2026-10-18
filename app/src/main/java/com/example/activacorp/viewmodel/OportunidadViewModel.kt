package com.example.activacorp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.activacorp.data.local.AppDatabase

class OportunidadViewModel(app: Application): AndroidViewModel(app) {
    private val dao = AppDatabase.getDatabase(app).OportunidadDao()
}