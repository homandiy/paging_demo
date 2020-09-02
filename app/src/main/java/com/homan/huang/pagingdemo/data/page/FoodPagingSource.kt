package com.homan.huang.pagingdemo.data.page

import androidx.paging.PagingSource
import com.homan.huang.pagingdemo.data.dao.FoodDao
import com.homan.huang.pagingdemo.data.dao.PageKeyDao
import com.homan.huang.pagingdemo.data.entity.Food

class FoodPagingSource(
    private val foodDao: FoodDao,
    private val keyDao: PageKeyDao
) : PagingSource<Int, Food>() {

    override suspend fun load( params: LoadParams<Int> ): LoadResult<Int, Food> {
        return try {
            val nextPage = params.key ?:0

            // gather data

            // success
            LoadResult.Page(
                data = emptyList(),
                prevKey = null, // Only paging forward.
                nextKey = 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}