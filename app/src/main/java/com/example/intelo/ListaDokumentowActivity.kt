package com.example.intelo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.intelo.ui.theme.InteloTheme
import com.example.simpleintelo.DodajDokumentActivity
import com.example.simpleintelo.DodajKontrahentaActivity
import androidx.compose.runtime.mutableStateListOf
import com.example.simpleintelo.EdycjaDokumentuActivity
import com.example.simpleintelo.EdycjaKontrahentaActivity


class ListaDokumentowActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InteloTheme {
                AppNavHost()
            }
        }
    }
}

val przykladoweDokumenty = mutableStateListOf(
    DokumentPrzyjecia(1, "2024-01-01", "SYMBOL1", "Kontrahent A", mutableStateListOf(
        PozycjaDokumentu("Samochód", "kg", 1048.0),
        PozycjaDokumentu("Rower", "kg", 16.0))),
    DokumentPrzyjecia(2, "2024-01-02", "SYMBOL2", "Kontrahent B", mutableStateListOf(
        PozycjaDokumentu("Komputer", "kg", 8.0),
        PozycjaDokumentu("Monitor", "kg", 4.0))),
    DokumentPrzyjecia(3, "2000-09-05", "SYMBOL3", "Kontrahent C", mutableStateListOf(
        PozycjaDokumentu("Książka", "kg", 1.0),
        PozycjaDokumentu("Zeszyt", "kg", 0.5)))
)

fun getDokumentById(dokumentId: String?): DokumentPrzyjecia? {
    return przykladoweDokumenty.find { it.id.toString() == dokumentId }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppNavHost() {
    Column(modifier = Modifier.padding(16.dp)) {
        val context = LocalContext.current
        Przycisk(text = "Dodaj dokument", onClick = { navigateToDodajDokument(context) })
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "listaDokumentow") {
            composable("listaDokumentow") {
                ListaDokumentowPrzyjec(przykladoweDokumenty){dokument ->
                    navController.navigate("szczegolyDokumentu/${dokument.id}")}
            }
            composable("szczegolyDokumentu/{dokumentId}") { backStackEntry ->
                SzczegolyDokumentuScreen(dokumentId = backStackEntry.arguments?.getString("dokumentId"), navController = navController)
            }
        }
    }
}



@Composable
fun SzczegolyDokumentuScreen(dokumentId: String?, navController: NavController) {
    val context = LocalContext.current
    val dokument = getDokumentById(dokumentId)

    Column(modifier = Modifier.padding(16.dp)) {
        Przycisk(text = "Edytuj dokument", onClick = {
            dokumentId?.toIntOrNull()?.let { id ->
                navigateToEdycjaDokumentu(context, id)
            }
        })
        Text("Szczegóły dokumentu ID: $dokumentId", style = MaterialTheme.typography.headlineMedium)

        if (dokument != null) {
            Text("Kontrahent: ${dokument.kontrahent}", style = MaterialTheme.typography.headlineSmall)
            Text("Symbol: ${dokument.symbol}", style = MaterialTheme.typography.headlineSmall)
            Text("Data: ${dokument.data}", style = MaterialTheme.typography.headlineSmall)
        }
        dokument?.pozycje?.forEach { pozycja ->
            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                Text("Nazwa towaru: ${pozycja.nazwaTowaru}", style = MaterialTheme.typography.bodyLarge)
                Text("Jednostka miary: ${pozycja.jednostkaMiary}", style = MaterialTheme.typography.bodyLarge)
                Text("Ilość: ${pozycja.ilosc}", style = MaterialTheme.typography.bodyLarge)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { navController.navigateUp() }) {
            Text("Powrót")
        }
    }
}

fun navigateToEdycjaDokumentu(context: Context, dokumentId: Int) {
    val intent = Intent(context, EdycjaDokumentuActivity::class.java).apply {
        putExtra("dokumentId", dokumentId)
    }
    context.startActivity(intent)
}

@Composable
fun ListaDokumentowPrzyjec(dokumenty: List<DokumentPrzyjecia>, onDokumentClick: (DokumentPrzyjecia) -> Unit) {
    LazyColumn {
        items(dokumenty) { dokument ->
            ElementListyDokumentu(dokument, onDokumentClick)
        }
    }
}

@Composable
fun ElementListyDokumentu(dokument: DokumentPrzyjecia, onDokumentClick: (DokumentPrzyjecia) -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onDokumentClick(dokument) }
        .padding(16.dp)) {
        Text("${dokument.data} ${dokument.symbol} ${dokument.kontrahent}", style = MaterialTheme.typography.bodyLarge)
    }
}

fun navigateToDodajDokument(context: Context) {
    try {
        val intent = Intent(context, DodajDokumentActivity::class.java)
        context.startActivity(intent)
    } catch (e: Exception) {
        Log.e("NavigationError", "Problem z uruchomieniem DokumentyActivity", e)
    }
}


data class DokumentPrzyjecia(
    val id: Int,
    var data: String,
    var symbol: String,
    var kontrahent: String,
    var pozycje: MutableList<PozycjaDokumentu>
)

data class PozycjaDokumentu(
    var nazwaTowaru: String,
    var jednostkaMiary: String,
    var ilosc: Double
)

@Composable
fun DokumentyPreview() {
    InteloTheme {
        ListaDokumentowPrzyjec(przykladoweDokumenty) {
            println("Wybrano dokument: ${it.id}")
        }
    }
}
