package co.edu.udea.compumovil.gr08_20252.lab1.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.udea.compumovil.gr08_20252.lab1.data.model.Country
import co.edu.udea.compumovil.gr08_20252.lab1.data.repository.CountriesRepository
import kotlinx.coroutines.launch

class CountriesViewModel : ViewModel() {
    private val repository = CountriesRepository()

    var countries by mutableStateOf<List<Country>>(emptyList())
        private set

    var filteredCountries by mutableStateOf<List<Country>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    init {
        loadAllCountries()
    }

    private fun loadAllCountries() {
        viewModelScope.launch {
            isLoading = true
            error = null

            repository.getAllCountries()
                .onSuccess { countryList ->
                    countries = countryList.sortedBy { it.name.common }
                    filteredCountries = countries
                    println("Países cargados: ${countries.size}")
                }
                .onFailure { exception ->
                    error = exception.message ?: "Error desconocido"
                    println("Error cargando países: ${exception.message}")
                    exception.printStackTrace()
                }

            isLoading = false
        }
    }

}


