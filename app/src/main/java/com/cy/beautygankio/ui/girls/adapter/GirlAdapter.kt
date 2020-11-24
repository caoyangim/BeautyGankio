package com.cy.beautygankio.ui.girls.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigator
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.cy.beautygankio.R
import com.cy.beautygankio.data.Girl
import com.cy.beautygankio.databinding.ListItemGirlsBinding
import com.cy.beautygankio.ui.girls.GirlsFragment
import com.cy.beautygankio.ui.girls.detail.GirlDetailFragment
import com.cy.beautygankio.ui.home.GankFragmentDirections
import com.cy.beautygankio.utils.DpPxUtils

const val ITEM_TOP = 1
const val ITEM_NORMAL = 0
class GirlAdapter(val fragment: Fragment) : PagingDataAdapter<Girl, ItemViewHolder>(
    GIRL_DIFF
){

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return when(viewType){
            ITEM_TOP -> TopViewHolder.create(parent)
            ITEM_NORMAL -> GirlViewHolder.create(fragment,parent)
            else -> GirlViewHolder.create(fragment,parent)
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val girl = getItem(position)
        if (girl != null) {
            holder.bind(girl)
            fragment.startPostponedEnterTransition()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return ITEM_TOP
        return ITEM_NORMAL
    }

}