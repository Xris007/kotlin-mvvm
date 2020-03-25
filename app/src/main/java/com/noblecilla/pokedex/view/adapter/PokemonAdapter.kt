package com.noblecilla.pokedex.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.noblecilla.pokedex.databinding.LayoutPokemonBinding
import com.noblecilla.pokedex.model.Pokemon
import com.noblecilla.pokedex.view.utils.pokemonLoading
import com.noblecilla.pokedex.view.utils.typeLoading
import java.util.*

@Suppress("UNCHECKED_CAST")
@SuppressLint("SetTextI18n")
class PokemonAdapter(private var list: List<Pokemon>) :
    RecyclerView.Adapter<PokemonAdapter.ViewHolder>(), Filterable {

    private var listFilter = listOf<Pokemon>()

    init {
        listFilter = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutPokemonBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(if (listFilter.isEmpty()) list[position] else listFilter[position])
    }

    override fun getItemCount(): Int {
        return if (listFilter.isEmpty()) {
            list.size
        } else {
            listFilter.size
        }
    }

    fun update(list: List<Pokemon>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                listFilter = if (charSearch.isEmpty()) {
                    list
                } else {
                    val resultList = mutableListOf<Pokemon>()
                    for (row in list) {
                        if (row.name?.toLowerCase(Locale.ROOT)!!
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResult = FilterResults()
                filterResult.values = listFilter
                return filterResult
            }


            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listFilter = results?.values as List<Pokemon>
                notifyDataSetChanged()
            }
        }
    }

    class ViewHolder(private val binding: LayoutPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: Pokemon) = with(itemView) {
            binding.pokemonImage.pokemonLoading(String.format("%03d", pokemon.id))
            binding.pokemonName.text = "#${String.format("%03d", pokemon.id)} ${pokemon.name}"

            pokemon.type?.let {
                binding.pokemonType1.typeLoading(it[0]!!)
                binding.pokemonType2.visibility = if (it.size == 2) {
                    binding.pokemonType2.typeLoading(it[1]!!)
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }
    }
}
