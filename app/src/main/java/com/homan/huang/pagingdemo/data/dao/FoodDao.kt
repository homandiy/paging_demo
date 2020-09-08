package com.homan.huang.pagingdemo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.homan.huang.pagingdemo.data.entity.Food
import com.homan.huang.pagingdemo.data.entity.PageKey
import kotlinx.coroutines.flow.Flow


@Dao
interface FoodDao {
    // insert all
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllFood(food:List<Food>)

    // get all
    @Query("SELECT * FROM food")
    fun getAllFood(): Flow<List<Food>>

    // delete all
    @Query("DELETE FROM food")
    suspend fun clearAllFood()

    // select section from  next of ID address to # of rows limit
    @Query("SELECT * FROM food WHERE id > :id LIMIT :row ")
    suspend fun getFoodPage(id: Long, row: Int): List<Food>

}