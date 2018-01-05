package net.yuzumone.recordachi.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import net.yuzumone.recordachi.model.Record

@Dao
interface RecordDao {
    @Query("SELECT * FROM Record ")
    fun getAllRecord(): LiveData<List<Record>>

    @Query("SELECT * FROM Record WHERE id = :id ")
    fun load(id: String): LiveData<Record>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(record: Record)

    @Delete
    fun delete(record: Record)
}