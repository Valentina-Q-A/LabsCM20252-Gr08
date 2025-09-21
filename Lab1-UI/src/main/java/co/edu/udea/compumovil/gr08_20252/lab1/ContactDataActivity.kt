import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import co.edu.udea.compumovil.gr08_20252.lab1.UserData
import co.edu.udea.compumovil.gr08_20252.lab1.rememberOrientationManager

@Composable
fun ContactDataScreen(
    userData: UserData = UserData(),
    onDataChange: (UserData) -> Unit = {},
    onSaveClick: () -> Unit = {}
) {
    val orientationManager = rememberOrientationManager()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(orientationManager.getScreenPadding()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Información de Contacto", 
            style = orientationManager.getTitleStyle()
        )
        Spacer(modifier = Modifier.height(orientationManager.getTitleSpacing()))
        
        // Campos de entrada
        PhoneField(
            value = userData.phone,
            onValueChange = { onDataChange(userData.copy(phone = it)) }
        )
        
        Spacer(modifier = Modifier.height(orientationManager.getElementSpacing()))
        
        EmailField(
            value = userData.email,
            onValueChange = { onDataChange(userData.copy(email = it)) }
        )
        
        Spacer(modifier = Modifier.height(orientationManager.getElementSpacing()))
        
        CountryAutocomplete(
            value = userData.country,
            onValueChange = { onDataChange(userData.copy(country = it)) }
        )
        
        Spacer(modifier = Modifier.height(orientationManager.getElementSpacing()))
        
        CityAutocomplete(
            value = userData.city,
            onValueChange = { onDataChange(userData.copy(city = it)) }
        )
        
        Spacer(modifier = Modifier.height(orientationManager.getElementSpacing()))
        
        AddressField(
            value = userData.address,
            onValueChange = { onDataChange(userData.copy(address = it)) }
        )
        
        Spacer(modifier = Modifier.height(orientationManager.getElementSpacing() * 2))
        
        // Botón de Guardar
        Button(
            onClick = onSaveClick,
            enabled = userData.isContactDataComplete(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Guardar Datos")
        }
    }
}

@Composable
fun PhoneField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Teléfono") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        singleLine = true
    )
}

@Composable
fun EmailField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Correo") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true
    )
}


@Composable
fun AddressField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Dirección") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.None,
            autoCorrectEnabled = false
        ),
        singleLine = true
    )
}


@Composable
fun CountryAutocomplete(
    value: String,
    onValueChange: (String) -> Unit
) {
    val countries = listOf("Argentina", "Bolivia", "Brasil", "Chile", "Colombia", "Ecuador", "México", "Perú", "Venezuela")
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(it); expanded = true },
            label = { Text("País") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            countries.filter { it.contains(value, ignoreCase = true) }.forEach { country ->
                DropdownMenuItem(onClick = { onValueChange(country); expanded = false }) {
                    Text(text = country)
                }
            }
        }
    }
}

// Asumiendo que tienes un ViewModel para manejar la lógica de datos
// class ContactViewModel : ViewModel() {
//    val cities: State<List<String>> = ...
//    fun searchCities(query: String) { ... }
// }

@Composable
fun CityAutocomplete(
    value: String,
    onValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val citiesExample = listOf("Bogotá", "Medellín", "Cali", "Barranquilla", "Cartagena", "Bucaramanga", "Pereira", "Santa Marta")

    Box {
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                expanded = true
            },
            label = { Text("Ciudad") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            citiesExample.filter { it.contains(value, ignoreCase = true) }.forEach { city ->
                DropdownMenuItem(onClick = { onValueChange(city); expanded = false }) {
                    Text(text = city)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactDataActivityPreview() {
    ContactDataScreen()
}