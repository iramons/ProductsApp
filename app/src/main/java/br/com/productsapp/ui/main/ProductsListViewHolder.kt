package br.com.productsapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.productsapp.R
import br.com.productsapp.commom.base.BaseViewHolder
import br.com.productsapp.data.model.ProductsList

class ProductsListViewHolder(view: View) : BaseViewHolder<ProductsList>(view) {

    private val tvTitle: TextView = itemView.findViewById(R.id.tv_product_category)
    private val recyclerview: RecyclerView = itemView.findViewById(R.id.rv_products_list)
    private lateinit var mAdapter: ProductsAdapter

    companion object {
        val layoutId: Int
            get() = R.layout.item_with_list_products

        fun create(parent: ViewGroup): ProductsViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(layoutId, parent, false)
            return ProductsViewHolder(view)
        }
    }

    override fun bind(item: ProductsList) {
        tvTitle.text = item.header
        mAdapter = ProductsAdapter(item.items)
        recyclerview.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerview.adapter = mAdapter
    }
}