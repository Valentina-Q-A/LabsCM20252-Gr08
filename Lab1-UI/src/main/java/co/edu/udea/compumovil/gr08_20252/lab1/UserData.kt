package co.edu.udea.compumovil.gr08_20252.lab1

// Modelo de datos unificado para toda la información del usuario
data class UserData(
    // Datos personales
    val firstName: String = "",
    val lastName: String = "",
    val gender: String = "Hombre",
    val birthDate: String = "",
    val educationLevel: String = "Primaria",
    
    // Datos de contacto
    val phone: String = "",
    val email: String = "",
    val country: String = "",
    val city: String = "",
    val address: String = ""
) {
    // Función para validar si todos los campos requeridos están llenos
    fun isPersonalDataComplete(): Boolean {
        return firstName.isNotBlank() && 
               lastName.isNotBlank() && 
               birthDate.isNotBlank()
    }
    
    // Función para validar si todos los campos de contacto están llenos
    fun isContactDataComplete(): Boolean {
        return phone.isNotBlank() && 
               email.isNotBlank() && 
               country.isNotBlank() && 
               city.isNotBlank() && 
               address.isNotBlank()
    }
    
    // Función para obtener el nombre completo
    fun getFullName(): String {
        return "$firstName $lastName".trim()
    }

}
