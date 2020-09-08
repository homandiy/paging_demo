package com.homan.huang.pagingdemo.network

import android.util.Log
import com.homan.huang.pagingdemo.data.entity.Food
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.collections.ArrayList

// provide data
class FoodService {
    val maxRange = 131

    // generate data
    suspend fun getFoods(startRow: Int, endRow: Int): List<Food> {
        lgd("Simulate delay network...")
        return withContext(Dispatchers.IO) {
            delay(2000)
            val data = ArrayList<Food>()
            val foodType = listOf(
                "cucumber", "cookie", "tuna",
                "apple", "pear", "jelly bean",
                "walnut", "jelly", "ice cream",
                "noodle", "garlic", "pepper",
                "wine", "chicken soup", "bean soup",
                "s\'more", "potato chip", "milk shake"
            )
            val maxSize = foodType.size

            var mEndRow = endRow
            if (endRow > maxRange) mEndRow = maxRange

            for (id in startRow until mEndRow) {
                val ranInt = (0 until maxSize).random()
                val food = foodType[ranInt]
                data.add(Food(foodname = food))
            }
            data
        }
    }

    companion object {
        const val TAG = "MYLOG FoodService"
        fun lgd(s: String) = Log.d(TAG, s)
    }
}