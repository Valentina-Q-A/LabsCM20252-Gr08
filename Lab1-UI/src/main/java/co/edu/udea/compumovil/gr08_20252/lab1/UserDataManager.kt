package co.edu.udea.compumovil.gr08_20252.lab1

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
