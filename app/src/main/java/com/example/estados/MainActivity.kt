package com.example.estados

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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

@Composable
fun RatingBarSimple(
    titulo: String,
    rating: Int,
    onRatingChange: (Int) -> Unit
) {
    val etiquetas = listOf("Malo", "Regular", "Bueno", "Muy bueno", "Excelente")
    val texto = if (rating in 1..5) etiquetas[rating - 1] else ""

    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = titulo, color = Color.Gray, fontSize = 14.sp)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                for (i in 1..5) {
                    Icon(
                        imageVector = if (i <= rating) Icons.Filled.Star else Icons.Outlined.StarBorder,
                        contentDescription = null,
                        tint = if (i <= rating) Color(0xFFFFB800) else Color.Gray,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable { onRatingChange(i) }
                    )
                }
            }
            Text(text = texto, color = Color(0xFF6750A4), fontSize = 14.sp)
        }
    }
}

@Composable
fun practica() {
    var comida by remember { mutableStateOf(0) }
    var servicio by remember { mutableStateOf(0) }
    var ambiente by remember { mutableStateOf(0) }
    var comentario by remember { mutableStateOf("") }
    var publicada by remember { mutableStateOf(false) }
    var etiquetasSeleccionadas by remember { mutableStateOf(setOf<String>()) }
    if (publicada) {
        val promedio = (comida + servicio + ambiente) / 3.0
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text("comentario")
            Box(modifier = Modifier.background(Color.LightGray)) {
                Text(comentario)
            }
            Text("Etiquetas seleccionadas:", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                etiquetasSeleccionadas.forEach { etiqueta ->
                    Text(
                        text = "• $etiqueta",
                        fontSize = 14.sp,
                        color = Color(0xFF6750A4)
                    )
                }
            }
        }
    } else {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                "Escribe tu reseña",
                fontSize = 30.sp,
                modifier = Modifier.padding(vertical = 15.dp)
            )
            Text(
                "Restaurante El mezquite",
                fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 10.dp)
            )
            Text("Califica la experiencia", fontSize = 15.sp)

            RatingBarSimple("Comida", rating = comida, onRatingChange = { comida = it })
            RatingBarSimple("Servicio", rating = servicio, onRatingChange = { servicio = it })
            RatingBarSimple("Ambiente", rating = ambiente, onRatingChange = { ambiente = it })
            OutlinedTextField(
                value = comentario,
                onValueChange = { comentario = it },
                label = { Text("Comentarios") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            ) {
                listOf("Familiar", "Económico", "Rápido").forEach { etiqueta ->
                    val estaSeleccionada = etiquetasSeleccionadas.contains(etiqueta)
                    FilterChip(
                        selected = estaSeleccionada,
                        onClick = {
                            etiquetasSeleccionadas =
                                if (estaSeleccionada) etiquetasSeleccionadas - etiqueta else etiquetasSeleccionadas + etiqueta
                        },
                        label = { Text(etiqueta) },
                        leadingIcon = if (estaSeleccionada) {
                            { Icon(imageVector = Icons.Default.Check, contentDescription = null) }
                        } else null
                    )
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Romántico", "Vegetariano").forEach { etiqueta ->
                    val estaSeleccionada = etiquetasSeleccionadas.contains(etiqueta)
                    FilterChip(
                        selected = estaSeleccionada,
                        onClick = {
                            etiquetasSeleccionadas =
                                if (estaSeleccionada) etiquetasSeleccionadas - etiqueta else etiquetasSeleccionadas + etiqueta
                        },
                        label = { Text(etiqueta) },
                        leadingIcon = if (estaSeleccionada) {
                            { Icon(imageVector = Icons.Default.Check, contentDescription = null) }
                        } else null
                    )
                }
            }
            val calificacionesValidas = comida > 0 && servicio > 0 && ambiente > 0
            val comentarioValido = comentario.length >= 20

            Button(
                onClick = { publicada = true },
                enabled = calificacionesValidas && comentarioValido,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Publicar")
            }
        } // Column principal
    }
}
@Preview(showBackground = true)
@Composable
fun ver() {
    practica()
}