package com.bjanmelvin.products.datasource.remote

import com.bjanmelvin.products.models.Product
import com.bjanmelvin.products.models.api.response.ProductsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("products")
    suspend fun getProducts(@Query("limit") limit: Int = 10, @Query("skip") skip: Int): ProductsResponse
}