package br.com.productsapp.commom.base

internal interface IBaseContract {
    val layoutId: Int
    fun initViewModel()
    fun initUI()
    fun initObservers()
}