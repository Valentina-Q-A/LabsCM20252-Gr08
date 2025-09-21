package co.edu.udea.compumovil.gr08_20252.lab1

import ContactDataScreen
import PersonalDataScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import co.edu.udea.compumovil.gr08_20252.lab1.ui.theme.MLabsCM20252Gr08Theme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Forzar orientación portrait
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        
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
    val viewModel: UserDataViewModel = viewModel()
    
    when (viewModel.currentScreen) {
        Screen.PERSONAL_DATA -> {
            PersonalDataScreen(
                userData = viewModel.userData,
                onDataChange = { newData ->
                    viewModel.updateUserData(newData)
                },
                onNextClick = {
                    viewModel.navigateToNextScreen()
                }
            )
        }
        Screen.CONTACT_DATA -> {
            ContactDataScreen(
                userData = viewModel.userData,
                onDataChange = { newData ->
                    viewModel.updateUserData(newData)
                },
                onSaveClick = {
                    viewModel.navigateToNextScreen()
                }
            )
        }
        Screen.SUMMARY -> {
            SummaryScreen(
                userData = viewModel.userData,
                onEditPersonalData = {
                    viewModel.navigateToScreen(Screen.PERSONAL_DATA)
                },
                onEditContactData = {
                    viewModel.navigateToScreen(Screen.CONTACT_DATA)
                },
                onSaveData = {
                    // Loggear todos los datos finales
                    viewModel.logAllData()
                }
            )
        }
    }
}

