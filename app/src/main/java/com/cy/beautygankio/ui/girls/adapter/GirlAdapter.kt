package com.cy.beautygankio.ui.girls.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.cy.beautygankio.R
import com.cy.beautygankio.data.Girl
import com.cy.beautygankio.databinding.ListItemGirlsBinding
import com.cy.beautygankio.ui.home.GankFragmentDirections

val heights = arrayListOf(800,700)
class GirlAdapter : PagingDataAdapter<Girl, GirlAdapter.GirlViewHolder>(GIRL_DIFF){
    class GirlViewHolder(private val binding:ListItemGirlsBinding):RecyclerView.ViewHolder(binding.root){
        val image = binding.image
        val description = binding.description

        init {
            binding.root.setOnClickListener {
                binding.girl?.let { girl ->
                    navigateToPlant(girl, it)
                }
            }
        }

        private fun navigateToPlant(
            girl: Girl,
            view: View
        ) {
            val direction =
                GankFragmentDirections.actionMainFragmentToGirlDetailFragment(
                    girl
                )
            view.findNavController().navigate(direction)
        }

        fun bind(girl: Girl){
            binding.girl = girl
            image.load(girl.url){
                transformations(RoundedCornersTransformation(20f,20f,20f,20f))
            }
            description.text = girl.desc
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