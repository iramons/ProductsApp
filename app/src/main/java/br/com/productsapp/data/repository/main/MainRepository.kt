package br.com.productsapp.data.repository.main

import android.content.Context
import br.com.productsapp.data.model.ProductsResponse
import br.com.productsapp.data.source.remote.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
        private val context: Context,
        private val localDataSource: MainLocalDataSource,
        private val remoteDataSource: MainRemoteDataSource
) : MainDataSource {

    override suspend fun getAllProducts(): Resource<ProductsResponse> = withContext(Dispatchers.IO) {
        remoteDataSource.getAllProducts()
    }

    override fun save(allProducts: ProductsResponse?) {
        localDataSource.save(allProducts)
    }


}