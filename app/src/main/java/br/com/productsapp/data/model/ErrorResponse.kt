package br.com.productsapp.data.model

import com.squareup.moshi.Json

data class ErrorResponse (
    @field:Json(name = "message") var message: String? = null
)
