package com.example.parquesnaturales.ui.pantallas

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.parquesnaturales.R
import com.example.parquesnaturales.modelo.ParqueNatural
import com.example.parquesnaturales.ui.ParqueUIState

@Composable
fun PantallaInicioParques(
    appUIState: ParqueUIState,
    onParquesObtenidos: ()-> Unit,
    onParquePulsado: (parque: ParqueNatural) -> Unit,
    onParqueEliminado: (id: Int) -> Unit,
    modifier: Modifier = Modifier
){
    when(appUIState){
        is ParqueUIState.Cargando -> PantallaCargando(modifier= modifier.fillMaxWidth())
        is ParqueUIState.Error -> PantallaError(modifier = modifier.fillMaxWidth())
        is ParqueUIState.ObtenerExito -> PantallaParquesNaturales(
            lista = appUIState.parques,
            onParquePulsado = onParquePulsado,
            onParqueEliminado = onParqueEliminado,
            modifier= modifier.fillMaxWidth()
        )
        is ParqueUIState.CrearExito -> onParquesObtenidos()
        is ParqueUIState.ActualizarExito -> onParquesObtenidos()
        is ParqueUIState.EliminarExito -> onParquesObtenidos()
    }

}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PantallaParquesNaturales(
    lista: List<ParqueNatural>,
    onParquePulsado: (parque: ParqueNatural) -> Unit,
    onParqueEliminado: (id: Int) -> Unit,
    modifier: Modifier = Modifier
){
    LazyColumn(modifier= modifier){
        items(lista){ parque->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .combinedClickable(
                        onClick = {onParquePulsado(parque)},
                        onLongClick = {onParqueEliminado(parque.id)}
                    )
                    .padding(16.dp)
            ){
                Column(
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text(
                        text = stringResource(R.string.nombre)+ ":" + parque.nombre,
                    )
                    Text(
                        text = stringResource(R.string.extension)+ ":" + parque.extension.toString()
                    )
                }
            }
        }
    }
}