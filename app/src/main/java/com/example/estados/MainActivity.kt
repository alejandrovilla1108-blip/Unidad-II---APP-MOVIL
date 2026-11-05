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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
            practica()
        }
    }
}

@Composable
fun practica() {
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var intentos by remember { mutableStateOf(0) }
    var show by remember { mutableStateOf(false) }
    var login by remember { mutableStateOf(false) }
    var recordar by remember { mutableStateOf(false) }
    val correoIngreso = "clinicaVida@gmail.com"
    val passwordIngreso = "COntraseña123"
    if (login) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "¡Bienvenido!", fontSize = 30.sp)
            Text(text = correo, fontSize = 18.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                login = false
                correo = ""
                password = ""
                intentos = 0
            }) {
                Text("Cerrar sesión")
            }
        }
    } else {
        Column(modifier = Modifier.padding(5.dp).fillMaxSize()) {
            Text(
                text = "Clinica Vida",
                fontSize = 50.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo electronico") })
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = if (show)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { show = !show }) {
                        Icon(
                            imageVector = if (show) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                }
            ) //OutLine del password
            Row(modifier = Modifier.fillMaxWidth()) {
                Checkbox(checked = recordar, onCheckedChange = { recordar = it })
                Text("Recordarme")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (correoIngreso == correo && passwordIngreso == password) {
                    login = true
                } else intentos++
            }, enabled = intentos < 3, modifier = Modifier.fillMaxWidth()) { Text("Entrar") }
            if (intentos > 0 && intentos < 3 && !login) {
                Text(
                    text = "Corro y/o contraseñas incorrectos (te quedan $intentos de 3)",
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }
            if (intentos >= 3) {
                Text(
                    text = "Has superado el límite de 3 intentos fallidos. Intenta más tarde.",
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }
        } //Column principal
    }
}
@Preview(showBackground = true)
@Composable
fun ver() {
    practica()
}