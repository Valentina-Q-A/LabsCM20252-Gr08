package co.edu.udea.compumovil.gr08_20252.lab1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.edu.udea.compumovil.gr08_20252.lab1.data.model.UserData
import co.edu.udea.compumovil.gr08_20252.lab1.ui.util.rememberOrientationManager
import androidx.lifecycle.viewmodel.compose.viewModel
import co.edu.udea.compumovil.gr08_20252.lab1.ui.viewmodel.CountriesViewModel


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
        
        CountryDropdown(
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
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Next
        ),
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
        label = { Text("Correo Electrónico") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
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
fun CountryDropdown(
    value: String,
    onValueChange: (String) -> Unit
) {
    val countriesViewModel: CountriesViewModel = viewModel()
    var expanded by remember { mutableStateOf(false) }

    // Lista de países de respaldo en caso de error de API
    val fallbackCountries = listOf(
        "Colombia", "México", "Argentina", "Brasil", "Chile", "Perú", "Venezuela",
        "Ecuador", "Bolivia", "Uruguay", "Paraguay", "Estados Unidos", "Canadá",
        "España", "Francia", "Alemania", "Italia", "Reino Unido", "Japón", "China",
        "India", "Australia", "Rusia", "Sudáfrica", "Egipto", "Nigeria", "Kenia"
    )

    Box {
        OutlinedTextField(
            value = value,
            onValueChange = { },
            label = { Text("País") },
            placeholder = { Text("Seleccione un país") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                if (countriesViewModel.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = "Dropdown"
                        )
                    }
                }
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            when {
                countriesViewModel.isLoading -> {
                    DropdownMenuItem(
                        onClick = { }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Cargando países...")
                        }
                    }
                }
                countriesViewModel.error != null -> {
                    // Mostrar países de respaldo si hay error
                    fallbackCountries.forEach { country ->
                        DropdownMenuItem(
                            onClick = {
                                onValueChange(country)
                                expanded = false
                            }
                        ) {
                            Text(text = country)
                        }
                    }
                }
                countriesViewModel.countries.isEmpty() -> {
                    // Mostrar países de respaldo si no hay datos
                    fallbackCountries.forEach { country ->
                        DropdownMenuItem(
                            onClick = {
                                onValueChange(country)
                                expanded = false
                            }
                        ) {
                            Text(text = country)
                        }
                    }
                }
                else -> {
                    countriesViewModel.countries.take(50).forEach { country ->
                        DropdownMenuItem(
                            onClick = {
                                onValueChange(country.name.common)
                                expanded = false
                            }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = country.flag,
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(text = country.name.common)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CityAutocomplete(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Ciudad") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            autoCorrectEnabled = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        singleLine = true
    )
}
