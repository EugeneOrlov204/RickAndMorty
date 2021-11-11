package com.shpp.eorlov.rickandmorty.retrofit

import com.shpp.eorlov.rickandmorty.model.CharactersListModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface API {
    @GET("/api/character")
    suspend fun getAllCharacters(@Query("page") page: Int): Response<CharactersListModel>
}
