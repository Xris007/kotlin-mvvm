package com.noblecilla.pokedex.model

import com.noblecilla.pokedex.vo.Result

interface PokemonDataSource {
    suspend fun all(): Result<List<Pokemon>>
}
