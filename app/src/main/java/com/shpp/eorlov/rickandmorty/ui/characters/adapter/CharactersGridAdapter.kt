package com.shpp.eorlov.rickandmorty.ui.characters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.shpp.eorlov.rickandmorty.ui.characters.adapter.viewHolder.ContactsViewHolder
import com.shpp.eorlov.rickandmorty.databinding.TableItemCharacterBinding
import com.shpp.eorlov.rickandmorty.model.CharacterModel


class CharactersGridAdapter() : ListAdapter<CharacterModel, ContactsViewHolder>(CharacterItemDiffCallback()) {

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        return ContactsViewHolder(
            TableItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun getItemId(position: Int): Long = position.toLong()
}