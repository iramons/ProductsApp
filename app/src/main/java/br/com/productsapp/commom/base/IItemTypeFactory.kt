package br.com.productsapp.commom.base

import android.view.View
import br.com.productsapp.data.model.*

interface IItemTypeFactory {
    fun type(productsList: ProductsList) : Int
    fun type(spotlightList: SpotlightList) : Int
    fun type(cash: Cash) : Int

    fun createViewHolder(parent : View, type : Int) : BaseViewHolder<*>
}