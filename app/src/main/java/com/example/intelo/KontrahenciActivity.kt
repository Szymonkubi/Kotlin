package com.example.simpleintelo

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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.intelo.ListaDokumentowActivity
import com.example.intelo.navigateToDokumentActivity
import com.example.intelo.ui.theme.InteloTheme

class KontrahenciActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InteloTheme {
                ListaKontrahentowScreen()
            }
        }
    }
}

data class Kontrahent(
    val id: Int,
    var nazwa: String,
    var adres: String,
    var telefon: String
)

val listaKontrahentow = mutableListOf(
    Kontrahent(1, "Kontrahent A", "Adres A", "123-456-789"),
    Kontrahent(2, "Kontrahent B", "Adres B", "987-654-321"),
    Kontrahent(3, "Kontrahent C", "Adres C", "456-123-789")
)

@Composable
fun ListaKontrahentowScreen() {
    val context = LocalContext.current

    val navigateToEdycjaKontrahenta = { kontrahentId: Int ->
        val intent = Intent(context, EdycjaKontrahentaActivity::class.java).apply {
            putExtra("kontrahentId", kontrahentId)
        }
        context.startActivity(intent)
    }

    Column {
        Przycisk(text = "Dodaj kontrahenta", onClick = { navigateToDodajKontrahenta(context) })
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(listaKontrahentow) { kontrahent ->
                KontrahentItem(kontrahent = kontrahent, onKontrahentClick = {
                    navigateToEdycjaKontrahenta(kontrahent.id)
                })
            }
        }
    }
}


@Composable
fun KontrahentItem(kontrahent: Kontrahent, onKontrahentClick: (Kontrahent) -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .clickable { onKontrahentClick(kontrahent) }
    ) {
        Text(
            text = kontrahent.nazwa,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Adres: ${kontrahent.adres}",
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = "Telefon: ${kontrahent.telefon}",
            style = MaterialTheme.typography.bodySmall
        )
        Divider(modifier = Modifier.padding(vertical = 8.dp))

    }

}

@Composable
fun Przycisk(text: String = "Kliknij", onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text = text)
    }
}

fun navigateToDodajKontrahenta(context: Context) {
    try {
        val intent = Intent(context, DodajKontrahentaActivity::class.java)
        context.startActivity(intent)
    } catch (e: Exception) {
        Log.e("NavigationError", "Problem z uruchomieniem DokumentyActivity", e)
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    InteloTheme {
        ListaKontrahentowScreen()
    }
}
