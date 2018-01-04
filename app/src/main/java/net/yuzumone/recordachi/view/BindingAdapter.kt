package net.yuzumone.recordachi.view

import android.databinding.ViewDataBinding
import android.support.annotation.UiThread
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

abstract class BindingAdapter<T, V : ViewDataBinding> :
        RecyclerView.Adapter<BindingHolder<V>>(), Iterable<T> {

    private var items: List<T>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<V> {
        val binding = createBinding(parent)
        return BindingHolder(binding)
    }

    protected abstract fun createBinding(parent: ViewGroup): V

    override fun onBindViewHolder(holder: BindingHolder<V>, position: Int) {
        bind(holder.binding, items!![position])
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

    override fun getItemCount(): Int {
        return if (items == null) 0 else items!!.size
    }

    override fun iterator(): Iterator<T> {
        return items!!.iterator()
    }
}