import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.*

// Definición del composable para toda la pantalla
@Composable
fun PersonalDataScreen() {
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
        NameField()

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para Apellidos
        LastNameField()

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para Sexo
        GenderSelection()

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para Fecha de Nacimiento
        DateOfBirthPicker()

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para Grado de Escolaridad
        EducationLevelSpinner()

        Spacer(modifier = Modifier.height(32.dp))

        // Botón de Siguiente
        Button(onClick = { /* Lógica para continuar */ }) {
            Text(text = "Siguiente")
        }
    }
}

// Campo de nombres
@Composable
fun NameField() {
    var name by remember { mutableStateOf("") }
    OutlinedTextField(
        value = name,
        onValueChange = { name = it },
        label = { Text("Nombres") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            autoCorrect = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )
    )
}

// Campo de apellidos
@Composable
fun LastNameField() {
    var lastName by remember { mutableStateOf("") }
    OutlinedTextField(
        value = lastName,
        onValueChange = { lastName = it },
        label = { Text("Apellidos") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            autoCorrect = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        )
    )
}

// Selección de sexo
@Composable
fun GenderSelection() {
    val genders = listOf("Hombre", "Mujer")
    var selectedGender by remember { mutableStateOf(genders[0]) }

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
                    onClick = { selectedGender = gender }
                )
                Text(text = gender)
                Spacer(Modifier.width(8.dp))
            }
        }
    }
}

// DatePicker funcional
@Composable
fun DateOfBirthPicker() {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    var selectedDate by remember { mutableStateOf("") }

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            selectedDate = "%02d/%02d/%04d".format(selectedDay, selectedMonth + 1, selectedYear)
        }, year, month, day
    )

    OutlinedTextField(
        value = if (selectedDate.isEmpty()) "" else selectedDate,
        onValueChange = {},
        label = { Text("Fecha de nacimiento") },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { datePickerDialog.show() },
        readOnly = true,
        placeholder = { Text("Seleccione una fecha") }
    )
}

// Spinner de escolaridad
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EducationLevelSpinner() {
    val educationLevels = listOf("Primaria", "Secundaria", "Universitaria", "Otro")
    var expanded by remember { mutableStateOf(false) }
    var selectedLevel by remember { mutableStateOf(educationLevels[0]) }

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
                        selectedLevel = level
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
