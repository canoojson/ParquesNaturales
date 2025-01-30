package com.example.parquesnaturales.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.parquesnaturales.R
import com.example.parquesnaturales.modelo.ParqueNatural

@Composable
fun PantallaActualizarParque(
    parque: ParqueNatural,
    onActualizarPulsado:(ParqueNatural) -> Unit,
    modifier: Modifier = Modifier
){
    var nombre by remember { mutableStateOf(parque.nombre)}
    var extensionTexto by remember { mutableStateOf(parque.extension.toString()) }
    var extension by remember { mutableStateOf(parque.extension) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(Modifier.height(16.dp))

        TextField(
            value = nombre,
            onValueChange = {nombre = it},
            label = { Text(stringResource(id = R.string.nombre))}
        )

        Spacer(Modifier.height(16.dp))

        TextField(
            value = extensionTexto,
            onValueChange = {
                extensionTexto = it
                extension = it.toDoubleOrNull() ?: 0.0  // Si está vacío, deja la extensión sin cambios
            },
            label = { Text(stringResource(id = R.string.extension)) }
        )
        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            val parque = ParqueNatural(nombre = nombre, extension = extension)
            onActualizarPulsado(parque)
        }) {
            Text(stringResource(id = R.string.actualizar))
        }
    }
}