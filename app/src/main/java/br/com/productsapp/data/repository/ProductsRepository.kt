package br.com.productsapp.data.repository

import androidx.annotation.WorkerThread
import br.com.productsapp.data.model.ProductsResponse
import br.com.productsapp.data.source.remote.Resource
import br.com.productsapp.data.source.remote.service.APIClient
import br.com.productsapp.data.source.remote.service.ServiceGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductsRepository @Inject constructor(private var serviceGenerator: ServiceGenerator) {

    @WorkerThread
    suspend fun getAllProducts(): Resource<ProductsResponse> = withContext(Dispatchers.IO) {
        val callService = serviceGenerator.createService(APIClient::class.java).getAllProducts()
        return@withContext serviceGenerator.runAsyncDeferredAwaited(callService)
    }

}