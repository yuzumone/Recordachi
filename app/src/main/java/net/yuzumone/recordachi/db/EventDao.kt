package net.yuzumone.recordachi.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import net.yuzumone.recordachi.model.Event
import net.yuzumone.recordachi.model.EventWithCategory

@Dao
interface EventDao {
    @Query("SELECT * FROM Event ")
    fun getAllEvent(): LiveData<List<Event>>

    @Query("SELECT * FROM Event WHERE id = :id ")
    fun load(id: String): LiveData<Event>

    @Query("SELECT Event.id, Event.name as eventName, Category.name as categoryName, " +
            "Event.image FROM Event INNER JOIN Category ON Event.category_id = Category.id ")
    fun getAllEventWithCategory(): LiveData<List<EventWithCategory>>

    @Query("SELECT Event.id, Event.name as eventName, Category.name as categoryName, " +
            "Event.image FROM Event INNER JOIN Category ON Event.category_id = Category.id " +
            "WHERE Event.id = :id ")
    fun loadEventWithCategory(id: String): LiveData<EventWithCategory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(event: Event)

    @Delete
    fun delete(event: Event)
}