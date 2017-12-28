package net.yuzumone.recordachi.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import net.yuzumone.recordachi.model.Item

@Database(entities = [(Item::class)], version = 1)
abstract class ItemDb : RoomDatabase() {

}