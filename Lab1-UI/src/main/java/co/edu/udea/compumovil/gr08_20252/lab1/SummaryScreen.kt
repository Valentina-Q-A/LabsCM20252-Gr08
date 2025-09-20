package co.edu.udea.compumovil.gr08_20252.lab1

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SummaryScreen(
    userData: UserData,
    onEditPersonalData: () -> Unit = {},
    onEditContactData: () -> Unit = {},
    onSaveData: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título
        Text(
            text = "Resumen de Datos",
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Icono de éxito
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Datos completos",
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colors.primary
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Información Personal
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Información Personal",
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                DataRow("Nombre completo:", userData.getFullName())
                DataRow("Sexo:", userData.gender)
                DataRow("Fecha de nacimiento:", userData.birthDate)
                DataRow("Grado de escolaridad:", userData.educationLevel)
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Button(
                    onClick = onEditPersonalData,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Editar Datos Personales")
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Información de Contacto
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Información de Contacto",
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                DataRow("Teléfono:", userData.phone)
                DataRow("Correo electrónico:", userData.email)
                DataRow("País:", userData.country)
                DataRow("Ciudad:", userData.city)
                DataRow("Dirección:", userData.address)
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Button(
                    onClick = onEditContactData,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Editar Datos de Contacto")
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Botón de Guardar
        Button(
            onClick = onSaveData,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary
            )
        ) {
            Text(
                text = "Guardar Todos los Datos",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Información de validación
        if (userData.isPersonalDataComplete() && userData.isContactDataComplete()) {
            Text(
                text = "✓ Todos los datos están completos y listos para guardar",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center
            )
        } else {
            Text(
                text = "⚠ Algunos campos están incompletos",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun DataRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(0.4f)
        )
        Text(
            text = value.ifEmpty { "No especificado" },
            style = MaterialTheme.typography.body2,
            modifier = Modifier.weight(0.6f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SummaryScreenPreview() {
    val sampleData = UserData(
        firstName = "Juan",
        lastName = "Pérez",
        gender = "Hombre",
        birthDate = "15/03/1990",
        educationLevel = "Universitaria",
        phone = "+57 300 123 4567",
        email = "juan.perez@email.com",
        country = "Colombia",
        city = "Bogotá",
        address = "Calle 123 #45-67"
    )
    
    SummaryScreen(userData = sampleData)
}
