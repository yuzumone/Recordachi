package net.yuzumone.recordachi.view

import android.view.View

interface ItemClickCallback {
    fun onItemClick(view: View, position: Int)
}