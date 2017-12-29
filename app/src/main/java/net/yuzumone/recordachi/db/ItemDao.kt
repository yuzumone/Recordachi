package net.yuzumone.recordachi.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import net.yuzumone.recordachi.model.Item

@Dao
interface ItemDao {

    @Query("SELECT * FROM items")
    fun allItems(): LiveData<List<Item>>

    @Query("SELECT * FROM items WHERE itemId = :id")
    fun load(id: String): LiveData<Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Item)

    @Delete
    fun delete(item: Item)
}