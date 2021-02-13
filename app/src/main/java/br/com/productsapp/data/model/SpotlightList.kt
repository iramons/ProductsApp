package br.com.productsapp.data.model

import br.com.productsapp.commom.base.BaseItemModel
import br.com.productsapp.commom.base.IItemTypeFactory

data class SpotlightList(
        val header: String? = null,
        val items: List<Spotlight>
) : BaseItemModel() {
    override fun type(IItemTypeFactory: IItemTypeFactory): Int {
        return IItemTypeFactory.type(this)
    }
}
