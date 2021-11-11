package com.shpp.eorlov.rickandmorty.ui.characters.adapter.listeners

import com.shpp.eorlov.rickandmorty.model.CharacterModel


interface CharacterClickListener {
    fun goToDetailView(characterModel: CharacterModel)
}