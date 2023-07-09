package com.bjanmelvin.products.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bjanmelvin.products.datasource.local.dao.ProductDao
import com.bjanmelvin.products.models.Product

@Database(entities = [Product::class], version = 1)
@TypeConverters(Product.ImagesTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}