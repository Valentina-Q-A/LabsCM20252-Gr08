import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ContactDataScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Información de Contacto", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(32.dp))
        // Campos de entrada
        PhoneField()
        EmailField()
        CountryAutocomplete()
        CityAutocomplete()
        AddressField()
        // Botón de Siguiente
        Button(onClick = { /* Lógica para continuar */ }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Siguiente")
        }
    }
}

@Composable
fun PhoneField() {
    var phone by remember { mutableStateOf("") }
    OutlinedTextField(
        value = phone,
        onValueChange = { phone = it },
        label = { Text("Teléfono") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
    )
}


@Composable
fun EmailField() {
    var email by remember { mutableStateOf("") }
    OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        label = { Text("Correo") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}


@Composable
fun AddressField() {
    var address by remember { mutableStateOf("") }
    OutlinedTextField(
        value = address,
        onValueChange = { address = it },
        label = { Text("Dirección") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = false
        )
    )
}


@Composable
fun CountryAutocomplete() {
    val countries = listOf("Argentina", "Bolivia", "Brasil", "Chile", "Colombia", "Ecuador", "México", "Perú", "Venezuela") // Ejemplo
    var text by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it; expanded = true },
            label = { Text("País") },
            modifier = Modifier.fillMaxWidth()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            countries.filter { it.contains(text, ignoreCase = true) }.forEach { country ->
                DropdownMenuItem(onClick = { text = country; expanded = false }) {
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
fun CityAutocomplete() {
    // val viewModel: ContactViewModel = hiltViewModel()
    // val cities by viewModel.cities.collectAsState()
    var text by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                // viewModel.searchCities(it) // Llamada al API
                expanded = true
            },
            label = { Text("Ciudad") },
            modifier = Modifier.fillMaxWidth()
        )
        // Ejemplo de DropdownMenu con datos del API
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            // cities.forEach { city ->
            //    DropdownMenuItem(onClick = { text = city; expanded = false }) {
            //        Text(text = city)
            //    }
            // }
            // Simulación con una lista estática para el ejemplo
            val citiesExample = listOf("Bogotá", "Medellín", "Cali", "Barranquilla")
            citiesExample.filter { it.contains(text, ignoreCase = true) }.forEach { city ->
                DropdownMenuItem(onClick = { text = city; expanded = false }) {
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