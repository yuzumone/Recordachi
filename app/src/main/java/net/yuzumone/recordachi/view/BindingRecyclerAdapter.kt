package net.yuzumone.recordachi.view

import android.databinding.ViewDataBinding
import android.support.annotation.UiThread
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

abstract class BindingRecyclerAdapter<T, V : ViewDataBinding> :
        RecyclerView.Adapter<BindingHolder<V>>(), Iterable<T> {

    private var items: List<T>? = null
    private var clickCallback: ItemClickCallback? = null
    private var longClickCallback: ItemLongClickCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<V> {
        val binding = createBinding(parent)
        return BindingHolder(binding)
    }

    protected abstract fun createBinding(parent: ViewGroup): V

    override fun onBindViewHolder(holder: BindingHolder<V>, position: Int) {
        bind(holder.binding, items!![position])
        holder.binding.root.setOnClickListener {
            clickCallback?.onItemClick(holder.itemView, position)
        }
        holder.binding.root.setOnLongClickListener {
            longClickCallback?.onItemLongClick(holder.itemView, position)
            true
        }
        holder.binding.executePendingBindings()
    }

    protected abstract fun bind(binding: V, item: T)

    @UiThread
    fun update(update: List<T>?) {
        if (items == null) {
            if (update == null) {
                return
            }
            items = update
            notifyDataSetChanged()
        } else if (update == null) {
            val oldSize = items!!.size
            items = null
            notifyItemRangeRemoved(0, oldSize)
        } else {
            items = update
            notifyDataSetChanged()
        }
    }

    fun getItem(position: Int): T {
        return items!![position]
    }

    fun setItemClickCallback(callback: ItemClickCallback) {
        this.clickCallback = callback
    }

    fun setItemLongClickCallback(callback: ItemLongClickCallback) {
        this.longClickCallback = callback
    }

    override fun getItemCount(): Int {
        return if (items == null) 0 else items!!.size
    }

    override fun iterator(): Iterator<T> {
        return items!!.iterator()
    }
}