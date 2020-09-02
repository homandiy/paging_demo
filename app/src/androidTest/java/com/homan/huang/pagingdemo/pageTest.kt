package com.homan.huang.pagingdemo

import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.homan.huang.pagingdemo.data.room.FoodRepository
import com.homan.huang.pagingdemo.network.FoodService
import com.homan.huang.pagingdemo.data.room.FoodDatabase
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.greaterThan
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class pageTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var context: Context
    private lateinit var mService: FoodService
    private lateinit var mRepo: FoodRepository
    private lateinit var db: FoodDatabase

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()

        db = Room.inMemoryDatabaseBuilder(
            context, FoodDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()

        // backend service
        mService = FoodService()
        mRepo = FoodRepository(db)
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