package com.cy.beautygankio.ui.girls.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigator
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.cy.beautygankio.R
import com.cy.beautygankio.data.Girl
import com.cy.beautygankio.databinding.ListItemGirlsBinding
import com.cy.beautygankio.ui.girls.GirlsFragment
import com.cy.beautygankio.ui.home.GankFragmentDirections
import com.cy.beautygankio.utils.DpPxUtils

val heights = arrayListOf(800,700,750)
class GirlViewHolder(val fragment: Fragment, private val binding: ListItemGirlsBinding):
    ItemViewHolder(
        binding.root
    ){
        val context = binding.root.context!!
        private val image = binding.image
        private val description = binding.description

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
                image to context.getString(R.string.image_detail_trans),
                description to context.getString(R.string.description_trans),
                binding.date to context.getString(R.string.date_day_trans)
            )
            Navigate(view,direction, extras)
        }

        override fun bind(girl: Girl){
            binding.girl = girl
            image.load(girl.url){
                val radius = DpPxUtils.dp2Px(image.context,10f)
                transformations(RoundedCornersTransformation(radius, radius))
                listener(
                    fun(request: ImageRequest) {},
                    fun(request: ImageRequest) {},
                    fun(request, throwable) {
                        fragment.parentFragment?.startPostponedEnterTransition()
                    },
                    fun(request, metadata) {
                        fragment.parentFragment?.startPostponedEnterTransition()
                    }
                )
            }
            description.text = girl.desc
            setTransition(girl)
            //设置一个固定高度，防止列表位置不停变换
            setHeight(girl)
        }

        private fun setHeight(girl: Girl) {
            val params = image.layoutParams
            params.height = heights[girl.likeCounts%(heights.size)]
            image.layoutParams = params
        }

        private fun setTransition(girl: Girl) {
            image.transitionName = girl._id
            description.transitionName = girl._id + binding.root.context.getString(R.string.transition_name_suffix_des)
            binding.date.transitionName = girl._id + binding.root.context.getString(R.string.transition_name_suffix_date)
        }

        companion object{
            fun create(fragment: Fragment, parent: ViewGroup): GirlViewHolder {
                val binding = ListItemGirlsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                val vh = GirlViewHolder(fragment, binding)
                return vh
            }

            var lastTime = 0L
            fun Navigate(view:View, direction: NavDirections, extras: Navigator.Extras){
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastTime > 1000){
                    lastTime = currentTime
                    view.findNavController().navigate(direction, extras)
                }
            }
        }
    }