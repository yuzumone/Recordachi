package net.yuzumone.recordachi.view

import android.databinding.BindingAdapter
import android.widget.TextView
import java.text.DateFormat
import java.util.*

object BindingAdapter {

    @BindingAdapter("time")
    @JvmStatic
    fun setTime(view: TextView, time: Long) {
        view.text = DateFormat.getDateTimeInstance().format(Date(time))
    }
}