package com.homan.huang.pagingdemo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.homan.huang.pagingdemo.data.entity.Setting


@Dao
interface SettingDao {

    // delete all
    @Query("DELETE FROM setting")
    suspend fun deleteAll()

    // get setting
    @Query("SELECT * FROM setting WHERE id = 1")
    suspend fun getPageSetting(): Setting

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun innerInsert(limit: Setting)

    suspend fun insertSetting(rowsPerPageSet: Int) =
        innerInsert(Setting(1, rowsPerPageSet))
}

