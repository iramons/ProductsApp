package br.com.productsapp.data.source.remote.service

import androidx.annotation.WorkerThread
import br.com.productsapp.data.model.ErrorResponse
import br.com.productsapp.data.model.ProductsResponse
import br.com.productsapp.data.source.remote.Resource
import br.com.productsapp.util.APIPaths
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.Call
import retrofit2.http.*

interface APIClient {

    @WorkerThread
    @GET(APIPaths.products)
    fun getAllProducts(): Call<ProductsResponse>

}