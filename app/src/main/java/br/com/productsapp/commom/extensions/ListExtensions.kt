package br.com.productsapp.commom.extensions


fun <T> Collection<T>?.isNotNullOrEmpty() = !this.isNullOrEmpty()
fun <T, Y> Map<T, Y>?.isNotNullOrEmpty() = !this.isNullOrEmpty()
fun <T> Array<T>?.isNotNullOrEmpty() = !this.isNullOrEmpty()