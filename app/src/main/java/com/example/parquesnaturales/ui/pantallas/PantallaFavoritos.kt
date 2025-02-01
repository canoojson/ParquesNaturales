package com.example.parquesnaturales.ui.pantallas

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.parquesnaturales.modelo.EspecieRoom
import com.example.parquesnaturales.ui.EspecieRoomUIState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text

@Composable
fun PantallaFavInicio(
    appUIState: EspecieRoomUIState,
    onEspeciesObtenidas: () -> Unit,
    onEspeciePulsada: (Int) -> Unit,
    onEspecieEliminada: (EspecieRoom) -> Unit,
    modifier: Modifier = Modifier
){
    when(appUIState){
        is EspecieRoomUIState.Cargando -> PantallaCargando(modifier= modifier.fillMaxSize())
        is EspecieRoomUIState.Error -> PantallaError(modifier= modifier.fillMaxSize())
        is EspecieRoomUIState.ObtenerExitoTodos -> PantallaFavoritos(
            lista = appUIState.especies,
            onEspeciePulsada = onEspeciePulsada,
            onEspecieEliminada = onEspecieEliminada,
            modifier = modifier.fillMaxWidth()
        )
        is EspecieRoomUIState.CrearExito -> onEspeciesObtenidas()
        is EspecieRoomUIState.ObtenerExito -> onEspeciesObtenidas()
        is EspecieRoomUIState.ActualizarExito -> onEspeciesObtenidas()
        is EspecieRoomUIState.EliminarExito -> onEspeciesObtenidas()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PantallaFavoritos(
    lista: List<EspecieRoom>,
    onEspeciePulsada: (Int) -> Unit,
    onEspecieEliminada: (EspecieRoom) -> Unit,
    modifier: Modifier = Modifier
){
    LazyColumn(modifier = modifier){
        items(lista){ especie ->
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .combinedClickable(
                    onClick = {onEspeciePulsada(especie.id)},
                    onLongClick = {onEspecieEliminada(especie)}
                )
            ){
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = especie.nombre
                    )
                    Text(
                        text = especie.tipo
                    )
                    Text(
                        text = especie.anotaciones
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}