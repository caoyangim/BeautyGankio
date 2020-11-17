package com.cy.beautygankio.ui.girls.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.cy.beautygankio.R
import com.cy.beautygankio.databinding.GirlsLoadStateFooterViewItemBinding

class GirlsLoadStateAdapter(private val retry: () -> Unit):LoadStateAdapter<GirlsLoadStateAdapter.GirlsLoadStateViewHolder>(){

    class GirlsLoadStateViewHolder(
        private val binding:GirlsLoadStateFooterViewItemBinding,
        retry:() -> Unit
    ):RecyclerView.ViewHolder(binding.root){
        init {
            binding.retryButton.setOnClickListener {
                retry.invoke()
            }
            val lp = binding.root.layoutParams
            if (lp!=null && lp is StaggeredGridLayoutManager.LayoutParams){
                lp.isFullSpan = true
            }
        }

        fun binding(loadState: LoadState){
            if (loadState is LoadState.Error) {
//                binding.errorMsg.text = loadState.error.localizedMessage
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState !is LoadState.Loading
        }

        companion object{
            fun create(parent: ViewGroup,retry: () -> Unit):GirlsLoadStateViewHolder{
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.girls_load_state_footer_view_item,parent,false)
                val binding = GirlsLoadStateFooterViewItemBinding.bind(view)
                return GirlsLoadStateViewHolder(binding,retry)
            }
        }
    }

    override fun onBindViewHolder(holder: GirlsLoadStateViewHolder, loadState: LoadState)
            = holder.binding(loadState)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): GirlsLoadStateViewHolder
            = GirlsLoadStateViewHolder.create(parent,retry)
}