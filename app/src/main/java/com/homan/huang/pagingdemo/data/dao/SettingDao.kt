package com.homan.huang.pagingdemo.data.dao

import androidx.room.*
import com.homan.huang.pagingdemo.data.entity.Setting


@Dao
interface SettingDao {
    // delete all
    @Query("DELETE FROM setting")
    suspend fun deleteAll()

    @Query("SELECT COUNT(id) FROM setting")
    suspend fun getCount(): Int

    // get setting
    @Query("SELECT rows_page FROM setting WHERE id = 1")
    suspend fun getPageSetting(): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(limit: Setting)
    suspend fun insertSetting(rows: Int) = insert(Setting(1, rows))
}

