package com.homan.huang.pagingdemo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.homan.huang.pagingdemo.R
import com.homan.huang.pagingdemo.databinding.FooterNetworkBinding
import com.homan.huang.pagingdemo.ui.FoodFooterAdapter.*
import kotlinx.android.synthetic.main.footer_network.view.*

class FoodFooterAdapter(
    private val retry: () -> Unit
): LoadStateAdapter<FooterNetworkViewHolder>() {

    override fun onBindViewHolder(
        holder: FooterNetworkViewHolder,
        loadState: LoadState
    ) {
        val processBar = holder.itemView.progress_bar
        val retryBt = holder.itemView.retry_button
        val errorTv = holder.itemView.error_msg_tv

        processBar.isVisible = loadState !is LoadState.Loading
        errorTv.isVisible = loadState !is LoadState.Loading
        retryBt.isVisible = loadState !is LoadState.Loading

        if (loadState is LoadState.Error) {
            errorTv.text = loadState.error.localizedMessage
        }

        retryBt.setOnClickListener { retry.invoke() }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): FooterNetworkViewHolder {
        return FooterNetworkViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.footer_network, parent, false)
        )
    }

    class FooterNetworkViewHolder(private val view: View) : RecyclerView.ViewHolder(view)
}