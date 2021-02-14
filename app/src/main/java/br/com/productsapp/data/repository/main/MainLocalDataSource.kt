package br.com.productsapp.data.repository.main

import br.com.productsapp.data.dao.AllProductsDAO
import br.com.productsapp.data.model.ProductsResponse
import br.com.productsapp.data.source.remote.Resource
import br.com.productsapp.util.AppExecutors
import javax.inject.Inject

class MainLocalDataSource @Inject constructor(
        private val dao: AllProductsDAO,
        private val appExecutors: AppExecutors) :
        MainDataSource {

    override fun save(allProducts: ProductsResponse?) {
        appExecutors.roomThreadExecutor.execute {
            if (allProducts != null)
                dao.insert(allProducts)
        }
    }

    override suspend fun getAllProducts() : Resource<ProductsResponse> {
        TODO("Not yet implemented")
    }


}