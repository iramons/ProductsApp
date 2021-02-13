package br.com.productsapp.commom.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

class BaseRecyclerViewAdapter  @Inject constructor(
    private val IItemTypeFactory : IItemTypeFactory,
    private val items : ArrayList<BaseItemModel> = arrayListOf()
) : RecyclerView.Adapter<BaseViewHolder<BaseItemModel>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BaseItemModel> {
        val view = inflateView(viewType, parent)
        return IItemTypeFactory.createViewHolder(view, viewType) as BaseViewHolder<BaseItemModel>
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BaseViewHolder<BaseItemModel>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type(IItemTypeFactory)
    }
    private fun inflateView(viewType: Int, viewGroup:ViewGroup ) : View {
        return LayoutInflater.from(viewGroup.context).inflate(viewType,viewGroup, false)
    }

    fun addItems(items: List<BaseItemModel>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}