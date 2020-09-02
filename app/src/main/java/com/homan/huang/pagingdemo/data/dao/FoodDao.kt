package com.homan.huang.pagingdemo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.homan.huang.pagingdemo.data.entity.Food
import com.homan.huang.pagingdemo.data.entity.PageKey


@Dao
interface FoodDao {
    // nsert all
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllFood(food:List<Food>)

    // delete all
    @Query("DELETE FROM food")
    suspend fun clearAllFood()

    // select section from  next of ID address to # of rows limit
    @Query("SELECT * FROM food WHERE id > :id LIMIT :row ")
    suspend fun getFoodPage(id: Long, row: Int): List<Food>
}