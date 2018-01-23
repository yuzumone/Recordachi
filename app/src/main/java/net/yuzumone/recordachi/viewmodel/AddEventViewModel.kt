package net.yuzumone.recordachi.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import net.yuzumone.recordachi.db.AppDb
import net.yuzumone.recordachi.extension.ioThread
import net.yuzumone.recordachi.model.Category
import net.yuzumone.recordachi.model.Event
import net.yuzumone.recordachi.model.Record

class AddEventViewModel(app: Application) : AndroidViewModel(app) {
    private val eventDao = AppDb.getInstance(app).eventDao()
    private val recordDao = AppDb.getInstance(app).recordDao()
    private val categoryDao = AppDb.getInstance(app).categoryDao()

    val noneCategory = categoryDao.noneCategory()

    fun insert(event: Event, record: Record) = ioThread {
        eventDao.insert(event)
        recordDao.insert(record)
    }

    fun insert(category: Category, event: Event, record: Record) = ioThread {
        categoryDao.insert(category)
        eventDao.insert(event)
        recordDao.insert(record)
    }

    fun insertEvent(event: Event) = ioThread {
        eventDao.insert(event)
    }

    fun insertRecord(record: Record) = ioThread {
        recordDao.insert(record)
    }

    fun insertCategory(category: Category) = ioThread {
        categoryDao.insert(category)
    }
}