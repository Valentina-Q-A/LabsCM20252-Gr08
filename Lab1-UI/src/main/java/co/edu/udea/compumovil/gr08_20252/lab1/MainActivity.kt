package co.edu.udea.compumovil.gr08_20252.lab1

import ContactDataScreen
import PersonalDataScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import co.edu.udea.compumovil.gr08_20252.lab1.ui.theme.MLabsCM20252Gr08Theme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MLabsCM20252Gr08Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    AppContent()
                }
            }
        }
    }
}

@Composable
fun AppContent() {
    val appState = rememberAppState()
    
    when (appState.currentScreen) {
        Screen.PERSONAL_DATA -> {
            PersonalDataScreen(
                userData = appState.userData,
                onDataChange = appState.onUserDataChange,
                onNextClick = {
                    appState.onScreenChange(Screen.CONTACT_DATA)
                }
            )
        }
        Screen.CONTACT_DATA -> {
            ContactDataScreen(
                userData = appState.userData,
                onDataChange = appState.onUserDataChange,
                onSaveClick = {
                    appState.onScreenChange(Screen.SUMMARY)
                }
            )
        }
        Screen.SUMMARY -> {
            SummaryScreen(
                userData = appState.userData,
                onEditPersonalData = {
                    appState.onScreenChange(Screen.PERSONAL_DATA)
                },
                onEditContactData = {
                    appState.onScreenChange(Screen.CONTACT_DATA)
                },
                onSaveData = {
                    appState.onSaveData()
                    // Aquí podrías mostrar un mensaje de éxito o navegar a otra pantalla
                }
            )
        }
    }
}

