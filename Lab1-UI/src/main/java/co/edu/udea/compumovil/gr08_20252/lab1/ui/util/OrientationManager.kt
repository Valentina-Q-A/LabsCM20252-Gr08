package co.edu.udea.compumovil.gr08_20252.lab1.ui.util

import android.annotation.SuppressLint
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

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
        isTablet = width >= 600.dp
    }

    fun getScreenPadding(): Dp = when {
        isTablet -> 32.dp
        isPortrait -> 12.dp
        else -> 8.dp
    }

    fun getElementSpacing(): Dp = when {
        isTablet -> 24.dp
        isPortrait -> 12.dp
        else -> 8.dp
    }

    fun getTitleSpacing(): Dp = when {
        isTablet -> 32.dp
        isPortrait -> 12.dp
        else -> 8.dp
    }

    fun getSectionSpacing(): Dp = when {
        isTablet -> 24.dp
        isPortrait -> 16.dp
        else -> 12.dp
    }

    @Composable
    fun getTitleStyle() = when {
        isTablet -> MaterialTheme.typography.headlineLarge
        isPortrait -> MaterialTheme.typography.headlineMedium
        else -> MaterialTheme.typography.headlineSmall
    }
}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun rememberOrientationManager(): OrientationManager {
    val configuration = LocalConfiguration.current
    val orientationManager = androidx.compose.runtime.remember { OrientationManager() }
    androidx.compose.runtime.LaunchedEffect(configuration.screenWidthDp, configuration.screenHeightDp) {
        orientationManager.updateConfiguration(configuration.screenWidthDp.dp, configuration.screenHeightDp.dp)
    }
    return orientationManager
}


