package br.com.productsapp.commom.extensions

import com.google.gson.Gson

inline fun <reified T> T.toJsonString(): String {
    return Gson().toJson(this, T::class.java)
}

inline fun <reified T> String.fromJsonString(): T {
    return Gson().fromJson(this, T::class.java)
}