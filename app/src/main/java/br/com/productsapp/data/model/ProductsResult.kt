package br.com.productsapp.data.model

import javax.inject.Inject

sealed class ProductsResult {
    class Success(val allproducts: ProductsResponse) : ProductsResult()
    class ApiError(val code: Int, val message: String?) : ProductsResult()
    class UnknownError(val message: String?) : ProductsResult()
}
