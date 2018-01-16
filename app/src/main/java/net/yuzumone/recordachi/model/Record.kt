package net.yuzumone.recordachi.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(foreignKeys = [(ForeignKey(
        entity = Event::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("event_id")))])
data class Record(
        @PrimaryKey val id: String = UUID.randomUUID().toString(),
        val time: Long,
        @ColumnInfo(name = "event_id") val eventId: String
)