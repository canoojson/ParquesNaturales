package com.example.parquesnaturales.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.parquesnaturales.ParquesNaturalesAplicacion
import com.example.parquesnaturales.datos.EspecieRoomRepositorio
import com.example.parquesnaturales.modelo.EspecieRoom
import kotlinx.coroutines.launch

sealed interface EspecieRoomUIState{
    data class ObtenerExitoTodos(val especies: List<EspecieRoom>): EspecieRoomUIState
    data class ObtenerExito(val especie: EspecieRoom): EspecieRoomUIState

    object CrearExito: EspecieRoomUIState
    object ActualizarExito: EspecieRoomUIState
    object EliminarExito: EspecieRoomUIState
    object Error: EspecieRoomUIState
    object Cargando: EspecieRoomUIState
}

class EspecieRoomViewModel(private val especieRoomRepositorio: EspecieRoomRepositorio): ViewModel() {

    var especieRoomUIState: EspecieRoomUIState by mutableStateOf(EspecieRoomUIState.Cargando)
        private set

    var especiePulsada: EspecieRoom by mutableStateOf(EspecieRoom(0, "", "", ""))
        private set

    init {
        obtenerEspecies()
    }

    fun obtenerEspecies(){
        viewModelScope.launch {
            especieRoomUIState = try {
                val lista = especieRoomRepositorio.obtenerTodasEspecies()
                EspecieRoomUIState.ObtenerExitoTodos(lista)
            }catch (e: Exception){
                Log.v("Error", e.message.toString())
                EspecieRoomUIState.Error
            }
        }
    }

    fun insertarEspecie(especie: EspecieRoom){
        viewModelScope.launch {
            especieRoomUIState = try {
                especieRoomRepositorio.insertar(especie)
                EspecieRoomUIState.CrearExito
            }catch (e: Exception){
                Log.v("Error", e.message.toString())
                EspecieRoomUIState.Error
            }
        }
    }

    fun obtenerEspecie(id: Int){
        viewModelScope.launch {
            especieRoomUIState = try {
                val especie = especieRoomRepositorio.obtenerEspecie(id)
                especiePulsada = especie
                EspecieRoomUIState.ObtenerExito(especie)
            }catch (e: Exception){
                Log.v("Error", e.message.toString())
                EspecieRoomUIState.Error
            }
        }
    }

    fun actualizarEspecie(especie: EspecieRoom){
        viewModelScope.launch {
            especieRoomUIState = try {
                especieRoomRepositorio.actualizar(especie)
                EspecieRoomUIState.ActualizarExito
            }catch (e: Exception){
                Log.v("Error", e.message.toString())
                EspecieRoomUIState.Error
            }
        }
    }

    fun eliminarEspecie(especie: EspecieRoom){
        viewModelScope.launch {
            especieRoomUIState = try {
                especieRoomRepositorio.eliminar(especie)
                EspecieRoomUIState.EliminarExito
            }catch (e: Exception) {
                Log.v("Error", e.message.toString())
                EspecieRoomUIState.Error
            }
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ParquesNaturalesAplicacion)
                val especieRoomRepositorio = application.contenedor.especieRoomRepositorio
                EspecieRoomViewModel(especieRoomRepositorio)
            }
        }
    }
}