package com.noblecilla.pokedex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.noblecilla.pokedex.R
import com.noblecilla.pokedex.databinding.ActivityMainBinding
import com.noblecilla.pokedex.model.Pokemon
import com.noblecilla.pokedex.view.adapter.PokemonAdapter
import com.noblecilla.pokedex.view.setting.Mode
import com.noblecilla.pokedex.view.setting.Preferences
import com.noblecilla.pokedex.view.utils.toast
import com.noblecilla.pokedex.viewmodel.PokemonViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val pokemonViewModel: PokemonViewModel by viewModel()

    private lateinit var pokemonAdapter: PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupView()
    }

    private fun setupViewModel() {
        pokemonViewModel.pokemons.observe(this, pokemons)
        pokemonViewModel.onMessageError.observe(this, onMessageError)
    }

    private val pokemons = Observer<List<Pokemon>> {
        pokemonAdapter.update(it)
    }

    private val onMessageError = Observer<Any> {
        toast("$it")
    }

    private fun setupView() {
        pokemonAdapter = PokemonAdapter(emptyList())

        binding.pokemonSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                pokemonAdapter.filter.filter(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })

        binding.pokemonRecycler.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = pokemonAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        pokemonViewModel.all()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        when (Preferences.nightMode(this)) {
            Mode.LIGHT.ordinal -> menu?.findItem(R.id.night)?.isVisible = true
            Mode.NIGHT.ordinal -> menu?.findItem(R.id.light)?.isVisible = true
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.light -> switchToMode(AppCompatDelegate.MODE_NIGHT_NO, Mode.LIGHT)
            R.id.night -> switchToMode(AppCompatDelegate.MODE_NIGHT_YES, Mode.NIGHT)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun switchToMode(nightMode: Int, mode: Mode) {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        Preferences.switchToMode(this, mode)
    }
}
