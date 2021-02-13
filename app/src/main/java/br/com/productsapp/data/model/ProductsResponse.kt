package br.com.productsapp.data.model

import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class ProductsResponse(
    @PrimaryKey
    @field:Json(name = "spotlight") val spotlight: List<Spotlight>? = null,
    @field:Json(name = "products") val products: List<Products>? = null,
    @field:Json(name = "cash") val cash: Cash? = null,
) : JsonModel()