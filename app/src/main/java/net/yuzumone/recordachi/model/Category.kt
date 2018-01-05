package net.yuzumone.recordachi.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity
data class Category(
        @PrimaryKey val id: String = UUID.randomUUID().toString(),
        var name: String
)