package com.cy.beautygankio.ui.girls.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.cy.beautygankio.R
import com.cy.beautygankio.data.Girl
import com.cy.beautygankio.databinding.ListItemGirlsBinding
import com.cy.beautygankio.ui.girls.GirlsFragment
import com.cy.beautygankio.ui.girls.detail.GirlDetailFragment
import com.cy.beautygankio.ui.home.GankFragmentDirections

val heights = arrayListOf(800,700,750)
class GirlAdapter(val fragment: Fragment) : PagingDataAdapter<Girl, GirlAdapter.GirlViewHolder>(
    GIRL_DIFF
){
    class GirlViewHolder(val fragment: Fragment, private val binding: ListItemGirlsBinding):RecyclerView.ViewHolder(
        binding.root
    ){
        val image = binding.image
        val description = binding.description

        init {
            binding.root.setOnClickListener {
                (fragment as GirlsFragment).currentPosition = adapterPosition
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

            val extras = FragmentNavigatorExtras(
                image to "image_detail_trans",
                description to "description_trans",
                binding.date to "date_day_trans"
            )
            view.findNavController().navigate(direction, extras)
        }

        fun bind(girl: Girl){
            binding.girl = girl
            image.load(girl.url){
                transformations(RoundedCornersTransformation(20f, 20f, 20f, 20f))
                listener(
                    fun(request: ImageRequest) {},
                    fun(request: ImageRequest) {},
                    fun(request, throwable) {
                        fragment.parentFragment!!.startPostponedEnterTransition()
                    },
                    fun(request, metadata) {
                        fragment.parentFragment!!.startPostponedEnterTransition()
                    }
                )
            }
            image.transitionName = girl._id
            description.text = girl.desc
            description.transitionName = girl._id + binding.root.context.getString(R.string.transition_name_suffix_des)
            binding.date.transitionName = girl._id + binding.root.context.getString(R.string.transition_name_suffix_date)

            val params = image.layoutParams
            params.height = heights[girl.likeCounts%(heights.size)]
            image.layoutParams = params
        }

        companion object{
            fun create(fragment: Fragment, parent: ViewGroup): GirlViewHolder {
                val binding = ListItemGirlsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                val vh = GirlViewHolder(fragment, binding)
//                val params = vh.image.layoutParams
//                params.height = heights.random()
//                vh.image.layoutParams = params
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GirlAdapter.GirlViewHolder {
        return GirlAdapter.GirlViewHolder.create(fragment, parent)
    }

    override fun onBindViewHolder(holder: GirlAdapter.GirlViewHolder, position: Int) {
       val girl = getItem(position)
        if (girl != null) {
            holder.bind(girl)
            fragment.startPostponedEnterTransition()
        }
    }
}