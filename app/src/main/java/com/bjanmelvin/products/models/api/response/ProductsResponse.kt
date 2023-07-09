package com.bjanmelvin.products.models.api.response

import com.bjanmelvin.products.models.Product

data class ProductsResponse(
    val products: List<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int
)