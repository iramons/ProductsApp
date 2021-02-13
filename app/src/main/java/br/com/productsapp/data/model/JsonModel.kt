package br.com.productsapp.data.model

import com.google.gson.Gson

open class JsonModel {
    override fun toString() : String {
        return Gson().toJson(this, this::class.java)
    }
}