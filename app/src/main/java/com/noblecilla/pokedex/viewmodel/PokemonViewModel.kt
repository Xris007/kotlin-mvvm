package com.noblecilla.pokedex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noblecilla.pokedex.model.Pokemon
import com.noblecilla.pokedex.model.PokemonDataSource
import com.noblecilla.pokedex.vo.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonViewModel(private val dataSource: PokemonDataSource) : ViewModel() {

    private val _pokemons = MutableLiveData<List<Pokemon>>()
    val pokemons: LiveData<List<Pokemon>> = _pokemons

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    fun all() = viewModelScope.launch {
        val result: Result<List<Pokemon>> = withContext(Dispatchers.IO) {
            dataSource.all()
        }

        when (result) {
            is Result.Success -> _pokemons.value = result.data
            is Result.Error -> _onMessageError.value = result.exception
        }
    }
}
