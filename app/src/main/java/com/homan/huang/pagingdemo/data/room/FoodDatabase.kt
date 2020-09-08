package com.homan.huang.pagingdemo.data.room

import android.content.Context
import android.provider.Settings
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.homan.huang.pagingdemo.data.dao.FoodDao
import com.homan.huang.pagingdemo.data.dao.PageKeyDao
import com.homan.huang.pagingdemo.data.dao.SettingDao
import com.homan.huang.pagingdemo.data.entity.Food
import com.homan.huang.pagingdemo.data.entity.PageKey
import com.homan.huang.pagingdemo.data.entity.Setting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Database(
    entities = [
        Food::class,
        PageKey::class,
        Setting::class
    ],
    version = 2,
    exportSchema = false
)
abstract class FoodDatabase: RoomDatabase() {
    abstract val foodDao: FoodDao
    abstract val keyDao: PageKeyDao
    abstract val settingDao: SettingDao

    companion object {
        private val TAG = "MYLOG FoodDb"
        fun lgd(s: String) = Log.d(TAG, s)

        const val DEFAULT_ROWS_PAGE = 10

        @Volatile
        private var INSTANCE: FoodDatabase? = null

        const val DB_NAME = "food_page_demo"

        private fun DefaultSettingCallback(context: Context) =
            object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    GlobalScope.launch(Dispatchers.IO) {
                        val settingDao =
                                getDatabase(context).settingDao
                        settingDao.insertSetting(DEFAULT_ROWS_PAGE)
                    }
                }
            }

        fun getDatabase(context: Context): FoodDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodDatabase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration()
                    .addCallback(DefaultSettingCallback(context))
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // from version 1 to version 2
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("CREATE TABLE IF NOT EXISTS `setting` (" +
                        "`id` INTEGER, " +
                        "`rows_page` INTEGER DEFAULT 0, " +
                        "PRIMARY KEY(`id`)" +
                        ")")
            }
        }
    }
}