package com.shpp.eorlov.rickandmorty.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RestClient @Inject constructor(){

    private val BASE_URL = "https://rickandmortyapi.com"

    private var service: API? = null


    fun getInstance() : API? {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(API::class.java)

        return service
    }
}