package com.shpp.eorlov.rickandmorty.ui.characters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shpp.eorlov.rickandmorty.model.CharacterModel
import javax.inject.Inject

class CharactersViewModel @Inject constructor() : ViewModel() {
    val charactersListLiveData = MutableLiveData<MutableList<CharacterModel>>(ArrayList())
    val sortedLiveData = MutableLiveData(false)

    fun setCharactersList(charactersList: MutableList<CharacterModel>) {
        charactersListLiveData.value = charactersList
    }

    fun getSortedCharacters(currentList: List<CharacterModel>): MutableList<CharacterModel> {
        val listOfNames: List<String> = currentList.map {
            it.name
        }

        val sortedListOfNames = listOfNames.sorted()
        val sortedListOfCharacters = mutableListOf<CharacterModel>()
        for (value in sortedListOfNames) {
            sortedListOfCharacters.add(currentList.first {
                it.name == value
            })
        }
        sortedLiveData.value = true

        return sortedListOfCharacters
    }
}