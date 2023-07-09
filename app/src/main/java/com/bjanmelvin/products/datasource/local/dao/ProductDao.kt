package com.bjanmelvin.products.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bjanmelvin.products.models.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAllProducts(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Product>)
}
