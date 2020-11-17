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
import coil.load
import coil.transform.RoundedCornersTransformation
import com.cy.beautygankio.R
import com.cy.beautygankio.data.Girl

val heights = arrayListOf(800,700)
class GirlAdapter : PagingDataAdapter<Girl, GirlAdapter.GirlViewHolder>(GIRL_DIFF){
    class GirlViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val image = itemView.findViewById<ImageView>(R.id.image)
        val author = itemView.findViewById<TextView>(R.id.author)

        fun bind(girl: Girl){
            image.load(girl.url){
                transformations(RoundedCornersTransformation(10f,10f,10f,10f))
            }

            author.text = girl.desc
        }

        companion object{
            fun create(parent:ViewGroup): GirlViewHolder {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_girls,parent,false)
                val vh = GirlViewHolder(itemView)
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
        Log.e(">>>","onCreateViewHolder")
        return GirlViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: GirlViewHolder, position: Int) {
       val girl = getItem(position)
        if (girl != null) {
            holder.bind(girl)
        }
    }
}