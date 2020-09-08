package com.homan.huang.pagingdemo.data.page

import androidx.paging.PagingConfig

class SettingConfig {
    private var mConfig = PagingConfig(
            pageSize = 10, // rows/page
            enablePlaceholders = true, // cache
            prefetchDistance = 3, // refresh distance
            initialLoadSize = 30, // prepare size
            maxSize = 200 // maximum of data
    )

    fun setPageSize(pgSize: Int): PagingConfig {
        mConfig = PagingConfig(
                pageSize = pgSize, // rows/page
                enablePlaceholders = true, // cache
                prefetchDistance = 3, // refresh distance
                initialLoadSize = 30, // prepare size
                maxSize = 200 // maximum of data
        )
        return mConfig
    }
}