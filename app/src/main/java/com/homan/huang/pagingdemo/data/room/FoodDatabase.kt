package com.homan.huang.pagingdemo.data.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.homan.huang.pagingdemo.data.dao.FoodDao
import com.homan.huang.pagingdemo.data.dao.PageKeyDao
import com.homan.huang.pagingdemo.data.dao.SettingDao
import com.homan.huang.pagingdemo.data.entity.Food
import com.homan.huang.pagingdemo.data.entity.PageKey
import com.homan.huang.pagingdemo.data.entity.Setting

@Database(
    entities = [
        Food::class,
        PageKey::class,
        Setting::class
    ],
    version = 1,
    exportSchema = false
)
abstract class FoodDatabase: RoomDatabase() {
    abstract val foodDao: FoodDao
    abstract val keyDao: PageKeyDao
    abstract val settingDao: SettingDao

    companion object {
        private val TAG = "MYLOG FoodDb"
        fun lgd(s: String) = Log.d(TAG, s)

        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: FoodDatabase? = null

        const val DB_NAME = "food_page_demo"

        fun getDatabase(context: Context): FoodDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodDatabase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration()
                    //.addCallback(BloombergDbCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}