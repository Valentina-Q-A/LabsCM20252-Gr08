package co.edu.udea.compumovil.gr08_20252.lab1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import co.edu.udea.compumovil.gr08_20252.lab1.data.model.UserData
import co.edu.udea.compumovil.gr08_20252.lab1.ui.util.rememberOrientationManager
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PersonalDataScreen(
    userData: UserData = UserData(),
    onDataChange: (UserData) -> Unit = {},
    onNextClick: () -> Unit = {}
) {
    val orientationManager = rememberOrientationManager()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(orientationManager.getScreenPadding()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Información Personal", style = orientationManager.getTitleStyle())
        Spacer(modifier = Modifier.height(orientationManager.getTitleSpacing()))

        NameField(
            value = userData.firstName,
            onValueChange = { onDataChange(userData.copy(firstName = it)) }
        )
        Spacer(modifier = Modifier.height(orientationManager.getElementSpacing()))

        LastNameField(
            value = userData.lastName,
            onValueChange = { onDataChange(userData.copy(lastName = it)) }
        )
        Spacer(modifier = Modifier.height(orientationManager.getElementSpacing()))

        GenderSelection(
            selectedGender = userData.gender,
            onGenderChange = { onDataChange(userData.copy(gender = it)) }
        )
        Spacer(modifier = Modifier.height(orientationManager.getElementSpacing()))

        DatePickerModal(
            selectedDate = userData.birthDate,
            onDateChange = { onDataChange(userData.copy(birthDate = it)) }
        )
        Spacer(modifier = Modifier.height(orientationManager.getElementSpacing()))

        EducationLevelSpinner(
            selectedLevel = userData.educationLevel,
            onLevelChange = { onDataChange(userData.copy(educationLevel = it)) }
        )
        Spacer(modifier = Modifier.height(orientationManager.getElementSpacing() * 2))

        Button(onClick = onNextClick, enabled = userData.isPersonalDataComplete()) {
            Text(text = "Siguiente")
        }
    }
}

@OptIn(ExperimentalMaterialApi::class) // Or ExperimentalMaterial3Api if using Material3
@Composable
fun EducationLevelSpinner(
    selectedLevel: String,
    onLevelChange: (String) -> Unit
) {
    val educationLevels = listOf("Primaria", "Secundaria", "Bachillerato", "Pregrado", "Posgrado")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(selectedLevel.ifEmpty { educationLevels[0] }) }

    // Update selectedOptionText if selectedLevel changes externally
    LaunchedEffect(selectedLevel) {
        if (selectedLevel.isNotEmpty()) {
            selectedOptionText = selectedLevel
        }
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedOptionText,
            onValueChange = { }, // Not directly editable
            label = { Text("Nivel Educativo") },
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            educationLevels.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOptionText = selectionOption
                        onLevelChange(selectionOption)
                        expanded = false
                    }
                ) {
                    Text(text = selectionOption)
                }
            }
        }
    }
}

@Composable
fun NameField(value: String, onValueChange: (String) -> Unit) {
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

@Composable
fun LastNameField(value: String, onValueChange: (String) -> Unit) {
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

@Composable
fun GenderSelection(selectedGender: String, onGenderChange: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Sexo", modifier = Modifier.weight(1f))
        Row(modifier = Modifier.weight(2f), verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = selectedGender == "Hombre", onClick = { onGenderChange("Hombre") })
            Text(text = "Hombre")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(selected = selectedGender == "Mujer", onClick = { onGenderChange("Mujer") })
            Text(text = "Mujer")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(selectedDate: String, onDateChange: (String) -> Unit) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    OutlinedTextField(
        value = selectedDate,
        onValueChange = { },
        label = { Text("Fecha de Nacimiento") },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { showDatePicker = true }) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Selecciona fecha")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        onDateChange(convertMillisToDate(millis))
                    }
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = { TextButton(onClick = { showDatePicker = false }) { Text("Cancelar") } }
        ) { DatePicker(state = datePickerState, showModeToggle = false) }
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}


