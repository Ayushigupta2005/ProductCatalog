package com.ayushi.productcatalog.data.remote

import retrofit2.http.Query
import com.ayushi.productcatalog.data.model.ProductResponse
import retrofit2.http.GET

interface  ProductApi {

    @GET("products")
    suspend fun getProducts(): ProductResponse

    @GET("products/search")
    suspend fun searchProducts(
        @Query("q") query: String
    ): ProductResponse
}