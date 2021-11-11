package com.shpp.eorlov.rickandmorty.retrofit

import com.shpp.eorlov.rickandmorty.model.CharactersList
import retrofit2.Response
import retrofit2.http.GET


interface API {
    @GET("/api/character")
    suspend fun getAllCharacters(): Response<CharactersList>
}
