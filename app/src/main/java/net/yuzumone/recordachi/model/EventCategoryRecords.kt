package net.yuzumone.recordachi.model

import android.arch.persistence.room.Relation

data class EventCategoryRecords(
        var id: String,
        var eventName: String,
        var categoryName: String,
        var image: ByteArray? = null,
        @Relation(
                parentColumn = "id",
                entityColumn = "event_id",
                entity = Record::class
        )
        var records: List<Record>
) {
        constructor(id: String, eventName: String, categoryName: String, image: ByteArray?) :
                this(id, eventName, categoryName, image, ArrayList())
}