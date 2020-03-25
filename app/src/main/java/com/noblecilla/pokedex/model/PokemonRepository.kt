package com.noblecilla.pokedex.model

import com.noblecilla.pokedex.data.ApiClient
import com.noblecilla.pokedex.vo.Result

class PokemonRepository : PokemonDataSource {

    override suspend fun all(): Result<List<Pokemon>> {
        return try {
            val response = ApiClient.build()?.all()

            response?.let {
                if (it.isSuccessful && it.body() != null) {
                    val pokemons = it.body()!!
                        .filter { pokemon -> pokemon.form == "Normal" || pokemon.form == null }
                    Result.Success(pokemons)
                } else {
                    Result.Error(Exception(it.message()))
                }
            } ?: run {
                Result.Error(Exception("An error ocurred"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
