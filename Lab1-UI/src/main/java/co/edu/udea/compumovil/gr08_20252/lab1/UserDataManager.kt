package co.edu.udea.compumovil.gr08_20252.lab1

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// Manager para manejar el estado global de los datos del usuario
class UserDataManager(private val context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
    private val gson = Gson()
    
    // Guardar datos del usuario
    fun saveUserData(userData: UserData) {
        val json = gson.toJson(userData)
        prefs.edit().putString("user_data", json).apply()
    }
    
    // Cargar datos del usuario
    fun loadUserData(): UserData {
        val json = prefs.getString("user_data", null)
        return if (json != null) {
            try {
                gson.fromJson(json, UserData::class.java)
            } catch (e: Exception) {
                UserData() // Retorna datos vacíos si hay error
            }
        } else {
            UserData()
        }
    }
    
    // Limpiar datos del usuario
    fun clearUserData() {
        prefs.edit().clear().apply()
    }
    
    // Verificar si hay datos guardados
    fun hasUserData(): Boolean {
        return prefs.contains("user_data")
    }
}

// Composable para manejar el estado global
@Composable
fun rememberUserDataManager(): UserDataManager {
    val context = LocalContext.current
    return remember { UserDataManager(context) }
}

// Composable para manejar el estado de la aplicación
@Composable
fun rememberAppState(): AppState {
    val userDataManager = rememberUserDataManager()
    var currentUserData by remember { mutableStateOf(userDataManager.loadUserData()) }
    var currentScreen by remember { mutableStateOf(Screen.PERSONAL_DATA) }
    
    return AppState(
        userData = currentUserData,
        currentScreen = currentScreen,
        onUserDataChange = { newData ->
            currentUserData = newData
        },
        onScreenChange = { screen ->
            currentScreen = screen
        },
        onSaveData = {
            userDataManager.saveUserData(currentUserData)
        },
        onLoadData = {
            currentUserData = userDataManager.loadUserData()
        }
    )
}

// Estado de la aplicación
data class AppState(
    val userData: UserData,
    val currentScreen: Screen,
    val onUserDataChange: (UserData) -> Unit,
    val onScreenChange: (Screen) -> Unit,
    val onSaveData: () -> Unit,
    val onLoadData: () -> Unit
)

// Enum para las pantallas
enum class Screen {
    PERSONAL_DATA,
    CONTACT_DATA,
    SUMMARY
}
