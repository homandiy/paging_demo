package com.homan.huang.pagingdemo.data.page

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.homan.huang.pagingdemo.network.FoodService
import com.homan.huang.pagingdemo.data.dao.FoodDao
import com.homan.huang.pagingdemo.data.entity.Food
import com.homan.huang.pagingdemo.data.entity.PageKey
import com.homan.huang.pagingdemo.data.room.FoodDatabase
import com.homan.huang.pagingdemo.setting.PageSetting
import com.homan.huang.stockrestapi.dagger.qualifier.DatabaseTypeEnum.*
import com.homan.huang.stockrestapi.dagger.qualifier.FoodDB
import java.lang.Thread.sleep
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalPagingApi
@Singleton
class FoodRemoteMediator @Inject constructor(
    @FoodDB(DATABASE)
    private val db: FoodDatabase,
    @FoodDB(BACKEND)
    private val foodService: FoodService
) : RemoteMediator<Int, Food>() {

    // remote data and local cache
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Food>
    ): MediatorResult {

        val page = when (loadType) {
            // First time visit or Adapter.refresh()
            // null: refresh every time to get data
            LoadType.REFRESH -> null
            // More Data in the front
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            // More Data
            LoadType.APPEND -> {
            }
        }

        try {
            val atEndofPage = true
            val foodDao = db.foodDao
            val keyDao = db.keyDao
            val settingDao = db.settingDao

            // clear database
            foodDao.clearAllFood()
            keyDao.clearPageKeys()

            // One time download
            val foodList = foodService.getFoods(
                0,
                FoodService().maxRange
            )

            // Good request but no data
            if (foodList.isEmpty())
                return MediatorResult.Success(endOfPaginationReached = false)

            // save data to food table
            foodDao.insertAllFood(foodList)

            return MediatorResult.Success(
                endOfPaginationReached = atEndofPage)
        } catch (exception: Exception) {
            // Handle errors in this block
            return MediatorResult.Error(exception)
        }
    }

}