package com.example.parquesnaturales.ui

import android.app.Application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.parquesnaturales.ParquesNaturalesAplicacion
import com.example.parquesnaturales.datos.EspecieRoomRepositorio
import kotlinx.coroutines.launch

class AjustesViewModel(private val especieRoomRepositorio: EspecieRoomRepositorio, app: Application) : ViewModel() {

    fun vaciarDB(){
        viewModelScope.launch {
            especieRoomRepositorio.vaciarDB()
        }
    }

    companion object {
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val aplicacion = (this[APPLICATION_KEY] as ParquesNaturalesAplicacion)
                val especieRoomRepositorio = aplicacion.contenedor.especieRoomRepositorio
                AjustesViewModel(especieRoomRepositorio = especieRoomRepositorio, app = aplicacion)
            }
        }
    }
}