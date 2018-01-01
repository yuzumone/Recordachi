package net.yuzumone.recordachi.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import net.yuzumone.recordachi.db.ItemDb
import net.yuzumone.recordachi.extension.ioThread
import net.yuzumone.recordachi.model.Item

class ItemViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = ItemDb.getInstance(app).itemDao()

    val allItem = dao.allItems()

    fun load(id: String): LiveData<Item> {
        return dao.load(id)
    }

    fun insert(item: Item) = ioThread {
        dao.insert(item)
    }

    fun delete(item: Item) = ioThread {
        dao.delete(item)
    }
}