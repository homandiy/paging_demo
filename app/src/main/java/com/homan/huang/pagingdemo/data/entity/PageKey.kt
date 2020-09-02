package com.homan.huang.pagingdemo.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// page data wrapper
@Entity (tableName = "pagekey")
data class PageKey(
    @PrimaryKey
    val foodId: Long,
    val prevKey: Int?,
    val nextKey: Int?,
    var pageMax: Int?
)