package com.example.estados

import android.R.attr.enabled
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
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
            incripcionesScreen()
        }
    }
}

@Composable
fun incripcionesScreen() {
    var nombre by remember { mutableStateOf("") }
    val nombreValido = nombre.trim().length >= 3
    var correo by remember { mutableStateOf("") }
    val correoValido = correo.contains("@") && correo.contains(".")
    var edad by remember { mutableStateOf("") }
    val edadNumero = edad.toIntOrNull()
    val edadValida = edadNumero != null && edadNumero in 12..99
    var taller by remember { mutableStateOf("Fotografia") }
    var expandido by remember { mutableStateOf(false) }
    var intentoEnvio by remember { mutableStateOf(false) }
    var privacidad by remember { mutableStateOf(false) }
    var inscrito by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(10.dp, 50.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        if (inscrito) {
            Text("Te has inscrito correctamente", fontSize = 25.sp, color = Color.Green)
            Text("Resumen de tu inscripción", fontSize = 20.sp)
            Text("Nombre: $nombre")
            Text("Correo: $correo")
            Text("Edad: $edad")
            Text("Taller: $taller")
            Button(onClick = { nombre = ""; correo = ""; edad = ""; taller = "Fotografia"; privacidad = false; intentoEnvio = false; inscrito = false }, modifier = Modifier.fillMaxWidth()) { Text("Nueva inscripción") }
        } else {
            Text("Incripciones a talleres", fontSize = 30.sp)
            Text("Casa de lago       •cupo limitado")
            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre completo") }, modifier = Modifier.fillMaxWidth())
            if (intentoEnvio && !nombreValido) { Text("Nombre no valido") }
            OutlinedTextField(value = correo, onValueChange = { correo = it }, label = { Text("Correo") }, modifier = Modifier.fillMaxWidth())
            if (intentoEnvio && !correoValido) { Text("Correo no valido") }
            OutlinedTextField(value = edad, onValueChange = { edad = it }, label = { Text("Edad") }, modifier = Modifier.fillMaxWidth())
            if (intentoEnvio && !edadValida) { Text("Edad no valida") }
            Box {
                Text(text = "$taller", modifier = Modifier.clickable { expandido = !expandido })
                DropdownMenu(expanded = expandido, onDismissRequest = { expandido = false }) {
                    DropdownMenuItem(text = { Text("Fotografia") }, onClick = { taller = "Fotografia"; expandido = false })
                    DropdownMenuItem(text = { Text("Pintura") }, onClick = { taller = "Pintura"; expandido = false })
                    DropdownMenuItem(text = { Text("Guitarra") }, onClick = { taller = "Guitarra"; expandido = false })
                }
            }
            Row {
                Checkbox(checked = privacidad, onCheckedChange = { privacidad = it })
                Text("Acepto avsio de privacidad")
            }
            if (intentoEnvio && !privacidad) { Text("Acepta los terminos de privacidad para continuar") }
            Button(onClick = { intentoEnvio = true; if (edadValida && privacidad && nombreValido && correoValido) { inscrito = true } }, modifier = Modifier.fillMaxWidth()) { Text("Inscribirme") }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ver() {
    incripcionesScreen()
}