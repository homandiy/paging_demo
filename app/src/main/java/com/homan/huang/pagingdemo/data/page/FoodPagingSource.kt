package com.homan.huang.pagingdemo.data.page

import android.util.Log
import androidx.paging.PagingSource
import com.homan.huang.pagingdemo.data.dao.FoodDao
import com.homan.huang.pagingdemo.data.dao.PageKeyDao
import com.homan.huang.pagingdemo.data.entity.Food
import com.homan.huang.pagingdemo.network.FoodService
import java.io.IOException

class FoodPagingSource(
    private val foodService: FoodService,
    private val pgSize: Int
) : PagingSource<Int, Food>() {

    override suspend fun load (
        params: LoadParams<Int>
    ): LoadResult<Int, Food> {
        return try {
            val page = params.key ?:1

            // gather data
            var start = 0
            if (page > 1) start = (page-1) * pgSize
            val end = start + pgSize
            val foodList = foodService.getFoods(start, end)

            lgd("Loading page#: $page")

            // success
            LoadResult.Page(
                data = foodList,
                prevKey = if (page > 0) page-1 else null,
                nextKey = if(foodList.size < params.loadSize) null
                            else page+1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        }
    }

    companion object{
        val tag = "MYLOG PgSource"
        fun lgd(s:String) = Log.d(tag, s)
    }
}