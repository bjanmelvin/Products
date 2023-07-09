package com.bjanmelvin.products

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.bjanmelvin.products.datasource.local.AppDatabase
import com.bjanmelvin.products.datasource.local.dao.ProductDao
import com.bjanmelvin.products.datasource.remote.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainApplication: Application() {
    lateinit var database: AppDatabase
    lateinit var retrofit: Retrofit
    lateinit var apiService: ApiService
    lateinit var productDao: ProductDao

    override fun onCreate() {
        super.onCreate()

        // Singleton Creation
        instance = this

        createDatabase()
        createDataDao()
        createRetrofit()
        createApiService()

    }

    private fun createDatabase(){
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "ProductDatabase"
        ).build()
    }

    private fun createDataDao(){
        productDao = database.productDao()
    }

    private fun createRetrofit(){
        retrofit = Retrofit.Builder()
            .baseUrl(HOST_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createApiService(){
        apiService = retrofit.create(ApiService::class.java)
    }

    companion object {
        const val HOST_API = "https://dummyjson.com/"

        const val CATALOG_DISPLAY_LIMIT = 10

        lateinit var instance: MainApplication
            private set
    }

}