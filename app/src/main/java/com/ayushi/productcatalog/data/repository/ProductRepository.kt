package com.ayushi.productcatalog.data.repository


import com.ayushi.productcatalog.data.model.ProductResponse
import com.ayushi.productcatalog.data.remote.ProductApi

class ProductRepository(
    private val api: ProductApi
) {

    suspend fun getProducts(): ProductResponse {
        return api.getProducts()
    }

    suspend fun searchProducts(query: String): ProductResponse {
        return api.searchProducts(query)
    }
}