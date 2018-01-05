package net.yuzumone.recordachi.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(foreignKeys = [(ForeignKey(
        entity = Category::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("category_id")))])
data class Event(
        @PrimaryKey val id: String = UUID.randomUUID().toString(),
        var name: String,
        @ColumnInfo(name = "category_id") var categoryId: String,
        @ColumnInfo(typeAffinity = ColumnInfo.BLOB) var image: ByteArray? = null
)