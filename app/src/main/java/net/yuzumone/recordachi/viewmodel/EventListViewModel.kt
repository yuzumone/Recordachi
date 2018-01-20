package net.yuzumone.recordachi.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import net.yuzumone.recordachi.db.AppDb
import net.yuzumone.recordachi.extension.ioThread
import net.yuzumone.recordachi.model.Record

class EventListViewModel(app: Application) : AndroidViewModel(app) {
    private val eventDao = AppDb.getInstance(app).eventDao()
    private val recordDao = AppDb.getInstance(app).recordDao()

    val allEventCategoryRecords = eventDao.getAllEventCategoryRecords()

    fun insertRecord(record: Record) = ioThread {
        recordDao.insert(record)
    }
}