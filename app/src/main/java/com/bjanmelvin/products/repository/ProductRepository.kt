package com.bjanmelvin.products.repository

import com.bjanmelvin.products.datasource.local.dao.ProductDao
import com.bjanmelvin.products.datasource.remote.ApiService
import com.bjanmelvin.products.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class ProductRepository(
    private val mApiService: ApiService,
    private val mDataDao: ProductDao) {

    fun getProductData(skip: Int = 0): Flow<List<Product>> = flow {
        try{
            // Get Data from API
            val data = mApiService.getProducts(skip = skip)
            // Insert Data to Local
            mDataDao.insertProducts(data.products)
            emit(data.products)
        }  catch (e: Exception) {
            // Get Data from Local
            val cachedData = mDataDao.getProducts(skip = skip)
            emit(cachedData)
        }
    }.flowOn(Dispatchers.IO)

}