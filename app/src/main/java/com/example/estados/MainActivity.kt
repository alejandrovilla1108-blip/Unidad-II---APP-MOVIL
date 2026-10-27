package com.example.estados

import android.R.attr.enabled
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
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
            lecturaScreen()
        }
    }
}

@Composable
fun lecturaScreen() {
    var tamaño by remember { mutableStateOf(14f) }

    Column(modifier = Modifier.fillMaxSize().padding(28.dp)) {
        Text(text = "Ajuste de lectura", fontSize = 30.sp)
        Text("Tamaño ${tamaño.toInt()} sp")
        Slider(value = tamaño,
            onValueChange = {tamaño = it},
            valueRange = 14f..32f)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("A", fontSize = 14.sp)
            Text("A", fontSize = 32.sp)
        }
        Text("Interlineado")
        var interlineado by remember { mutableFloatStateOf(1.3f) }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = interlineado == 1.0f, onClick = { interlineado = 1.0f })
            Text("Compacto")

            RadioButton(selected = interlineado == 1.3f, onClick = { interlineado = 1.3f })
            Text("Normal")

            RadioButton(selected = interlineado == 1.6f, onClick = { interlineado = 1.6f })
            Text("Amplio")
        }
        var modoOscuro by remember { mutableStateOf(false) }
        Row( modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Modo oscuro")
            Switch(checked = modoOscuro, onCheckedChange = {modoOscuro = it})
        }
        var usaSerif by remember { mutableStateOf(false) }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Fuente")
            FilterChip( selected = usaSerif,
                onClick = {usaSerif = !usaSerif},
                label = { Text(if (usaSerif) "Serif" else "Sans") })
        }
        Text("Vista previa")
        Box(modifier = Modifier.background(if (modoOscuro) Color.DarkGray else Color.White, RoundedCornerShape(8.dp)).width(300.dp).height(200.dp)){
            Text("En una noche tranquila en el pueblo cuando las campanas comenzaron a sonar, y nadie supo por que", fontSize = tamaño.sp, fontFamily = if (usaSerif) FontFamily.Serif else FontFamily.SansSerif, lineHeight = (tamaño * interlineado).sp, )
        }
        Button(onClick = { tamaño = 18f
            interlineado = 1.3f
            modoOscuro = false
            usaSerif = false
        }) {
            Text("Restablecer ajustes")
        }
    }
}

@Preview (showBackground = true)
@Composable
fun ver() {
    lecturaScreen()
}