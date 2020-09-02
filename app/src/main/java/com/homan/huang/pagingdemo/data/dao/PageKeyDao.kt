package com.homan.huang.pagingdemo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.homan.huang.pagingdemo.data.entity.PageKey


@Dao
interface PageKeyDao {
    // insert all
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<PageKey>)

    // delete all
    @Query("DELETE FROM pagekey")
    suspend fun clearPageKeys()

    // search by next key
    @Query("SELECT * FROM pagekey WHERE nextKey = :nxKey")
    suspend fun getNextPage(nxKey: Int): PageKey?
}