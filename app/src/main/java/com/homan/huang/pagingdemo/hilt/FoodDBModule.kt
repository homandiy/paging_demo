package com.homan.huang.pagingdemo.hilt

import android.content.Context
import com.homan.huang.pagingdemo.network.FoodService
import com.homan.huang.pagingdemo.data.dao.FoodDao
import com.homan.huang.pagingdemo.data.dao.PageKeyDao
import com.homan.huang.pagingdemo.data.dao.SettingDao
import com.homan.huang.pagingdemo.data.room.FoodDatabase
import com.homan.huang.pagingdemo.data.room.FoodRepository
import com.homan.huang.stockrestapi.dagger.qualifier.FoodDB
import com.homan.huang.stockrestapi.dagger.qualifier.DatabaseTypeEnum.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

// provide remote data
@Module
@InstallIn(ApplicationComponent::class)
class FoodDBModule {
    @Provides @FoodDB(DATABASE)
    fun provideFoodDatabase(@ApplicationContext context: Context
    ): FoodDatabase = FoodDatabase.getDatabase(context)

    @Provides @FoodDB(DAO)
    fun provideFoodDao(@FoodDB(DATABASE) database: FoodDatabase
    ): FoodDao = database.foodDao

    @Provides @FoodDB(DAO2)
    fun provideKeyDao(@FoodDB(DATABASE) database: FoodDatabase
    ): PageKeyDao = database.keyDao

    @Provides @FoodDB(DAO3)
    fun provideSettingDao(@FoodDB(DATABASE) database: FoodDatabase
    ): SettingDao = database.settingDao

    @Provides @FoodDB(BACKEND)
    fun provideFoodService(): FoodService = FoodService()

    @Provides @FoodDB(REPOSITORY)
    fun provideFoodRepository(
            @FoodDB(DATABASE) db: FoodDatabase,
            @FoodDB(BACKEND) service: FoodService
    ): FoodRepository = FoodRepository(db, service)


}