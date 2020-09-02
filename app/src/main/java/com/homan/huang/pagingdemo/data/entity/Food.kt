package com.homan.huang.pagingdemo.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "food")
data class Food (
    val foodname: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}