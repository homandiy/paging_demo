package com.homan.huang.pagingdemo.data.page

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.homan.huang.pagingdemo.network.FoodService
import com.homan.huang.pagingdemo.data.dao.FoodDao
import com.homan.huang.pagingdemo.data.dao.PageKeyDao
import com.homan.huang.pagingdemo.data.dao.SettingDao
import com.homan.huang.pagingdemo.data.entity.Food
import com.homan.huang.pagingdemo.setting.PageSetting
import com.homan.huang.stockrestapi.dagger.qualifier.DatabaseTypeEnum.*
import com.homan.huang.stockrestapi.dagger.qualifier.FoodDB
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalPagingApi
@Singleton
class FoodRemoteMediator @Inject constructor(
    @FoodDB(DAO)
    private val foodDao: FoodDao,
    @FoodDB(DAO2)
    private val keyDao: PageKeyDao,
    @FoodDB(DAO3)
    private val settingDao: SettingDao,
    @FoodDB(BACKEND)
    private val foodService: FoodService
) : RemoteMediator<Int, Food>() {

    // remote data and local cache
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Food>
    ): MediatorResult {

        val page = when (loadType) {
            // for 1st page
            LoadType.REFRESH -> null
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
            }
        }

        try {
            val atEndofPage = false

            // clear database
            foodDao.clearAllFood()
            keyDao.clearPageKeys()

            // get data from backend
            val foodList = foodService.getFoods()

            // save data to food table
            foodDao.insertAllFood(foodList)

            // split to pages
            val totalCount = foodList.size
            val rowPerPage = PageSetting().ROWS_PAGE

            return MediatorResult.Success(
                endOfPaginationReached = atEndofPage)
        } catch (exception: Exception) {
            // Handle errors in this block
            return MediatorResult.Error(exception)
        }
    }
}