package com.example.parquesnaturales.ui.pantallas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import com.example.parquesnaturales.modelo.Especie
import com.example.parquesnaturales.modelo.ParqueNatural

@Composable
fun PantallaInsertarEspecie(
    onInsertarPulsado:(Especie) -> Unit,
    modifier: Modifier = Modifier
){
    var nombre by remember { mutableStateOf("")}
    var descripcion by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))

        TextField(
            value = nombre,
            onValueChange = {
                if(it.all { it.isLetter()|| it == ' '  }){
                    nombre = it
                }
            },
            label = { Text(stringResource(id = R.string.nombre))}
        )

        Spacer(Modifier.height(16.dp))

        TextField(
            value = tipo,
            onValueChange = {
                if(it.all { it.isLetter()|| it == ' '  }){
                    tipo = it
                }
            },
            label = { Text(stringResource(id = R.string.tipo))}
        )

        Spacer(Modifier.height(16.dp))

        TextField(
            value = descripcion,
            onValueChange = {
                if(it.all { it.isLetter() || it == ' ' }){
                    descripcion = it
                }
            },
            label = { Text(stringResource(id = R.string.descripcion))}
        )

        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            val especie = Especie(nombre = nombre, tipo = tipo, descripcion = descripcion)
            onInsertarPulsado(especie)
        }) {
            Text(stringResource(id = R.string.insertar))
        }
    }
}