package com.shpp.eorlov.rickandmorty.ui.characters.adapter.viewHolder

import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.shpp.eorlov.rickandmorty.databinding.TableItemCharacterBinding
import com.shpp.eorlov.rickandmorty.model.CharacterModel

class ContactsViewHolder(
    private val binding: TableItemCharacterBinding,
) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var character: CharacterModel

    fun bindTo(character: CharacterModel) {
        this.character = character

        character.apply {
            binding.simpleDraweeViewCharacterImage.setImageURI(character.image)
            binding.textViewName.text = character.name
            binding.textViewSpecies.text = character.species
        }

        setListeners()
    }

    private fun setListeners() {
        binding.apply {
            binding.constraintLayoutCharacterItem.setOnClickListener {
                //Go to detail view
            }
        }
    }
}