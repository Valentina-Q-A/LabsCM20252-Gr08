package co.edu.udea.compumovil.gr08_20252.lab1.data.model

data class UserData(
    val firstName: String = "",
    val lastName: String = "",
    val gender: String = "Hombre",
    val birthDate: String = "",
    val educationLevel: String = "Primaria",
    val phone: String = "",
    val email: String = "",
    val country: String = "",
    val city: String = "",
    val address: String = ""
) {
    fun isPersonalDataComplete(): Boolean {
        return firstName.isNotBlank() &&
               lastName.isNotBlank() &&
               birthDate.isNotBlank()
    }

    fun isContactDataComplete(): Boolean {
        return phone.isNotBlank() &&
               email.isNotBlank() &&
               country.isNotBlank() &&
               city.isNotBlank() &&
               address.isNotBlank()
    }

    fun getFullName(): String = "$firstName $lastName".trim()
}


