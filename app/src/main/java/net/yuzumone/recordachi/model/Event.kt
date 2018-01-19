package net.yuzumone.recordachi.model

import android.arch.persistence.room.*
import java.util.*

@Entity(indices = [Index("category_id")],
        foreignKeys = [(ForeignKey(
        entity = Category::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("category_id"),
        onDelete = ForeignKey.NO_ACTION))])
data class Event(
        @PrimaryKey val id: String = UUID.randomUUID().toString(),
        var name: String,
        @ColumnInfo(name = "category_id") var categoryId: String,
        @ColumnInfo(typeAffinity = ColumnInfo.BLOB) var image: ByteArray? = null
)