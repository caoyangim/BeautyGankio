package com.cy.beautygankio.ui.girls.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.cy.beautygankio.R
import com.cy.beautygankio.data.Girl
import com.cy.beautygankio.databinding.ListItemGirlsBinding

val heights = arrayListOf(800,700)
class GirlAdapter : PagingDataAdapter<Girl, GirlAdapter.GirlViewHolder>(GIRL_DIFF){
    class GirlViewHolder(private val binding:ListItemGirlsBinding):RecyclerView.ViewHolder(binding.root){
        val image = binding.image
        val author = binding.author

        init {
            binding.root.setOnClickListener {
                binding.girl?.title?.let { it1 -> Log.e(">>>", it1) }
            }
        }

        fun bind(girl: Girl){
            binding.girl = girl
            Glide.with(image.context)
                .load(girl.url)
                .transition(withCrossFade())
                .fitCenter()
                .transform(RoundedCorners(20))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(image)

            author.text = girl.desc
        }

        companion object{
            fun create(parent:ViewGroup): GirlViewHolder {
                val binding = ListItemGirlsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                val vh = GirlViewHolder(binding)
                val params = vh.image.layoutParams
                params.height = heights.random()
                vh.image.layoutParams = params
                return vh
            }
        }
    }

    companion object{
         val GIRL_DIFF = object : DiffUtil.ItemCallback<Girl>(){
            override fun areItemsTheSame(oldItem: Girl, newItem: Girl): Boolean {
                return oldItem._id == newItem._id
            }

            override fun areContentsTheSame(oldItem: Girl, newItem: Girl): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GirlViewHolder {
        return GirlViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: GirlViewHolder, position: Int) {
       val girl = getItem(position)
        if (girl != null) {
            holder.bind(girl)
        }
    }
}