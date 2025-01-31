package com.example.parquesnaturales.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.parquesnaturales.ParquesNaturalesAplicacion
import com.example.parquesnaturales.datos.EspecieRepositorio
import com.example.parquesnaturales.modelo.Especie
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

sealed interface EspecieUIState {
    data class ObtenerExito(val especie: List<Especie>): EspecieUIState
    data class CrearExito(val especie: Especie): EspecieUIState
    data class ActualizarExito(val especie: Response<Especie>): EspecieUIState
    data class EliminarExito(val id: Int): EspecieUIState

    object Error: EspecieUIState
    object Cargando: EspecieUIState
}

class EspecieViewModel(private val especieRepositorio: EspecieRepositorio): ViewModel() {
    var especieUIState: EspecieUIState by mutableStateOf(EspecieUIState.Cargando)
        private set

    var especiePulsada: Especie by mutableStateOf(Especie(nombre = "", tipo = "", descripcion = ""))
        private set

    fun actualizarEspeciePulsada(parque: Especie){
        especiePulsada = parque
    }

    init {
        obtenerEspecies()
    }

    fun obtenerEspecies(){
        viewModelScope.launch {
            especieUIState = EspecieUIState.Cargando
            especieUIState = try {
                val listaEspecies = especieRepositorio.obtenerEspecies()
                EspecieUIState.ObtenerExito(listaEspecies)
            } catch (e: IOException){
                EspecieUIState.Error
            } catch (e: HttpException){
                EspecieUIState.Error
            }
        }
    }

    fun insertarEspecie(especie: Especie){
        viewModelScope.launch {
            especieUIState = EspecieUIState.Cargando
            especieUIState = try {
                val especieInsertada = especieRepositorio.insertarEspecie(especie)
                EspecieUIState.CrearExito(especieInsertada)
            } catch (e: IOException){
                EspecieUIState.Error
            } catch (e: HttpException){
                EspecieUIState.Error
            }
        }
    }

    fun actualizarEspecie(id: Int, especie: Especie){
        viewModelScope.launch {
            especieUIState = EspecieUIState.Cargando
            especieUIState = try {
                val especieActualizada = especieRepositorio.actualizarEspecie(id, especie)
                EspecieUIState.ActualizarExito(especieActualizada)
            }catch (e: IOException){
                EspecieUIState.Error
            } catch (e: HttpException){
                EspecieUIState.Error
            }
        }
    }

    fun eliminarEspecie(id: Int){
        viewModelScope.launch {
            especieUIState = EspecieUIState.Cargando
            especieUIState = try{
                especieRepositorio.eliminarEspecie(id)
                EspecieUIState.EliminarExito(id)
            }catch (e: IOException){
                EspecieUIState.Error
            } catch (e: HttpException){
                EspecieUIState.Error
            }
        }
    }

    companion object {
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val aplicacion = (this[APPLICATION_KEY] as ParquesNaturalesAplicacion)
                val especieRepositorio = aplicacion.contenedor.especieRepositorio
                EspecieViewModel(especieRepositorio = especieRepositorio)
            }
        }
    }
}
