package com.example.check24challenge.network.api

import com.example.check24challenge.model.ProductListModel
import retrofit2.http.GET
import retrofit2.http.Url

interface ProductListApi {

    @GET
    suspend fun getProductsList(@Url url: String): ProductListModel?

}