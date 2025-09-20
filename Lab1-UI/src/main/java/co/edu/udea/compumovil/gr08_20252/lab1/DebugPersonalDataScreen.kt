package co.edu.udea.compumovil.gr08_20252.lab1

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import java.text.SimpleDateFormat
import java.util.*

// Versión de debug para probar que los campos funcionen
@Composable
fun DebugPersonalDataScreen() {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Hombre") }
    var birthDate by remember { mutableStateOf("") }
    var educationLevel by remember { mutableStateOf("Primaria") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Debug - Información Personal", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(32.dp))
        
        // Campo de nombres
        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("Nombres") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Campo de apellidos
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Apellidos") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Selección de sexo
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Sexo:", style = MaterialTheme.typography.body1)
            Spacer(Modifier.width(8.dp))
            listOf("Hombre", "Mujer").forEach { genderOption ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = (genderOption == gender),
                        onClick = { gender = genderOption }
                    )
                    Text(text = genderOption)
                    Spacer(Modifier.width(8.dp))
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Campo de fecha de nacimiento
        DebugDatePicker(
            selectedDate = birthDate,
            onDateChange = { birthDate = it }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Campo de grado de escolaridad
        DebugEducationSpinner(
            selectedLevel = educationLevel,
            onLevelChange = { educationLevel = it }
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Botón de Siguiente
        Button(
            onClick = { 
                // Aquí puedes agregar lógica para continuar
                println("Datos: $firstName $lastName, $gender, $birthDate, $educationLevel")
            },
            enabled = firstName.isNotBlank() && lastName.isNotBlank() && birthDate.isNotBlank()
        ) {
            Text(text = "Siguiente")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Mostrar valores actuales
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Valores actuales:", style = MaterialTheme.typography.h6)
                Text("Nombres: $firstName")
                Text("Apellidos: $lastName")
                Text("Sexo: $gender")
                Text("Fecha de nacimiento: $birthDate")
                Text("Grado de escolaridad: $educationLevel")
            }
        }
    }
}

// Componente de fecha para debug
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebugDatePicker(
    selectedDate: String,
    onDateChange: (String) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { },
            label = { Text("Fecha de Nacimiento") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )

        if (showDatePicker) {
            Popup(
                onDismissRequest = { showDatePicker = false },
                alignment = Alignment.TopStart
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)
                        .padding(16.dp),
                    elevation = 4.dp
                ) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false
                    )
                }
            }
        }
        
        // Actualizar automáticamente cuando se selecciona una fecha
        LaunchedEffect(datePickerState.selectedDateMillis) {
            datePickerState.selectedDateMillis?.let { millis ->
                onDateChange(convertMillisToDate(millis))
                showDatePicker = false // Cerrar automáticamente
            }
        }
    }
}

// Componente de educación para debug
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DebugEducationSpinner(
    selectedLevel: String,
    onLevelChange: (String) -> Unit
) {
    val educationLevels = listOf("Primaria", "Secundaria", "Universitaria", "Otro")
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedLevel,
            onValueChange = {},
            readOnly = true,
            label = { Text("Grado de escolaridad") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            educationLevels.forEach { level ->
                DropdownMenuItem(
                    onClick = {
                        onLevelChange(level)
                        expanded = false
                    }
                ) {
                    Text(text = level)
                }
            }
        }
    }
}

// Función auxiliar para convertir fecha
fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@Preview(showBackground = true)
@Composable
fun DebugPersonalDataScreenPreview() {
    DebugPersonalDataScreen()
}
