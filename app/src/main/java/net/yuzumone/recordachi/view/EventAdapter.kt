package net.yuzumone.recordachi.view

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import net.yuzumone.recordachi.R
import net.yuzumone.recordachi.databinding.ItemEventBinding
import net.yuzumone.recordachi.model.EventCategoryRecords

class EventAdapter(checkClickCallback: CheckClickCallback) : BindingRecyclerAdapter<EventCategoryRecords,
        ItemEventBinding>() {

    private var callback: CheckClickCallback = checkClickCallback

    override fun createBinding(parent: ViewGroup): ItemEventBinding {
        val binding = DataBindingUtil.inflate<ItemEventBinding>(LayoutInflater.from(parent.context),
                R.layout.item_event, parent, false)
        binding.buttonCheck.setOnClickListener {
            val event = binding.event
            if (event != null) {
                callback.onCheck(event)
            }
        }
        return binding
    }

    override fun bind(binding: ItemEventBinding, item: EventCategoryRecords) {
        binding.event = item
    }

    interface CheckClickCallback {
        fun onCheck(event: EventCategoryRecords)
    }
}