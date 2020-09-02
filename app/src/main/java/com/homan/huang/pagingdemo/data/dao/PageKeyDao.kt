package com.homan.huang.pagingdemo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.homan.huang.pagingdemo.data.entity.PageKey


@Dao
interface PageKeyDao {

    // insert one
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertKey(pgKey: PageKey): Long

    // insert all
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<PageKey>)

    // search by food id
    @Query("SELECT * FROM pagekey WHERE foodId = :mId")
    suspend fun getPageByFoodId(mId: Long): PageKey?

    // delete all
    @Query("DELETE FROM pagekey")
    suspend fun clearPageKeys()

}