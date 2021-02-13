package br.com.productsapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import br.com.productsapp.BaseApp
import br.com.productsapp.R
import br.com.productsapp.commom.base.BaseViewHolder
import br.com.productsapp.data.model.Products
import com.bumptech.glide.Glide

class ProductsViewHolder(view: View) : BaseViewHolder<Products>(view) {

    private val ivImage: ImageView = itemView.findViewById(R.id.iv_image)

    companion object {
        val layoutId: Int
            get() = R.layout.item_product

        fun create(parent: ViewGroup): ProductsViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(layoutId, parent, false)
            return ProductsViewHolder(view)
        }
    }

    override fun bind(item: Products) {
        Glide.with(itemView.context)
            .applyDefaultRequestOptions(BaseApp.glideOptions)
            .load(item.imageURL)
            .into(ivImage)
    }
}