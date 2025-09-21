package co.edu.udea.compumovil.gr08_20252.lab1

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class UserDataViewModel : ViewModel() {
    
    // Estado de los datos del usuario
    var userData by mutableStateOf(UserData())
        private set
    
    // Estado de la pantalla actual
    var currentScreen by mutableStateOf(Screen.PERSONAL_DATA)
        private set
    
    // Función para actualizar los datos del usuario
    fun updateUserData(newData: UserData) {
        userData = newData
    }

    // Función para navegar a la siguiente pantalla
    fun navigateToNextScreen() {
        when (currentScreen) {
            Screen.PERSONAL_DATA -> {
                if (validatePersonalData()) {
                    logPersonalData()
                    currentScreen = Screen.CONTACT_DATA
                }
            }
            Screen.CONTACT_DATA -> {
                if (validateContactData()) {
                    logContactData()
                    currentScreen = Screen.SUMMARY
                }
            }
            Screen.SUMMARY -> {
                // Ya estamos en la pantalla final
            }
        }
    }
    
    // Función para validar datos personales
    private fun validatePersonalData(): Boolean {
        val isValid = userData.firstName.isNotBlank() && 
                     userData.lastName.isNotBlank() && 
                     userData.birthDate.isNotBlank()
        
        if (!isValid) {
            Log.w("UserDataViewModel", "Datos personales incompletos")
        }
        
        return isValid
    }
    
    // Función para validar datos de contacto
    private fun validateContactData(): Boolean {
        val isValid = userData.phone.isNotBlank() && 
                     userData.email.isNotBlank() && 
                     userData.country.isNotBlank()
        
        if (!isValid) {
            Log.w("UserDataViewModel", "Datos de contacto incompletos")
        }
        
        return isValid
    }
    
    // Función para loggear datos personales
    private fun logPersonalData() {
        Log.d("UserDataViewModel", "INFORMACIÓN PERSONAL")
        Log.d("UserDataViewModel", "${userData.firstName} ${userData.lastName}")
        
        if (userData.gender.isNotBlank()) {
            val genderText = if (userData.gender == "Hombre") "Masculino" else "Femenino"
            Log.d("UserDataViewModel", genderText)
        }
        
        if (userData.birthDate.isNotBlank()) {
            Log.d("UserDataViewModel", "Nació el ${userData.birthDate}")
        }
        
        if (userData.educationLevel.isNotBlank()) {
            Log.d("UserDataViewModel", userData.educationLevel)
        }
    }
    
    // Función para loggear datos de contacto
    private fun logContactData() {
        Log.d("UserDataViewModel", "INFORMACIÓN DE CONTACTO")
        
        if (userData.phone.isNotBlank()) {
            Log.d("UserDataViewModel", "Teléfono: ${userData.phone}")
        }
        
        if (userData.address.isNotBlank()) {
            Log.d("UserDataViewModel", "Dirección: ${userData.address}")
        }
        
        if (userData.email.isNotBlank()) {
            Log.d("UserDataViewModel", "Email: ${userData.email}")
        }
        
        if (userData.country.isNotBlank()) {
            Log.d("UserDataViewModel", "País: ${userData.country}")
        }
        
        if (userData.city.isNotBlank()) {
            Log.d("UserDataViewModel", "Ciudad: ${userData.city}")
        }
    }
    
    // Función para navegar a una pantalla específica
    fun navigateToScreen(screen: Screen) {
        currentScreen = screen
    }

    // Función para loggear todos los datos
    fun logAllData() {
        Log.d("UserDataViewModel", "================ DATOS COMPLETOS DEL USUARIO ================")
        logPersonalData()
        logContactData()
    }
}
