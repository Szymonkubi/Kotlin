package com.example.intelo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.intelo.ui.theme.InteloTheme
import com.example.simpleintelo.KontrahenciActivity


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InteloTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting("Użytkowniku")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    val context = LocalContext.current
    Column {
        Text(text = "Witaj$name!")
        Text(text = "Wybierz gdzie chcesz przejsc:")
        Przycisk(text = "Lista Dokumentow", onClick = { navigateToDokumentActivity(context) })
        Przycisk(text = "Kontrahent", onClick = { navigateToKontrahentActivity(context) })
    }
}

fun navigateToDokumentActivity(context: Context) {
    try {
        val intent = Intent(context, ListaDokumentowActivity::class.java)
        context.startActivity(intent)
    } catch (e: Exception) {
        Log.e("NavigationError", "Problem z uruchomieniem DokumentyActivity", e)
    }
}

fun navigateToKontrahentActivity(context: Context) {
    try {
        val intent = Intent(context, KontrahenciActivity::class.java)
        context.startActivity(intent)
    } catch (e: Exception) {
        Log.e("NavigationError", "Problem z uruchomieniem KontrahenciActivity", e)
    }
}

@Composable
fun Przycisk(text: String = "Kliknij", onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text = text)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    InteloTheme {
        Greeting(", tutaj Szymon Kubik")
    }
}
