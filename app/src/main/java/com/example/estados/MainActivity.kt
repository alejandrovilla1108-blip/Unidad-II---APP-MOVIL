package com.example.estados

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            practica()
        }
    }
}
class Gasto(var concepto: String, var monto: Double, var categoria: String)
@Composable
fun practica() {
    var ingresoTexto by remember { mutableStateOf("") }
    val listaGastos = remember { mutableStateListOf<Gasto>() }
    var conceptoInput by remember { mutableStateOf("") }
    var montoInput by remember { mutableStateOf("") }
    var categoriaInput by remember { mutableStateOf("") }

    val ingreso = ingresoTexto.toDoubleOrNull() ?: 0.0
    val totalGastado = listaGastos.sumOf { it.monto }
    val saldo = ingreso - totalGastado
    val porcentaje = if (ingreso > 0) (totalGastado / ingreso) * 100 else 0.0
    val mayorCategoria = listaGastos.groupBy { it.categoria }.maxByOrNull { grupo -> grupo.value.sumOf { it.monto } }?.key ?: "Ninguna"

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Mi presupuesto", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = ingresoTexto, onValueChange = { ingresoTexto = it }, label = { Text("Ingreso mensual") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column { Text("Gastado"); Text("$$totalGastado") }
                    Column { Text("Saldo"); Text("$$saldo") }
                }
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(progress = { (porcentaje / 100).toFloat().coerceIn(0f, 1f) }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(4.dp))
                Text("${porcentaje.toInt()}% usado - Mayor gasto: $mayorCategoria")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        if (porcentaje >= 100) {
            Text("¡Alerta! Excediste el presupuesto por $${-saldo}", color = MaterialTheme.colorScheme.error)
        } else if (porcentaje >= 80) {
            Text("Cuidado: pasaste el 80%", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Nuevo gasto", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = conceptoInput, onValueChange = { conceptoInput = it }, label = { Text("Concepto") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(value = montoInput, onValueChange = { montoInput = it }, label = { Text("Monto") }, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(value = categoriaInput, onValueChange = { categoriaInput = it }, label = { Text("Categoría") }, modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(8.dp))
        val esValido = conceptoInput.isNotBlank() && (montoInput.toDoubleOrNull() ?: 0.0) > 0.0

        Button(
            onClick = {
                if (esValido) {
                    listaGastos.add(Gasto(concepto = conceptoInput, monto = montoInput.toDouble(), categoria = categoriaInput))
                    conceptoInput = ""
                    montoInput = ""
                }
            },
            enabled = esValido,
            modifier = Modifier.fillMaxWidth()
        ) { Text("Agregar gasto") }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Gastos recientes", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(listaGastos) { item ->
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text(item.concepto)
                        Text(item.categoria, color = Color.Gray)
                    }
                    Row {
                        Text("-$${item.monto}")
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = { listaGastos.remove(item) }) { Text("X") }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ver() { practica() }