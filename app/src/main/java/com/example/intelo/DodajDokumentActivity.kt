package com.example.simpleintelo

import android.app.Activity
import android.graphics.drawable.Icon
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.intelo.ui.theme.InteloTheme
import com.example.intelo.DokumentPrzyjecia
import com.example.intelo.PozycjaDokumentu
import com.example.intelo.przykladoweDokumenty
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp



class DodajDokumentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InteloTheme {
                DodajDokumentForm()
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DodajDokumentForm() {
    val data = remember { mutableStateOf("") }
    val symbol = remember { mutableStateOf("") }
    val kontrahent = remember { mutableStateOf("") }
    val nazwaTowaru= remember { mutableStateOf("") }
    val jednostkaMiary= remember { mutableStateOf("") }
    val ilosc= remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(PaddingValues(16.dp))) {
        Text(text = "Dodaj nowy dokument", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = data.value,
            onValueChange = { data.value = it },
            label = { Text("Data") }
        )
        OutlinedTextField(
            value = symbol.value,
            onValueChange = { symbol.value = it },
            label = { Text("Symbol") }
        )
        OutlinedTextField(
            value = kontrahent.value,
            onValueChange = { kontrahent.value = it },
            label = { Text("Kontrahent") }
        )

        OutlinedTextField(
            value = nazwaTowaru.value,
            onValueChange = { nazwaTowaru.value = it },
            label = { Text("Nazwa Towaru") }
        )
        OutlinedTextField(
            value = jednostkaMiary.value,
            onValueChange = { jednostkaMiary.value = it },
            label = { Text("Jednostka miary") }
        )
        OutlinedTextField(
            value = ilosc.value,
            onValueChange = { ilosc.value = it },
            label = { Text("Ilość (0.0)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        val context = LocalContext.current
        Button(onClick = {
            val iloscDouble = ilosc.value.toDoubleOrNull() ?: 0.0
            val nowyDokument = DokumentPrzyjecia(
                id = (przykladoweDokumenty.maxByOrNull { it.id }?.id ?: 0) + 1,
                data = data.value,
                symbol = symbol.value,
                kontrahent = kontrahent.value,
                pozycje = mutableListOf(PozycjaDokumentu(nazwaTowaru.value, jednostkaMiary.value, iloscDouble))  // Możesz tutaj dodać logikę do obsługi pozycji
            )
            Log.d("DodajDokument", "Przed dodaniem, rozmiar listy: ${przykladoweDokumenty.size}")
            przykladoweDokumenty.add(nowyDokument)
            Log.d("DodajDokument", "Po dodaniu, rozmiar listy: ${przykladoweDokumenty.size}")

            (context as? Activity)?.finish()
        }) {
            Text("Dodaj")
        }
    }
}
