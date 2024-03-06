package com.example.simpleintelo

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.intelo.ui.theme.InteloTheme

class DodajKontrahentaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InteloTheme {
                DodajKontrahentaForm()
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DodajKontrahentaForm() {
    val nazwa = remember { mutableStateOf("") }
    val adres = remember { mutableStateOf("") }
    val telefon = remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(modifier = Modifier.padding(PaddingValues(16.dp))) {
        Text(text = "Dodaj nowego kontrahenta", style = MaterialTheme.typography.headlineMedium)
        OutlinedTextField(
            value = nazwa.value,
            onValueChange = { nazwa.value = it },
            label = { Text("Nazwa") }
        )
        OutlinedTextField(
            value = adres.value,
            onValueChange = { adres.value = it },
            label = { Text("Adres") }
        )
        OutlinedTextField(
            value = telefon.value,
            onValueChange = { telefon.value = it },
            label = { Text("Telefon") }
        )
        Button(onClick = {
            val nowyKontrahent = Kontrahent(listaKontrahentow.size + 1, nazwa.value, adres.value, telefon.value)
            listaKontrahentow.add(nowyKontrahent)
            (context as? Activity)?.finish()
        }) {
            Text("Zapisz")
        }
    }
}
