package com.homan.huang.pagingdemo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.homan.huang.pagingdemo.data.entity.Food
import com.homan.huang.pagingdemo.data.entity.PageKey


@Dao
interface FoodDao {

    // coroutines: insert all
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllFood(food:List<Food>)

    // delete all
    @Query("DELETE FROM food")
    suspend fun clearAllFood()

    // select next of ID with # of rows limit
    @Query("SELECT * FROM food WHERE id > :id LIMIT :row ")
    suspend fun getFoodPage(id: Long, row: Int): List<Food>

}