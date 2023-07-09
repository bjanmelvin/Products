package com.bjanmelvin.products.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bjanmelvin.products.MainApplication.Companion.CATALOG_DISPLAY_LIMIT
import com.bjanmelvin.products.models.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM products LIMIT " + CATALOG_DISPLAY_LIMIT + " OFFSET :skip")
    fun getProducts(skip: Int): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Product>)
}
