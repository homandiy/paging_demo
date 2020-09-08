package com.homan.huang.pagingdemo.data.room

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingData
import com.homan.huang.pagingdemo.data.entity.Food
import com.homan.huang.pagingdemo.data.page.SettingConfig
import com.homan.huang.pagingdemo.data.page.FoodPagingSource
import com.homan.huang.pagingdemo.network.FoodService
import com.homan.huang.stockrestapi.dagger.qualifier.DatabaseTypeEnum
import com.homan.huang.stockrestapi.dagger.qualifier.DatabaseTypeEnum.DATABASE
import com.homan.huang.stockrestapi.dagger.qualifier.FoodDB
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FoodRepository @Inject constructor(
    @FoodDB(DATABASE)
    private val db: FoodDatabase,
    @FoodDB(DatabaseTypeEnum.BACKEND)
    private val foodService: FoodService
) {
    private var pgSize = DEFAULT_ROW_SIZE

    suspend fun fetchFoodList(): Flow<PagingData<Food>> =
        Pager(SettingConfig().setPageSize(getPageSize())) {
            FoodPagingSource(foodService, pgSize)
        }.flow

    // get row size per page
    suspend fun getPageSize(): Int {
        val settingDao = db.settingDao
        if (settingDao.getCount() == 0)
            settingDao.insertSetting(DEFAULT_ROW_SIZE)
        else
            pgSize = settingDao.getPageSetting()!!
        return pgSize
    }

    companion object {
        val tag = "MYLOG FoodRepo"
        fun lgd(s:String) = Log.d(tag, s)

        const val DEFAULT_ROW_SIZE = 10
    }
}