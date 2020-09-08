package com.homan.huang.pagingdemo.data.entity

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "food")
data class Food (
    val foodname: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    companion object {
        // Diff.Util for Adapter
        val diffCallback = object: DiffUtil.ItemCallback<Food>() {
            override fun areItemsTheSame(
                oldItem: Food, newItem: Food
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Food, newItem: Food
            ): Boolean = oldItem == newItem
        }
    }
}