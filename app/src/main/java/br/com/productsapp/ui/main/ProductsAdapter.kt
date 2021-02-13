package br.com.productsapp.ui.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.productsapp.data.model.Products
import javax.inject.Inject

class ProductsAdapter (
    var list: List<Products> = emptyList()
): RecyclerView.Adapter<ProductsViewHolder>() {

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}