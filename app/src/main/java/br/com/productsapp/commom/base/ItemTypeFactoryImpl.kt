package br.com.productsapp.commom.base

import android.view.View
import br.com.productsapp.data.model.*
import br.com.productsapp.ui.main.*
import java.lang.IllegalArgumentException

class ItemTypeFactoryImpl : IItemTypeFactory {

    override fun type(productsList: ProductsList): Int {
        return ProductsListViewHolder.layoutId
    }

    override fun type(spotlightList: SpotlightList): Int {
        return SpotlightListViewHolder.layoutId
    }

    override fun type(cash: Cash): Int {
        return CashViewHolder.layoutId
    }

    override fun createViewHolder(parent: View, type: Int): BaseViewHolder<*> {
        return when(type) {
            ProductsListViewHolder.layoutId -> ProductsListViewHolder(parent)
            SpotlightListViewHolder.layoutId -> SpotlightListViewHolder(parent)
            CashViewHolder.layoutId -> CashViewHolder(parent)

            else -> throw IllegalArgumentException("Unknown type")
        }
    }
}