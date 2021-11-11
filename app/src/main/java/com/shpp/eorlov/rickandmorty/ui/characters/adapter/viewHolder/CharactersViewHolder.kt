package com.shpp.eorlov.rickandmorty.ui.characters.adapter.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.shpp.eorlov.rickandmorty.databinding.TableItemCharacterBinding
import com.shpp.eorlov.rickandmorty.model.CharacterModel
import com.shpp.eorlov.rickandmorty.ui.characters.adapter.listeners.CharacterClickListener

class CharactersViewHolder(
    private val binding: TableItemCharacterBinding,
    private val characterClickListener: CharacterClickListener,
) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var characterModel: CharacterModel

    fun bindTo(character: CharacterModel) {
        this.characterModel = character

        character.apply {
            binding.simpleDraweeViewCharacterImage.setImageURI(character.image)
            binding.textViewName.text = character.name
            binding.textViewSpecies.text = character.species
        }

        setListeners()
    }

    private fun setListeners() {
        binding.constraintLayoutCharacterItem.setOnClickListener {
            characterClickListener.goToDetailView(characterModel)
        }
    }
}