package com.shpp.eorlov.rickandmorty.ui.characters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.shpp.eorlov.rickandmorty.ui.characters.adapter.viewHolder.CharactersViewHolder
import com.shpp.eorlov.rickandmorty.databinding.TableItemCharacterBinding
import com.shpp.eorlov.rickandmorty.model.CharacterModel
import com.shpp.eorlov.rickandmorty.ui.characters.adapter.listeners.CharacterClickListener


class CharactersGridAdapter(
    private val characterClickListener: CharacterClickListener
) : ListAdapter<CharacterModel, CharactersViewHolder>(CharacterItemDiffCallback()) {

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        return CharactersViewHolder(
            TableItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            characterClickListener
        )
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun getItemId(position: Int): Long = position.toLong()
}