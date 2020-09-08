package com.homan.huang.pagingdemo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android.codelabs.paging.ui.MainViewModel
import com.homan.huang.pagingdemo.R
import com.homan.huang.pagingdemo.data.entity.Food
import com.homan.huang.pagingdemo.databinding.ActivityMainBinding
import com.homan.huang.pagingdemo.databinding.FoodItemViewBinding
import com.homan.huang.pagingdemo.ui.FoodAdapter.FoodViewHolder


class FoodAdapter: PagingDataAdapter<Food, FoodViewHolder>(
    Food.diffCallback
) {

    class FoodViewHolder(
        val binding: FoodItemViewBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bindFood(item: Food) {
            binding.food = item
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(
        holder: FoodViewHolder, position: Int
    ) {
        getItem(position)?.let { holder.bindFood(it) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): FoodViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FoodItemViewBinding.inflate(layoutInflater)
        return FoodViewHolder(binding)
    }
}