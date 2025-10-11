package co.edu.udea.compumovil.gr08_20252.lab1.data.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name")
    val name: CountryName,
    @SerializedName("cca2")
    val countryCode: String,
    @SerializedName("flag")
    val flag: String
)

data class CountryName(
    @SerializedName("common")
    val common: String,
    @SerializedName("official")
    val official: String
)


