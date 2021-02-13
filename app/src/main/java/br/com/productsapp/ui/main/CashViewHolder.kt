package br.com.productsapp.ui.main

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import br.com.productsapp.BaseApp
import br.com.productsapp.R
import br.com.productsapp.commom.base.BaseViewHolder
import br.com.productsapp.data.model.Cash
import com.bumptech.glide.Glide

class CashViewHolder(view: View): BaseViewHolder<Cash>(view) {

    private val tvTitle: TextView = itemView.findViewById(R.id.tv_product_category)
    private val ivImage: ImageView = itemView.findViewById(R.id.iv_image)

    companion object {
        val layoutId: Int
            get() = R.layout.item_cash
    }

    override fun bind(item: Cash) {

        tvTitle.text = item.title

        Glide.with(itemView.context)
                .applyDefaultRequestOptions(BaseApp.glideOptions)
                .load(item.bannerURL)
                .into(ivImage)
    }

}