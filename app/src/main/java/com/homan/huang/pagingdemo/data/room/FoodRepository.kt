package com.homan.huang.pagingdemo.data.room

import com.homan.huang.stockrestapi.dagger.qualifier.DatabaseTypeEnum.DATABASE
import com.homan.huang.stockrestapi.dagger.qualifier.FoodDB
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FoodRepository @Inject constructor(
    @FoodDB(DATABASE)
    private val db: FoodDatabase
) {

}