package com.shpp.eorlov.rickandmorty.ui.characters.adapter

import androidx.recyclerview.widget.DiffUtil
import com.shpp.eorlov.rickandmorty.model.CharacterModel

class CharacterItemDiffCallback : DiffUtil.ItemCallback<CharacterModel>() {
    override fun areItemsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean =
        oldItem == newItem
}