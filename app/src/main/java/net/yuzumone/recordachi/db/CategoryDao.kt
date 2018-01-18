package net.yuzumone.recordachi.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import net.yuzumone.recordachi.model.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM Category ")
    fun getAllCategory(): LiveData<List<Category>>

    @Query("SELECT * FROM Category WHERE id = :id ")
    fun load(id: String): LiveData<Category>

    @Query("SELECT * FROM Category WHERE name = 'none' ")
    fun noneCategory(): LiveData<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: Category)

    @Delete
    fun delete(category: Category)
}