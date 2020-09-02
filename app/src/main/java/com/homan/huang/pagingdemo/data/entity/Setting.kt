package com.homan.huang.pagingdemo.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// page data wrapper
@Entity (tableName = "setting")
data class Setting(
    @PrimaryKey
    val id: Long,
    val rows_page: Int
)