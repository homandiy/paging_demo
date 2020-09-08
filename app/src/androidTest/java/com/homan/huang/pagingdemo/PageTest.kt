package com.homan.huang.pagingdemo

import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.homan.huang.pagingdemo.data.dao.FoodDao
import com.homan.huang.pagingdemo.data.dao.PageKeyDao
import com.homan.huang.pagingdemo.data.dao.SettingDao
import com.homan.huang.pagingdemo.data.page.FoodPagingSource
import com.homan.huang.pagingdemo.data.page.FoodRemoteMediator
import com.homan.huang.pagingdemo.network.FoodService
import com.homan.huang.pagingdemo.data.room.FoodDatabase
import com.homan.huang.pagingdemo.data.room.FoodRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

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

    private lateinit var foodPageSource: FoodPagingSource
    private lateinit var foodRepo: FoodRepository

    val DEFAULT_ROW_PAGE = 10

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

        foodRepo = FoodRepository(db, mService)
        // paging source
        runBlocking {
            foodPageSource = FoodPagingSource(mService, foodRepo.getPageSize())
        }
    }

    @Test
    @Throws(Exception::class)
    fun foodServiceTest() {
        runBlocking {
            var testRow = 29
            var result = mService.getFoods(0, testRow)
            assertEquals(testRow, result.size)

            testRow = 400
            result = mService.getFoods(0, testRow)
            assertEquals(FoodService().maxRange, result.size)

        }
    }

    @Test
    @Throws(Exception::class)
    fun pageSettingTest() {
        runBlocking {
            // Check prepopulate data
            val count = settingDao.getCount()
            assertEquals(0, count)

            if (count == 0) settingDao.insertSetting(DEFAULT_ROW_PAGE)

            var rowsPerPageLimit = settingDao.getPageSetting()
            assertEquals(DEFAULT_ROW_PAGE, rowsPerPageLimit)

            val mSetting = 15
            settingDao.insertSetting(mSetting)
            rowsPerPageLimit = settingDao.getPageSetting()
            assertEquals(mSetting, rowsPerPageLimit)
        }
    }

    @InternalCoroutinesApi
    @Test
    @Throws(Exception::class)
    fun foodDao_GetAllFood_FlowTest() {
        runBlocking {
            foodDao.clearAllFood()
            var testRow = 29
            var result = mService.getFoods(0, testRow)
            foodDao.insertAllFood(result)

            val latch = CountDownLatch(1)
            val job = async(Dispatchers.IO) {
                foodDao.getAllFood().collect {
                    assertEquals(testRow, it.size)
                    latch.countDown()
                }
            }
            latch.await()
            job.cancelAndJoin()
        }
    }

    @Test
    fun testMyFlow() {
        runBlocking {
            foodPageSource.load(
                    PagingSource.LoadParams.Refresh(
                            null,
                            10,
                            true,
                            10))

            val pager = foodRepo.fetchFoodList()





        }
    }

    @ExperimentalPagingApi
    @Test
    @Throws(Exception::class)
    fun remoteMediatorTest() {
        runBlocking {
            val mMediator = FoodRemoteMediator(db, mService)

        }
    }


    companion object {
        private val TAG = "MYLOG FsTest"
        fun lgd(s: String) = Log.d(TAG, s)
    }
}