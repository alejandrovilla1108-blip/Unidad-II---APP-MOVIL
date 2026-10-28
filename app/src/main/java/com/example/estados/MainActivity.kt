package com.example.estados

import android.R
import android.R.attr.enabled
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
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
            tienda()
        }
    }
}

    @Composable
    fun tienda(){
        var tortillasPrecio = 24.00
        var lechePrecio = 27.50
        var HuevoPrecio = 52.00
        var FrijolPrecio = 38.00
        var tortillasCant by remember { mutableStateOf(0) }
        var lecheCant by remember { mutableStateOf(0) }
        var huevoCant by remember { mutableStateOf(0) }
        var frijolCant by remember { mutableStateOf(0) }
        var confirmado by remember { mutableStateOf(false) }
        var totalTort = tortillasPrecio * tortillasCant
        var totalLeche = lecheCant * lechePrecio
        var totalHuevo = huevoCant * HuevoPrecio
        var totalFrijoles = frijolCant * FrijolPrecio
        var subTotal = totalFrijoles + totalHuevo + totalLeche + totalTort
        var descuento by remember { mutableStateOf(0.0) }
        var envio by remember { mutableStateOf(0.0) }
        fun vaciar(){
            tortillasCant = 0
            lecheCant = 0
            huevoCant = 0
            frijolCant = 0
            descuento = 0.0
            confirmado = false
        }
        Column(modifier = Modifier.fillMaxSize().fillMaxWidth().padding(16.dp)) {
            Text("Tienda Don Pepe", fontSize = 35.sp)
            Column(modifier = Modifier.padding(20.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("tortillas 1kg", fontSize = 18.sp)
                    Button(onClick = {if (tortillasCant < 10){tortillasCant++} }) {Text("+")}
                    Box(modifier = Modifier.size(34.dp).background(Color.White, CircleShape).border(1.dp, color = Color.Black, shape = CircleShape) ,contentAlignment = Alignment.Center) {
                        Text(text = tortillasCant.toString())
                    }
                    Button(onClick = {if (tortillasCant > 0){tortillasCant--} }) {Text("-")}

                    }
                Text("$24.00 c/u", color = Color.LightGray, fontSize = 14.sp)
                } //Column tortillas
            HorizontalDivider(color = Color.LightGray)
            Column(modifier = Modifier.padding(20.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Leche 1lt", fontSize = 18.sp)
                    Button(onClick = {if ( lecheCant< 10){lecheCant++} }) {Text("+")}
                    Box(modifier = Modifier.size(34.dp).background(Color.White, CircleShape).border(1.dp, color = Color.Black, shape = CircleShape) ,contentAlignment = Alignment.Center) {
                        Text(text = lecheCant.toString())
                    }
                    Button(onClick = {if (lecheCant > 0){lecheCant--} }) {Text("-")}

                    }
                Text("$27.50", color = Color.LightGray, fontSize = 14.sp)
                } //Column leche
            HorizontalDivider(color = Color.LightGray)
            Column(modifier = Modifier.padding(20.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Huevo 12pz", fontSize = 18.sp)
                    Button(onClick = {if ( huevoCant< 10){huevoCant++} }) {Text("+")}
                    Box(modifier = Modifier.size(34.dp).background(Color.White, CircleShape).border(1.dp, color = Color.Black, shape = CircleShape) ,contentAlignment = Alignment.Center) {
                        Text(text = huevoCant.toString())
                    }
                    Button(onClick = {if (huevoCant > 0){huevoCant--} }) {Text("-")}

                }
                Text("$52.00", color = Color.LightGray, fontSize = 14.sp)
            } //Column huevo
            HorizontalDivider(color = Color.LightGray)
            Column(modifier = Modifier.padding(20.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Frijol 1 kg", fontSize = 18.sp)
                    Button(onClick = {if ( frijolCant< 10){frijolCant++} }) {Text("+")}
                    Box(modifier = Modifier.size(34.dp).background(Color.White, CircleShape).border(1.dp, color = Color.Black, shape = CircleShape) ,contentAlignment = Alignment.Center) {
                        Text(text = frijolCant.toString())
                    }
                    Button(onClick = {if (frijolCant > 0){frijolCant--} }) {Text("-")}

                }
                Text("$38.00", color = Color.LightGray, fontSize = 14.sp)
            } //Column leche
            HorizontalDivider(color = Color.LightGray)
            if(subTotal >= 200.00 ){
                descuento = subTotal * 0.10
            }
            if (subTotal >= 150.00) {
                envio = 0.0
            } else {
                envio = 25.0
            }
            var totalFinal = subTotal - descuento + envio
            var articulos = tortillasCant + lecheCant + huevoCant + frijolCant
            Column(modifier = Modifier.fillMaxWidth().padding(5.dp ,10.dp).background(color = Color.LightGray, RoundedCornerShape(10.dp))) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Articulos : ", fontSize = 20.sp)
                    Text("$articulos", fontSize = 20.sp)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Subtotal : ", fontSize = 20.sp)
                    Text("$subTotal", fontSize = 20.sp)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Descuento : ", fontSize = 20.sp)
                    Text("$descuento", fontSize = 20.sp)
                }
                Row(modifier = Modifier.fillMaxWidth().padding(0.dp, 0.dp, 0.dp, 20.dp ), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Envio : ", fontSize = 20.sp)
                    Text(text = if (subTotal >= 150) "GRATIS" else "25.00", fontSize = 20.sp )
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Total: ", fontSize = 40.sp, color = Color.Green)
                    Text("$totalFinal", fontSize = 40.sp, color = Color.Green)
                }
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Button(onClick = {vaciar()}, modifier = Modifier) { Text("Vaciar", color = Color.Black)}
                Button(onClick = {if(articulos > 0){confirmado = true} } ,modifier = Modifier.background(color = Color.White)) { Text("Confirmar pedido", color = Color.Black)}
            } //Row botones de abajo
            if (confirmado){
                Text("Pedido confirmado")
            }
            } // Column principal
        }

@Preview(showBackground = true)
@Composable
fun ver() {
    tienda()
}