package com.example.parquesnaturales.ui.pantallas

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.parquesnaturales.R
import com.example.parquesnaturales.modelo.Especie
import com.example.parquesnaturales.modelo.EspecieRoom
import com.example.parquesnaturales.ui.EspecieUIState

@Composable
fun PantallaInicioEspecies(
    appUIState: EspecieUIState,
    onEspeciesObtenidas: ()-> Unit,
    onEspeciePulsada: (especie: Especie) -> Unit,
    onEspecieEliminada: (id: Int) -> Unit,
    onEspecieGuardada: (especie: EspecieRoom) -> Unit,
    modifier: Modifier = Modifier
){
    when(appUIState){
        is EspecieUIState.Cargando -> PantallaCargando(modifier= modifier.fillMaxWidth())
        is EspecieUIState.Error -> PantallaError(modifier = modifier.fillMaxWidth())
        is EspecieUIState.ObtenerExito -> PantallaEspecies(
            lista = appUIState.especie,
            onEspeciePulsada = onEspeciePulsada,
            onEspecieEliminada = onEspecieEliminada,
            onEspecieGuardada = onEspecieGuardada,
            modifier= modifier.fillMaxWidth()
        )
        is EspecieUIState.CrearExito -> onEspeciesObtenidas()
        is EspecieUIState.ActualizarExito -> onEspeciesObtenidas()
        is EspecieUIState.EliminarExito -> onEspeciesObtenidas()
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PantallaEspecies(
    lista: List<Especie>,
    onEspeciePulsada: (especie: Especie) -> Unit,
    onEspecieEliminada: (id: Int) -> Unit,
    onEspecieGuardada: (especie: EspecieRoom) -> Unit,
    modifier: Modifier = Modifier
){
    LazyColumn(modifier= modifier){
        items(lista){ especie->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .combinedClickable(
                        onClick = {onEspeciePulsada(especie)},
                        onLongClick = {onEspecieEliminada(especie.id)},
                        onDoubleClick = {
                            val especieRoom = EspecieRoom(nombre = especie.nombre, tipo = especie.tipo!!, anotaciones = "")
                            onEspecieGuardada(especieRoom)
                        }
                    )
                    .padding(16.dp)
            ){
                Column(
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text(
                        text = stringResource(R.string.nombre) + ":"+ especie.nombre,
                    )
                    Text(
                        text = stringResource(R.string.tipo)+ ":" + especie.tipo
                    )
                    Text(
                        text = stringResource(R.string.descripcion) + ":" + especie.descripcion
                    )
                }
            }
        }
    }
}