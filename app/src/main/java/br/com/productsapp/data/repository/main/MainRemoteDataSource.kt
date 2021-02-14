package br.com.productsapp.data.repository.main

import androidx.annotation.WorkerThread
import br.com.productsapp.data.model.ProductsResponse
import br.com.productsapp.data.source.remote.Resource
import br.com.productsapp.data.source.remote.service.APIClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRemoteDataSource @Inject constructor(private var api: APIClient) {

    @WorkerThread
    suspend fun getAllProducts(): Resource<ProductsResponse> = withContext(Dispatchers.IO) {
        val callService = api.service.getAllProducts()
        return@withContext api.runAsyncDeferredAwaited(callService)
    }

}