package net.yuzumone.recordachi.model

data class EventWithCategory(
        val id: String,
        val eventName: String,
        val categoryName: String,
        val image: ByteArray?
)