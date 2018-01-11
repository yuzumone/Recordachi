package net.yuzumone.recordachi.view

import android.view.View

interface ItemLongClickCallback {
    fun onItemLongClick(view: View, position: Int)
}