package com.shpp.eorlov.rickandmorty.ui.start

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shpp.eorlov.rickandmorty.model.CharacterModel
import com.shpp.eorlov.rickandmorty.retrofit.RestClient
import com.shpp.eorlov.rickandmorty.utils.Results
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class StartViewModel @Inject constructor(
    private val client: RestClient
) : ViewModel() {

    val charactersListLiveData = MutableLiveData<MutableList<CharacterModel>>(ArrayList())
    val loadingProgressLiveData = MutableLiveData<Int>()
    val loadEventLiveData = MutableLiveData<Results>()

    private var currentPage = 1

    init {
        getAllCharacters()
    }

    private fun getAllCharacters() {
        viewModelScope.launch {
            val allCharactersList = ArrayList<CharacterModel>()
            do {
                loadEventLiveData.postValue(Results.LOADING)
                val response = try {
                    client.getInstance()?.getCharactersFromPage(currentPage++)
                } catch (exception: IOException) {
                    loadEventLiveData.postValue(Results.INTERNET_ERROR)
                    return@launch
                } catch (exception: HttpException) {
                    loadEventLiveData.postValue(Results.UNEXPECTED_RESPONSE)
                    return@launch
                }
                if (response?.isSuccessful == true && response.body() != null) {
                    val responseBody = response.body()!!
                    allCharactersList.addAll(responseBody.results)
                    loadingProgressLiveData.value =
                        (currentPage.toFloat() / responseBody.info.pages * 100).toInt()
                } else {
                    loadEventLiveData.postValue(Results.NOT_SUCCESSFUL_RESPONSE)
                }

            } while (response?.body()!!.info?.next != null) //Check if the next page exists

            charactersListLiveData.postValue(allCharactersList)
        }
    }
}