package net.yuzumone.recordachi.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import net.yuzumone.recordachi.model.Item

@Database(entities = [(Item::class)], version = 1)
abstract class ItemDb : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object {
        private var instance: ItemDb? = null
        @Synchronized
        fun getInstance(context: Context): ItemDb {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                        ItemDb::class.java, "ItemDatabase").build()
            }
            return instance!!
        }
    }
}