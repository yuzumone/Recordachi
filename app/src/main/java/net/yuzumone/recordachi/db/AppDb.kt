package net.yuzumone.recordachi.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import net.yuzumone.recordachi.model.Category
import net.yuzumone.recordachi.model.Event
import net.yuzumone.recordachi.model.Record

@Database(entities = [(Event::class), (Record::class), (Category::class)], version = 1)
abstract class AppDb : RoomDatabase() {
    abstract fun eventDao(): EventDao
    abstract fun recordDao(): RecordDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        private var instance: AppDb? = null
        @Synchronized
        fun getInstance(context: Context): AppDb {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                        AppDb::class.java, "Database").build()
            }
            return instance!!
        }
    }
}