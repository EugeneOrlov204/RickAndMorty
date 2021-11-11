package com.shpp.eorlov.rickandmorty.retrofit

import com.shpp.eorlov.rickandmorty.model.CharactersList
import dagger.Provides
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import javax.inject.Inject


class RestClient @Inject constructor(){
//    @GET("/api/character")
//    fun getAllCharacters(): Response<CharactersList>

//    companion object {
//        var retrofitService: RetrofitService? = null
//
//        @Inject
//        fun getInstance() : RetrofitService {
//            if (retrofitService == null) {
//                val retrofit = Retrofit.Builder()
//                    .baseUrl("https://rickandmortyapi.com")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//                retrofitService = retrofit.create(RetrofitService::class.java)
//            }
//            return retrofitService!!
//        }
//    }

    val BASE_URL = "https://rickandmortyapi.com"

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