package net.yuzumone.recordachi.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "items")
data class Item(
        @PrimaryKey @ColumnInfo(name = "itemId") val id: String = UUID.randomUUID().toString(),
        @ColumnInfo(name = "name") val name: String,
        @ColumnInfo(name = "time") val time: Long
)