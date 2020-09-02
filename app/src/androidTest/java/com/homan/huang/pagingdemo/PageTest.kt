package com.homan.huang.pagingdemo

import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.homan.huang.pagingdemo.data.dao.FoodDao
import com.homan.huang.pagingdemo.data.dao.PageKeyDao
import com.homan.huang.pagingdemo.data.dao.SettingDao
import com.homan.huang.pagingdemo.network.FoodService
import com.homan.huang.pagingdemo.data.room.FoodDatabase
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class PageTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var context: Context
    private lateinit var mService: FoodService
    private lateinit var db: FoodDatabase
    private lateinit var foodDao: FoodDao
    private lateinit var keyDao: PageKeyDao
    private lateinit var settingDao: SettingDao

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()

        db = Room.inMemoryDatabaseBuilder(
            context, FoodDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        // Dao
        foodDao = db.foodDao
        keyDao = db.keyDao
        settingDao = db.settingDao

        // backend service
        mService = FoodService()
    }

    @Test
    @Throws(Exception::class)
    fun foodServiceTest() {
        runBlocking {
            val result = mService.getFoods()
            for (item in result) { lgd("$item") }
            assertEquals(result.size, mService.maxRange)
        }
    }

    @Test
    @Throws(Exception::class)
    fun pageSettingTest() {
        runBlocking {
            val rowsPerPageSet = 10
            settingDao.insertSetting(rowsPerPageSet)
            val rowsPerPageLimit = settingDao.getPageSetting().rows_page
            assertEquals(rowsPerPageSet, rowsPerPageLimit)
        }
    }

    @ExperimentalPagingApi
    @Test
    @Throws(Exception::class)
    fun foodPagingSourceTest() {
        runBlocking {


        }
    }


    companion object {
        private val TAG = "MYLOG FsTest"
        fun lgd(s: String) = Log.d(TAG, s)
    }
}