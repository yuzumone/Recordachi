package net.yuzumone.recordachi.view

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import net.yuzumone.recordachi.R
import net.yuzumone.recordachi.databinding.ItemEventBinding
import net.yuzumone.recordachi.model.Event

class EventAdapter : BindingRecyclerAdapter<Event, ItemEventBinding>() {
    override fun createBinding(parent: ViewGroup): ItemEventBinding {
        return DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_event,
                parent, false)
    }

    override fun bind(binding: ItemEventBinding, item: Event) {
        binding.event = item
    }
}