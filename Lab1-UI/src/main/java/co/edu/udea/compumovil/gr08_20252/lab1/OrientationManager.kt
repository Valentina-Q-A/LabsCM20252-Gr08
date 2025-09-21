package co.edu.udea.compumovil.gr08_20252.lab1

import android.annotation.SuppressLint
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Clase para manejar la orientación y configuración de pantalla
class OrientationManager {
    var screenWidth by mutableStateOf(0.dp)
        private set
    var screenHeight by mutableStateOf(0.dp)
        private set
    var isPortrait by mutableStateOf(true)
        private set
    var isTablet by mutableStateOf(false)
        private set
    
    fun updateConfiguration(width: Dp, height: Dp) {
        screenWidth = width
        screenHeight = height
        isPortrait = height > width
        isTablet = width >= 600.dp // Consideramos tablet si el ancho es >= 600dp
    }
    
    // Función para obtener el padding apropiado según el tamaño de pantalla
    fun getScreenPadding(): Dp {
        return when {
            isTablet -> 32.dp
            isPortrait -> 12.dp
            else -> 8.dp
        }
    }
    
    // Función para obtener el espaciado entre elementos
    fun getElementSpacing(): Dp {
        return when {
            isTablet -> 24.dp
            isPortrait -> 12.dp
            else -> 8.dp
        }
    }
    
    // Función para obtener el espaciado del título
    fun getTitleSpacing(): Dp {
        return when {
            isTablet -> 32.dp
            isPortrait -> 12.dp
            else -> 8.dp
        }
    }
    
    // Función para obtener el espaciado entre secciones
    fun getSectionSpacing(): Dp {
        return when {
            isTablet -> 24.dp
            isPortrait -> 16.dp
            else -> 12.dp
        }
    }
    
    // Función para obtener el tamaño de fuente del título
    @Composable
    fun getTitleStyle() = when {
        isTablet -> MaterialTheme.typography.headlineLarge
        isPortrait -> MaterialTheme.typography.headlineMedium
        else -> MaterialTheme.typography.headlineSmall
    }
}

// Composable para obtener la configuración de orientación
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun rememberOrientationManager(): OrientationManager {
    val configuration = LocalConfiguration.current
    val orientationManager = androidx.compose.runtime.remember { OrientationManager() }
    
    androidx.compose.runtime.LaunchedEffect(configuration.screenWidthDp, configuration.screenHeightDp) {
        orientationManager.updateConfiguration(
            width = configuration.screenWidthDp.dp,
            height = configuration.screenHeightDp.dp
        )
    }
    
    return orientationManager
}
