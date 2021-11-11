package com.shpp.eorlov.rickandmorty.ui.characters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.shpp.eorlov.rickandmorty.model.CharactersList
import com.shpp.eorlov.rickandmorty.retrofit.RestClient
import com.shpp.eorlov.rickandmorty.utils.Results
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CharacterViewModel @Inject constructor(
    private val client: RestClient
) : ViewModel() {
    val charactersListLiveData = MutableLiveData<CharactersList>()
    val loadEventLiveData = MutableLiveData<Results>()

    init {
        getAllCharacters()
    }

    private fun getAllCharacters() {
        viewModelScope.launch {
            loadEventLiveData.postValue(Results.LOADING)
            val response = try {
                client.getInstance()?.getAllCharacters()
            } catch (exception: IOException) {
                loadEventLiveData.postValue(Results.INTERNET_ERROR)
                return@launch
            } catch (exception: HttpException) {
                loadEventLiveData.postValue(Results.UNEXPECTED_RESPONSE)
                return@launch
            }
            if (response?.isSuccessful == true && response.body() != null) {
                charactersListLiveData.postValue(response.body()!!)
                loadEventLiveData.postValue(Results.OK)
            } else {
                loadEventLiveData.postValue(Results.NOT_SUCCESSFUL_RESPONSE)
            }
        }
    }
}