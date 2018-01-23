package net.yuzumone.recordachi.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import net.yuzumone.recordachi.db.AppDb
import net.yuzumone.recordachi.extension.ioThread
import net.yuzumone.recordachi.model.Event
import net.yuzumone.recordachi.model.Record

class DetailViewModel(app: Application) : AndroidViewModel(app) {
    private val eventDao = AppDb.getInstance(app).eventDao()
    private val recordDao = AppDb.getInstance(app).recordDao()

    fun event(id: String): LiveData<Event> {
        return eventDao.load(id)
    }

    fun records(eventId: String): LiveData<List<Record>> {
        return recordDao.loadRecordList(eventId)
    }

    fun deleteEvent(id: String) = ioThread {
        eventDao.delete(id)
    }
}