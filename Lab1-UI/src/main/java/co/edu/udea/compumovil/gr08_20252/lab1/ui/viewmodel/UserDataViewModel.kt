package co.edu.udea.compumovil.gr08_20252.lab1.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import co.edu.udea.compumovil.gr08_20252.lab1.Screen
import co.edu.udea.compumovil.gr08_20252.lab1.data.model.UserData

class UserDataViewModel : ViewModel() {
    var userData by mutableStateOf(UserData())
        private set

    var currentScreen by mutableStateOf(Screen.PERSONAL_DATA)
        private set

    fun updateUserData(newData: UserData) { userData = newData }

    fun navigateToNextScreen() {
        when (currentScreen) {
            Screen.PERSONAL_DATA -> if (validatePersonalData()) { logPersonalData(); currentScreen = Screen.CONTACT_DATA }
            Screen.CONTACT_DATA -> if (validateContactData()) { logContactData(); currentScreen = Screen.SUMMARY }
            Screen.SUMMARY -> {}
        }
    }

    private fun validatePersonalData(): Boolean {
        val isValid = userData.firstName.isNotBlank() && userData.lastName.isNotBlank() && userData.birthDate.isNotBlank()
        if (!isValid) Log.w("UserDataViewModel", "Datos personales incompletos")
        return isValid
    }

    private fun validateContactData(): Boolean {
        val isValid = userData.phone.isNotBlank() && userData.email.isNotBlank() && userData.country.isNotBlank()
        if (!isValid) Log.w("UserDataViewModel", "Datos de contacto incompletos")
        return isValid
    }

    private fun logPersonalData() {
        Log.d("UserDataViewModel", "INFORMACIÓN PERSONAL")
        Log.d("UserDataViewModel", "${userData.firstName} ${userData.lastName}")
        if (userData.gender.isNotBlank()) Log.d("UserDataViewModel", if (userData.gender == "Hombre") "Masculino" else "Femenino")
        if (userData.birthDate.isNotBlank()) Log.d("UserDataViewModel", "Nació el ${userData.birthDate}")
        if (userData.educationLevel.isNotBlank()) Log.d("UserDataViewModel", userData.educationLevel)
    }

    private fun logContactData() {
        Log.d("UserDataViewModel", "INFORMACIÓN DE CONTACTO")
        if (userData.phone.isNotBlank()) Log.d("UserDataViewModel", "Teléfono: ${userData.phone}")
        if (userData.address.isNotBlank()) Log.d("UserDataViewModel", "Dirección: ${userData.address}")
        if (userData.email.isNotBlank()) Log.d("UserDataViewModel", "Email: ${userData.email}")
        if (userData.country.isNotBlank()) Log.d("UserDataViewModel", "País: ${userData.country}")
        if (userData.city.isNotBlank()) Log.d("UserDataViewModel", "Ciudad: ${userData.city}")
    }

    fun navigateToScreen(screen: Screen) { currentScreen = screen }

    fun logAllData() { logPersonalData(); logContactData() }
}


