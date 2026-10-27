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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
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
            aforoScreen()
        }
    }

    @Composable
    fun aforoScreen() {
        var actuales by remember { mutableStateOf(0) }
        var visitasDelDia by remember { mutableStateOf(0) }
        val porcentaje = actuales / 50f
        val colorAforo = when {
            porcentaje < 0.70f -> Color(0xFF2E7D32)
            porcentaje < 1.0f -> Color(0xFFFFA000)
            else -> Color(0xFFC62828)
        }

        Column(modifier = Modifier.fillMaxSize().padding(10.dp)) {
            Text("Biblioteca Central", fontSize = 25.sp)
            Text("Sala de estudio • capacidad 50", fontSize = 14.sp)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray)
                    .border(2.dp, Color.DarkGray, RoundedCornerShape(8.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("$actuales / 50", fontSize = 50.sp, color = colorAforo)
                Text("personas en la sala", textAlign = TextAlign.Center)
                LinearProgressIndicator(
                    progress = { porcentaje },
                    modifier = Modifier.fillMaxWidth(),
                    color = colorAforo
                )
                Text("${(porcentaje * 100).toInt()} % de ocupación", color = colorAforo)
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        if (actuales < 50) {
                            actuales = actuales + 1
                            visitasDelDia = visitasDelDia + 1
                        }
                    },
                    enabled = actuales < 50,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Entra")
                }

                Button(
                    onClick = {
                        if (actuales > 0) {
                            actuales = actuales - 1
                        }
                    },
                    enabled = actuales > 0,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Sale", color = Color.White)
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth().padding(8.dp).clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF1EAF7)).padding(12.dp)
            ) {
                Text("Visitas del día: $visitasDelDia", fontSize = 20.sp)
            }


            Button(
                onClick = {
                    actuales = 0
                    visitasDelDia = 0
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar día")
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun ver() {
        aforoScreen()
    }
}
