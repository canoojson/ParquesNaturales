package com.example.parquesnaturales.ui.pantallas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.parquesnaturales.R
import com.example.parquesnaturales.modelo.EspecieRoom

@Composable
fun PantallaActualizarEspecieRoom(
    especie: EspecieRoom,
    onEspecieActualizada: (EspecieRoom) -> Unit,
    modifier: Modifier = Modifier
){
    var nombre by remember { mutableStateOf(especie.nombre) }
    var tipo by remember { mutableStateOf(especie.tipo) }
    var anotaciones by remember { mutableStateOf(especie.anotaciones) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Spacer(Modifier.height(16.dp))

        TextField(
            value = nombre,
            onValueChange = {
                if(it.all { it.isLetter() || it == ' '}){
                    nombre = it
                }
            },
            label = { Text(stringResource(id = R.string.nombre))},
            enabled = false
        )

        Spacer(Modifier.height(16.dp))

        TextField(
            value = tipo,
            onValueChange = {
                if(it.all { it.isLetter()|| it == ' ' }){
                    tipo = it
                }
            },
            label = { Text(stringResource(id = R.string.tipo))},
            enabled = false
        )

        Spacer(Modifier.height(16.dp))

        TextField(
            value = anotaciones,
            onValueChange = {
                if(it.all { it.isLetter()|| it == ' ' }){
                    anotaciones = it
                }
            },
            label = { Text(stringResource(id = R.string.anotaciones))},
            modifier = Modifier.width(300.dp).height(100.dp)
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                val e = EspecieRoom(id = especie.id, nombre = nombre, tipo = tipo, anotaciones = anotaciones)
                onEspecieActualizada(e)
            }
        ) {
            Text(stringResource(id = R.string.actualizar))
        }
    }
}