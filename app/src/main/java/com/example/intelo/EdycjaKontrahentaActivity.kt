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

class EdycjaKontrahentaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val kontrahentId = intent.getIntExtra("kontrahentId", -1)

        val kontrahentDoEdycji = listaKontrahentow.find { it.id == kontrahentId }

        setContent {
            InteloTheme {
                if (kontrahentDoEdycji != null) {
                    EdycjaKontrahentaForm(kontrahentDoEdycji)
                } else {
                    Text("Kontrahent nie został znaleziony.")                }
            }
        }
    }
}



@Composable
fun EdycjaKontrahentaForm(kontrahent: Kontrahent) {
    val nazwa = remember { mutableStateOf(kontrahent.nazwa) }
    val adres = remember { mutableStateOf(kontrahent.adres) }
    val telefon = remember { mutableStateOf(kontrahent.telefon) }
    val context = LocalContext.current

    Column(modifier = Modifier.padding(PaddingValues(16.dp))) {
        Text(text = "Edytuj kontrahenta", style = MaterialTheme.typography.headlineMedium)
        OutlinedTextField(value = nazwa.value, onValueChange = { nazwa.value = it }, label = { Text("Nazwa") })
        OutlinedTextField(value = adres.value, onValueChange = { adres.value = it }, label = { Text("Adres") })
        OutlinedTextField(value = telefon.value, onValueChange = { telefon.value = it }, label = { Text("Telefon") })

        Button(onClick = {
            val index = listaKontrahentow.indexOfFirst { it.id == kontrahent.id }
            if (index != -1) {
                listaKontrahentow[index] = kontrahent.copy(nazwa = nazwa.value, adres = adres.value, telefon = telefon.value)
            }
            (context as? Activity)?.finish()
        }) {
            Text("Zapisz zmiany")
        }
    }
}


