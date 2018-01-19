package net.yuzumone.recordachi.model

import android.arch.persistence.room.*
import java.util.*

@Entity(indices = [(Index("event_id"))],
        foreignKeys = [(ForeignKey(
        entity = Event::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("event_id"),
        onDelete = ForeignKey.CASCADE))])
data class Record(
        @PrimaryKey val id: String = UUID.randomUUID().toString(),
        val time: Long,
        @ColumnInfo(name = "event_id") val eventId: String
)