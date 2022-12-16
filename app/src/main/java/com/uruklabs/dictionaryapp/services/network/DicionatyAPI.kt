package com.uruklabs.dictionaryapp.services.network

import com.uruklabs.dictionaryapp.models.respondeModels.DicionaryReponse
import com.uruklabs.dictionaryapp.models.respondeModels.DicionaryReponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DicionatyAPI {

    @GET("entries/en/{word}")
    suspend fun getWord(@Path("word")word: String): ArrayList<DicionaryReponseItem>



}