package com.homan.huang.pagingdemo.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// page data wrapper
@Entity (
    tableName = "pagekey",
    indices = [Index(value = ["foodId"], unique = true)],
)
data class PageKey(
    val foodId: Long,
    val prevKey: Int?,
    val nextKey: Int?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}