package com.bjanmelvin.products.ui.catalog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bjanmelvin.products.MainApplication
import com.bjanmelvin.products.models.Product
import com.bjanmelvin.products.repository.ProductRepository

class FragCatalogViewModel : ViewModel() {

    private var repository: ProductRepository

    init {
        val apiService = MainApplication.instance.apiService
        val productDao = MainApplication.instance.productDao
        repository = ProductRepository(apiService, productDao)
    }

    val productList: MutableLiveData<MutableList<Product>> = MutableLiveData()

    suspend fun loadCatalogData(){
        repository.getProductData().collect { dataList ->
            productList.value = dataList.toMutableList()
        }
    }

    suspend fun loadmoreCatalogData(skip: Int){
        repository.getProductData(skip = skip).collect { dataList ->
            val products = productList.value
            products?.addAll(dataList)
            productList.value = products
        }
    }
}