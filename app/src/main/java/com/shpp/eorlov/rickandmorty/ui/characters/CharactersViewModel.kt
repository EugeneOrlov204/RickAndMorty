package com.shpp.eorlov.rickandmorty.ui.characters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shpp.eorlov.rickandmorty.model.CharacterModel
import com.shpp.eorlov.rickandmorty.utils.SingleLiveEvent
import javax.inject.Inject

class CharactersViewModel @Inject constructor() : ViewModel() {
    val charactersListLiveData = MutableLiveData<MutableList<CharacterModel>>(ArrayList())

    fun setCharactersList(charactersList: MutableList<CharacterModel>) {
        charactersListLiveData.value = charactersList
    }
}