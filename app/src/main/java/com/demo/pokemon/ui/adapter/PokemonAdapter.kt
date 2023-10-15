package com.demo.pokemon.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.DataBindingHolder
import com.demo.pokemon.R
import com.demo.pokemon.DemonPokemonQuery
import com.demo.pokemon.databinding.ListItemPokemonBinding
import com.demo.pokemon.databinding.ListItemPokemonDetailBinding
import com.demo.pokemon.datasource.DataRepository

class PokemonAdapter(var orchestrator: DataRepository) :
    BaseQuickAdapter<DemonPokemonQuery.Pokemon_v2_pokemonspecy, DataBindingHolder<ListItemPokemonBinding>>() {

    override fun onBindViewHolder(
        holder: DataBindingHolder<ListItemPokemonBinding>,
        position: Int,
        item: DemonPokemonQuery.Pokemon_v2_pokemonspecy?
    ) {
        val binding = holder.binding
        binding.apply {
            bgColor = when (item?.pokemon_v2_pokemoncolor?.name) {
                "red" -> ContextCompat.getColor(holder.binding.root.context, R.color.color_red)
                "blue" -> ContextCompat.getColor(holder.binding.root.context, R.color.color_blue)
                "green" -> ContextCompat.getColor(
                    holder.binding.root.context,
                    R.color.color_green
                )

                "brown" -> ContextCompat.getColor(
                    holder.binding.root.context,
                    R.color.color_brown
                )

                else -> ContextCompat.getColor(holder.binding.root.context, R.color.color_red)
            }
            viewData = item
            val detailAdapter = (holder as PokemonViewHolder).adapter
            rvChild.adapter = detailAdapter
            detailAdapter.submitList(item?.pokemon_v2_pokemons?.firstOrNull()?.pokemon_v2_pokemonabilities)
        }
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): DataBindingHolder<ListItemPokemonBinding> {
        return PokemonViewHolder(parent)
    }

    class PokemonViewHolder(parent: ViewGroup) :
        DataBindingHolder<ListItemPokemonBinding>(R.layout.list_item_pokemon, parent) {
        val adapter = DetailAdapter()
    }

    class DetailAdapter :
        BaseQuickAdapter<DemonPokemonQuery.Pokemon_v2_pokemonability, DataBindingHolder<ListItemPokemonDetailBinding>>() {
        override fun onBindViewHolder(
            holder: DataBindingHolder<ListItemPokemonDetailBinding>,
            position: Int,
            item: DemonPokemonQuery.Pokemon_v2_pokemonability?
        ) {
            holder.binding.apply {
                viewData = item
            }
        }

        override fun onCreateViewHolder(
            context: Context,
            parent: ViewGroup,
            viewType: Int
        ): DataBindingHolder<ListItemPokemonDetailBinding> {
            return DataBindingHolder(R.layout.list_item_pokemon_detail, parent)
        }
    }
}