package com.example.parquesnaturales.ui

import android.util.Log
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
import com.example.parquesnaturales.datos.ParqueRepositorio
import com.example.parquesnaturales.modelo.ParqueNatural
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface ParqueUIState {
    data class ObtenerExito(val parque:List<ParqueNatural>): ParqueUIState
    data class CrearExito(val parque: ParqueNatural): ParqueUIState
    data class ActualizarExito(val parque: ParqueNatural): ParqueUIState
    data class EliminarExito(val id: Int): ParqueUIState

    object Error: ParqueUIState
    object Cargando: ParqueUIState
}

class ParqueNaturalViewModel(private val parqueRepositorio: ParqueRepositorio): ViewModel() {
    var parqueUIState: ParqueUIState by mutableStateOf(ParqueUIState.Cargando)
        private set

    var parquePulsado: ParqueNatural by mutableStateOf(ParqueNatural(nombre = "", extension = 0.0))
        private set

    fun actualizarParquePulsado(parque: ParqueNatural){
        parquePulsado = parque
    }

    init {
        obtenerParques()
    }

    fun obtenerParques(){
        viewModelScope.launch {
            parqueUIState = ParqueUIState.Cargando
            parqueUIState = try {
               val listaParques = parqueRepositorio.obtenerParques()
                ParqueUIState.ObtenerExito(listaParques)
            } catch (e: IOException){
                Log.v("Error", e.toString())
                ParqueUIState.Error
            } catch (e: HttpException){
                Log.v("Error", e.toString())
                ParqueUIState.Error
            }
        }
    }

    fun insertarParque(parque: ParqueNatural){
        viewModelScope.launch {
            parqueUIState = ParqueUIState.Cargando
            parqueUIState = try {
                val parqueInsertado = parqueRepositorio.insertarParque(parque)
                ParqueUIState.CrearExito(parqueInsertado)
            } catch (e: IOException){
                ParqueUIState.Error
            } catch (e: HttpException){
                ParqueUIState.Error
            }
        }
    }

    fun actualizarParque(id: Int, parque: ParqueNatural){
        viewModelScope.launch {
            parqueUIState = ParqueUIState.Cargando
            parqueUIState = try {
                val parqueActualizado = parqueRepositorio.actualizarParque(id, parque)
                ParqueUIState.ActualizarExito(parqueActualizado)
            }catch (e: IOException){
                ParqueUIState.Error
            } catch (e: HttpException){
                ParqueUIState.Error
            }
        }
    }

    fun eliminarParque(id: Int){
        viewModelScope.launch {
            parqueUIState = ParqueUIState.Cargando
            parqueUIState = try{
                parqueRepositorio.eliminarParque(id)
                ParqueUIState.EliminarExito(id)
            }catch (e: IOException){
                ParqueUIState.Error
            } catch (e: HttpException){
                ParqueUIState.Error
            }
        }
    }

    companion object {
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val aplicacion = (this[APPLICATION_KEY] as ParquesNaturalesAplicacion)
                val parqueRepositorio = aplicacion.contenedor.parqueRepositorio
                ParqueNaturalViewModel(parqueRepositorio = parqueRepositorio)
            }
        }
    }
}
