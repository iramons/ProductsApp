package br.com.productsapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.productsapp.R
import br.com.productsapp.commom.base.BaseViewHolder
import br.com.productsapp.commom.extensions.isNotNullOrEmpty
import br.com.productsapp.commom.extensions.visible

import br.com.productsapp.data.model.SpotlightList


class SpotlightListViewHolder(view: View) : BaseViewHolder<SpotlightList>(view) {

    private val tvTitle: TextView = itemView.findViewById(R.id.tv_product_category)
    private val recyclerview: RecyclerView = itemView.findViewById(R.id.rv_spotlight_list)
    private lateinit var mAdapter: SpotlightAdapter

    companion object {
        val layoutId: Int
            get() = R.layout.item_with_list_spotlight

        fun create(parent: ViewGroup): ProductsViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(layoutId, parent, false)
            return ProductsViewHolder(view)
        }
    }

    override fun bind(item: SpotlightList) {

        tvTitle.visible = item.header.isNotNullOrEmpty()
        tvTitle.text = item.header

        mAdapter = SpotlightAdapter(item.items)
        recyclerview.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerview.adapter = mAdapter
    }
}