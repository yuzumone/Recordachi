package net.yuzumone.recordachi.view

import android.databinding.BindingAdapter
import android.widget.TextView
import net.yuzumone.recordachi.R
import net.yuzumone.recordachi.model.Record
import java.text.DateFormat
import java.util.*

object BindingAdapter {

    @BindingAdapter("time")
    @JvmStatic
    fun setTime(view: TextView, time: Long) {
        view.text = DateFormat.getDateTimeInstance().format(Date(time))
    }

    @BindingAdapter("records")
    @JvmStatic
    fun setRecords(view: TextView, records: List<Record>) {
        val diff = if (records.count() > 1) (records[records.lastIndex].time - records[records.lastIndex - 1].time) /
                (1000 * 60 * 60 * 24) else 0
        view.text = view.context.getString(R.string.day_format, diff)
    }
}