package net.yuzumone.recordachi.view

import android.databinding.BindingAdapter
import android.graphics.BitmapFactory
import android.widget.ImageView
import android.widget.TextView
import net.yuzumone.recordachi.R
import net.yuzumone.recordachi.model.Record
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapter {

    @BindingAdapter("time")
    @JvmStatic
    fun setTime(view: TextView, time: Long) {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.US)
        view.text = dateFormat.format(Date(time))
    }

    @BindingAdapter("records")
    @JvmStatic
    fun setRecords(view: TextView, records: List<Record>) {
        if (records.isNotEmpty()) {
            val diff = (System.currentTimeMillis() - records[records.lastIndex].time) / (1000 * 60 * 60 * 24)
            view.text = view.context.getString(R.string.day_format, diff)
        } else {
            view.text = view.context.getString(R.string.day_format, 0)
        }
    }

    @BindingAdapter("header")
    @JvmStatic
    fun setHeader(view: ImageView, array: ByteArray?) {
        if (array == null) return
        val bitmap = BitmapFactory.decodeByteArray(array, 0, array.size)
        view.setImageBitmap(bitmap)
    }
}