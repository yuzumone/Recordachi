package net.yuzumone.recordachi.view

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import net.yuzumone.recordachi.R
import net.yuzumone.recordachi.databinding.ItemRecordBinding
import net.yuzumone.recordachi.model.Record

class RecordAdapter : BindingRecyclerAdapter<Record, ItemRecordBinding>() {
    override fun createBinding(parent: ViewGroup): ItemRecordBinding {
        return DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_record,
                parent, false)
    }

    override fun bind(binding: ItemRecordBinding, item: Record) {
        binding.record = item
    }
}