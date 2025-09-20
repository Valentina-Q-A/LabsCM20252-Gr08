
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
//noinspection UsingMaterialAndMaterial3Libraries
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import co.edu.udea.compumovil.gr08_20252.lab1.UserData
import java.text.SimpleDateFormat
import java.util.*

// Definición del composable para toda la pantalla
@Composable
fun PersonalDataScreen(
    userData: UserData = UserData(),
    onDataChange: (UserData) -> Unit = {},
    onNextClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título de la pantalla
        Text(text = "Información Personal", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(32.dp))
        
        // Campo para Nombres
        NameField(
            value = userData.firstName,
            onValueChange = { onDataChange(userData.copy(firstName = it)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para Apellidos
        LastNameField(
            value = userData.lastName,
            onValueChange = { onDataChange(userData.copy(lastName = it)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para Sexo
        GenderSelection(
            selectedGender = userData.gender,
            onGenderChange = { onDataChange(userData.copy(gender = it)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para Fecha de Nacimiento
        DatePickerDocked(
            selectedDate = userData.birthDate,
            onDateChange = { onDataChange(userData.copy(birthDate = it)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para Grado de Escolaridad
        EducationLevelSpinner(
            selectedLevel = userData.educationLevel,
            onLevelChange = { onDataChange(userData.copy(educationLevel = it)) }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón de Siguiente
        Button(
            onClick = onNextClick,
            enabled = userData.isPersonalDataComplete()
        ) {
            Text(text = "Siguiente")
        }
    }
}

// Campo de nombres
@Composable
fun NameField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
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
}

// Campo de apellidos
@Composable
fun LastNameField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
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
}

// Selección de sexo
@Composable
fun GenderSelection(
    selectedGender: String,
    onGenderChange: (String) -> Unit
) {
    val genders = listOf("Hombre", "Mujer")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Sexo:", style = MaterialTheme.typography.body1)
        Spacer(Modifier.width(8.dp))
        genders.forEach { gender ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = (gender == selectedGender),
                    onClick = { onGenderChange(gender) }
                )
                Text(text = gender)
                Spacer(Modifier.width(8.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked(
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
    }
    
    // Actualizar automáticamente cuando se selecciona una fecha
    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let { millis ->
            onDateChange(convertMillisToDate(millis))
            showDatePicker = false // Cerrar automáticamente
        }
    }
}


fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

// Spinner de escolaridad
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EducationLevelSpinner(
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

// Vista previa
@Preview(showBackground = true)
@Composable
fun PersonalDataScreenPreview() {
    PersonalDataScreen()
}
