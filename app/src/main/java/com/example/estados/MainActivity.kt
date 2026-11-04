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
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.estados.ui.theme.EstadosTheme
import java.nio.file.WatchEvent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            tienda()
        }
    }
}

@Composable
fun tienda(){
    var monto by remember { mutableStateOf("") }
    var propina by remember { mutableStateOf("") }
    var otroPorcentaje by remember { mutableStateOf("")}
    var personas by remember { mutableStateOf(1) }
    var totalFinal = monto.toInt() + propina.toInt() + otroPorcentaje.toInt()
    var redondeo by remember { mutableStateOf(false) }
    val total = if (redondeo) totalFinal.toInt() + 1 else totalFinal
    var montoPersonal = totalFinal / personas
    Column(modifier = Modifier.fillMaxSize().padding(10.dp)) {
        Text("Cuenta dividida", fontSize = 40.sp)
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        OutlinedTextField(
            value = monto,
            onValueChange = {monto = it },
            label = { Text("Monto de la cuenta") }
        )
    } // Row del monto
    Row(modifier = Modifier.fillMaxWidth().padding(1.dp, 10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        FilterChip(
            selected = propina == "10",
            onClick = { propina = "10"},
            label = {Text("10%")}
        )
        FilterChip(
            selected = propina == "15",
            onClick = { propina = "15"},
            label = {Text("15%")}
        )
        FilterChip(
            selected = propina == "20",
            onClick = { propina = "20"},
            label = {Text("20%")}
        )
        FilterChip(
            selected = propina == "Otro",
            onClick = { propina = "Otro"},
            label = {Text("Otro")}
        )
    }
        if (propina == "Otro") {
            Row(modifier = Modifier.fillMaxWidth()) {
                Slider(value = otroPorcentaje.toFloatOrNull() ?: 0f,
                    onValueChange = {otroPorcentaje = it.toInt().toString()},
                    valueRange = 0f..30f,
                    steps = 30
                    )
            } //Row de slider
            Text("Propina : $otroPorcentaje %")
        } //if de Otro
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Personas")
            Button(onClick = {if (personas > 1){personas = personas - 1}}, modifier = Modifier.padding(5.dp)) { Text("-")}
            Box(modifier = Modifier){
                Text("$personas")
            }
            Button(onClick = {personas = personas + 1}, modifier = Modifier.padding(5.dp)) { Text("+")}
        } //Row personas
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Redondear por persona")
            Switch(checked = redondeo,
                onCheckedChange = {redondeo = it})
        }// Row redondear
        Spacer(modifier = Modifier.height(10.dp))
        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(16.dp).background(color = Color.LightGray, RoundedCornerShape(18.dp))) {
            Row(modifier = Modifier.fillMaxWidth().padding(0.dp, 20.dp, 10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Propina($propina)", fontSize = 25.sp)
                Text("${if (propina == "Otro") otroPorcentaje else propina}", fontSize = 25.sp)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Total con propina : ", fontSize = 25.sp)
                Text("$totalFinal", fontSize = 25.sp)
            }
            Column(modifier = Modifier.fillMaxWidth().padding(0.dp, 20.dp, 10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Cada quin paga:")
                Text("$montoPersonal", fontSize = 100.sp, color = Color.Green )
                Text("Entre $personas personas")
            }
        }
    } // Column principal
} // Funtion

@Preview(showBackground = true)
@Composable
fun ver() {
    tienda()
}