package br.com.productsapp.data.repository.main

import br.com.productsapp.data.model.ProductsResponse
import br.com.productsapp.data.source.remote.Resource

interface MainDataSource {
    suspend fun getAllProducts(): Resource<ProductsResponse>
    fun save(allProducts: ProductsResponse?)
}