package br.com.productsapp.ui.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.productsapp.data.model.Products
import br.com.productsapp.data.model.Spotlight
import javax.inject.Inject

class SpotlightAdapter(
    var list: List<Spotlight> = emptyList()
): RecyclerView.Adapter<SpotlightViewHolder>() {

    override fun onBindViewHolder(holder: SpotlightViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpotlightViewHolder {
        return SpotlightViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}