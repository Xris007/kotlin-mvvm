package com.noblecilla.pokedex.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
    @SerializedName("form") val form: String?,
    @SerializedName("pokemon_id") val id: Int?,
    @SerializedName("pokemon_name") val name: String?,
    @SerializedName("type") val type: List<String?>?
)
