package com.shpp.eorlov.rickandmorty.model

data class CharactersListModel(
    val info: Info,
    val results: List<CharacterModel>
)