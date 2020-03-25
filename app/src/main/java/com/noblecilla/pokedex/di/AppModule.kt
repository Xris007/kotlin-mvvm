package com.noblecilla.pokedex.di

import com.noblecilla.pokedex.model.PokemonDataSource
import com.noblecilla.pokedex.model.PokemonRepository
import com.noblecilla.pokedex.viewmodel.PokemonViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<PokemonDataSource> { PokemonRepository() }
    viewModel { PokemonViewModel(get()) }
}
