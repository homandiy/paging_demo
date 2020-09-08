/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.codelabs.paging.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.homan.huang.pagingdemo.data.entity.Food
import com.homan.huang.pagingdemo.data.room.FoodRepository
import com.homan.huang.stockrestapi.dagger.qualifier.DatabaseTypeEnum
import com.homan.huang.stockrestapi.dagger.qualifier.FoodDB
import kotlinx.coroutines.launch

/**
 * ViewModel for the [MainActivity] screen.
 * The ViewModel works with the [GithubRepository] to get the data.
 */
class MainViewModel @ViewModelInject constructor(
    @FoodDB(DatabaseTypeEnum.REPOSITORY)
    private val foodRepo: FoodRepository
) : ViewModel()  {

    // LiveData to PagingDataAdapter
    // Cache the data so it won't be lost in transition.
    suspend fun postToAdapter(): LiveData<PagingData<Food>> =
        foodRepo.fetchFoodList().cachedIn(viewModelScope).asLiveData()


}
