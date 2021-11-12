package com.shpp.eorlov.rickandmorty.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shpp.eorlov.rickandmorty.model.CharacterModel
import kotlinx.coroutines.launch
import com.shpp.eorlov.rickandmorty.model.CharactersListModel
import com.shpp.eorlov.rickandmorty.retrofit.RestClient
import com.shpp.eorlov.rickandmorty.utils.Results
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SharedViewModel @Inject constructor(
    private val client: RestClient
) : ViewModel() {

    val charactersListLiveData = MutableLiveData<MutableList<CharacterModel>>(ArrayList())
    val loadEventLiveData = MutableLiveData<Results>()

    private val charactersFromCurrentPageLiveData = MutableLiveData<CharactersListModel>()

    private var currentPage = 1

    init {
        getAllCharacters()
    }

    private fun getAllCharacters() {

        viewModelScope.launch {
            do {
                val currentList = charactersListLiveData.value
                loadEventLiveData.postValue(Results.LOADING)
                val response = try {
                    client.getInstance()?.getAllCharacters(currentPage++)
                } catch (exception: IOException) {
                    loadEventLiveData.postValue(Results.INTERNET_ERROR)
                    return@launch
                } catch (exception: HttpException) {
                    loadEventLiveData.postValue(Results.UNEXPECTED_RESPONSE)
                    return@launch
                }
                if (response?.isSuccessful == true && response.body() != null) {
                    val responseBody = response.body()!!
                    currentList?.addAll(responseBody.results)

                    charactersListLiveData.postValue(currentList!!)
                    charactersFromCurrentPageLiveData.postValue(responseBody)
                    loadEventLiveData.postValue(Results.OK)
                } else {
                    loadEventLiveData.postValue(Results.NOT_SUCCESSFUL_RESPONSE)
                }
            } while (response?.body()!!.info?.next != null) //Check if the next page exists
        }
    }
}