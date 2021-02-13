package br.com.productsapp.commom.base

abstract class BaseItemModel {
    abstract fun type(itemTypeFactory: IItemTypeFactory) : Int
}