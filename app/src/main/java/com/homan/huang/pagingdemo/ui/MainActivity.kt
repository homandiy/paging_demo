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

package com.homan.huang.pagingdemo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.android.codelabs.paging.ui.MainViewModel
import com.homan.huang.pagingdemo.R
import com.homan.huang.pagingdemo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var mBinding: ActivityMainBinding
    private val foodAdapter by lazy { FoodAdapter() }
    private lateinit var food_rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // data binding:
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        food_rv = findViewById(R.id.food_rv)

        // RecyclerView manager
        food_rv.layoutManager = LinearLayoutManager(this)

        // adapter
        mBinding.apply {
            food_rv.adapter = foodAdapter.withLoadStateFooter(
                FoodFooterAdapter { foodAdapter.retry() })
            mainViewModel = mainViewModel
            lifecycleOwner = this@MainActivity
        }

        lifecycleScope.launch {
            // observer
            mainViewModel.postToAdapter().observe(
                    this@MainActivity,
                    {
                        // update adapter
                        foodAdapter.submitData(lifecycle, it)
                        swiperRefresh.isEnabled = false
                    }
            )
        }

        lifecycleScope.launch {
            foodAdapter.loadStateFlow
                    .collectLatest {
                    state ->
                swiperRefresh.isRefreshing =
                    state.refresh is LoadState.Loading
            }


        }

    }

    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = "Android"
    }
}
