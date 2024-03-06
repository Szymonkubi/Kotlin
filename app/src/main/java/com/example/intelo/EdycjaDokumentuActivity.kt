package com.example.simpleintelo

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.intelo.DokumentPrzyjecia
import com.example.intelo.PozycjaDokumentu
import com.example.intelo.przykladoweDokumenty
import com.example.intelo.ui.theme.InteloTheme
import androidx.compose.runtime.mutableStateOf


class EdycjaDokumentuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dokumentId = intent.getIntExtra("dokumentId", -1)

        val dokumentDoEdycji = przykladoweDokumenty.find { it.id == dokumentId }

        setContent {
            InteloTheme {
                if (dokumentDoEdycji != null) {
                    EdycjaDokumentuForm(dokumentDoEdycji)
                } else {
                    Text("Dokument nie został znaleziony.")                }
            }
        }
    }
}



@Composable
fun EdycjaDokumentuForm(dokument: DokumentPrzyjecia) {
    val data = remember { mutableStateOf(dokument.data) }
    val symbol = remember { mutableStateOf(dokument.symbol) }
    val kontrahent = remember { mutableStateOf(dokument.kontrahent) }
    val pozycje = remember { mutableStateListOf(*dokument.pozycje.toTypedArray()) }
    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(value = data.value, onValueChange = { data.value = it }, label = { Text("Data") })
        OutlinedTextField(value = symbol.value, onValueChange = { symbol.value = it }, label = { Text("Symbol") })
        OutlinedTextField(value = kontrahent.value, onValueChange = { kontrahent.value = it }, label = { Text("Kontrahent") })

        pozycje.forEachIndexed { index, pozycja ->
            Text("Pozycja ${index + 1}", style = MaterialTheme.typography.bodyLarge)
            OutlinedTextField(
                value = pozycja.nazwaTowaru,
                onValueChange = { updatedValue -> pozycje[index] = pozycja.copy(nazwaTowaru = updatedValue) },
                label = { Text("Nazwa Towaru") }
            )
            OutlinedTextField(
                value = pozycja.jednostkaMiary,
                onValueChange = { updatedValue -> pozycje[index] = pozycja.copy(jednostkaMiary = updatedValue) },
                label = { Text("Jednostka Miary") }
            )
            OutlinedTextField(
                value = pozycja.ilosc.toString(),
                onValueChange = { updatedValue ->
                    val ilosc = updatedValue.toDoubleOrNull() ?: pozycja.ilosc
                    pozycje[index] = pozycja.copy(ilosc = ilosc)
                },
                label = { Text("Ilość") }
            )
        }
        Button(onClick = {
            dokument.data = data.value
            dokument.symbol = symbol.value
            dokument.kontrahent = kontrahent.value
            dokument.pozycje = pozycje.toMutableList()
            val index = przykladoweDokumenty.indexOfFirst { it.id == dokument.id }
            if (index != -1) {
                przykladoweDokumenty[index] = dokument
            }
            (context as? Activity)?.finish()
        }) {
            Text("Zapisz zmiany")
        }
    }
}


