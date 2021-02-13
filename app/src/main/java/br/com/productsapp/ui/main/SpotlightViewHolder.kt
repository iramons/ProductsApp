package br.com.productsapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import br.com.productsapp.BaseApp
import br.com.productsapp.R
import br.com.productsapp.commom.base.BaseViewHolder
import br.com.productsapp.data.model.Spotlight
import com.bumptech.glide.Glide


class SpotlightViewHolder(view: View) : BaseViewHolder<Spotlight>(view) {

    private val ivImage: ImageView = itemView.findViewById(R.id.iv_image)

    companion object {
        val layoutId: Int
            get() = R.layout.item_spotlight

        fun create(parent: ViewGroup): SpotlightViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(layoutId, parent, false)
            return SpotlightViewHolder(view)
        }
    }

    override fun bind(item: Spotlight) {
        Glide.with(itemView.context)
            .applyDefaultRequestOptions(BaseApp.glideOptions)
            .load(item.bannerURL)
            .into(ivImage)
    }
}