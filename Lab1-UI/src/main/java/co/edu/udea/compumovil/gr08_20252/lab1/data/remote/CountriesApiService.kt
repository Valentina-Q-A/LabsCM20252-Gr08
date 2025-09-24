package co.edu.udea.compumovil.gr08_20252.lab1.data.remote

import co.edu.udea.compumovil.gr08_20252.lab1.data.model.Country
import retrofit2.Response
import retrofit2.http.GET

interface CountriesApiService {
    @GET("v3.1/all")
    suspend fun getAllCountries(): Response<List<Country>>

}


