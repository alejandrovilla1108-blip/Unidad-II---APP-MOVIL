package com.example.estados

import android.R
import android.R.attr.enabled
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.estados.ui.theme.EstadosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            practica()
        }
    }
}
data class Tarea(
    val nombre: String,
    var prioridad: String,
    var completada: Boolean = false
)
@Composable
fun practica(){
    var nuevaTarea by remember { mutableStateOf("") }
    val tareas = remember { mutableStateListOf<Tarea>() }
    var prioridadSeleccionada by remember {mutableStateOf("Alta")}
    var filtroSeleccionado by remember { mutableStateOf("Todas") }
    Column(modifier = Modifier.fillMaxSize().padding(20.dp, 20.dp, 20.dp)) {
        Text("Mis pendientes", fontSize = 35.sp)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedTextField(value = nuevaTarea, onValueChange ={nuevaTarea = it}, label = {Text("Nueva tarea")}, modifier = Modifier.weight(1f))
            Button(onClick = {if (nuevaTarea.isNotBlank()){tareas.add(Tarea(nuevaTarea, prioridadSeleccionada))
            nuevaTarea = ""} }) {Text("+", fontSize = 35.sp)}
        } //Row de Outline
        Row(modifier = Modifier.padding(vertical = 5.dp).fillMaxWidth(0.750f), horizontalArrangement = Arrangement.SpaceBetween) {
            FilterChip(
                selected = prioridadSeleccionada == "Alta",
                onClick = {prioridadSeleccionada = "Alta"},
                label = {Text("Alta")}
            )
            FilterChip(
                selected = prioridadSeleccionada == "Media",
                onClick = {prioridadSeleccionada = "Media"},
                label = {Text("Media")}
            )
            FilterChip(
                selected = prioridadSeleccionada == "Baja",
                onClick = {prioridadSeleccionada = "Baja"},
                label = {Text("Baja")}
            )
        } //ROw de prioridades
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            FilterChip(
                selected = filtroSeleccionado == "Todas",
                onClick = {filtroSeleccionado = "Todas"},
                label = {Text("Todas")}
            )
            FilterChip(
                selected = filtroSeleccionado == "Pendientes",
                onClick = {filtroSeleccionado = "Pendientes"},
                label = {Text("Pendientes")}
            )
            FilterChip(
                selected = filtroSeleccionado == "Hechas",
                onClick = {filtroSeleccionado = "Hechas"},
                label = {Text("Hechas")}
            )
        } // Prioridades
        val tareasFiltradas = tareas.filter {
            when (filtroSeleccionado){
                "Pendientes" -> !it.completada
                "Hechas" -> it.completada
                else -> true
            }
        }
        LazyColumn (modifier = Modifier.weight(1f)){
            items(tareasFiltradas) {
                Row(modifier = Modifier.fillMaxWidth().padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = it.completada, onCheckedChange = { marcada -> val index = tareas.indexOf(it)
                        tareas[index] = it.copy(completada = marcada) })
                    Column() {
                        Text(text = it.nombre, fontSize = 25.sp)
                        Text(text = it.prioridad, fontSize = 15.sp)
                    }
                }
            }
        } // LazyColumn
        val completadas = tareas.count() {it.completada}
        val total = tareas.size
        HorizontalDivider(color = Color.LightGray)
        Text(
            text = "$completadas de $total completadas",
            fontSize = 18.sp
        )
        Button(onClick ={tareas.removeAll{it.completada}}, modifier = Modifier.fillMaxWidth()) {Text("Borrar completadas")}

    } //Column principal
}

@Preview(showBackground = true)
@Composable
fun ver() {
    practica()
}