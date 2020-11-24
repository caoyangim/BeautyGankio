package com.cy.beautygankio.ui.girls.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import coil.load
import coil.transform.BlurTransformation
import coil.transform.RoundedCornersTransformation
import com.cy.beautygankio.R
import com.cy.beautygankio.data.Girl
import com.cy.beautygankio.databinding.ListItemGirlsTopBinding
import com.cy.beautygankio.utils.DpPxUtils

class TopViewHolder(private val binding:ListItemGirlsTopBinding) :ItemViewHolder(binding.root){

    val image = binding.bac

    init {

        binding.root.setOnClickListener {
            Toast.makeText(binding.root.context, binding.root.context.getString(R.string.stay_tuned)+">_<",Toast.LENGTH_SHORT).show()
        }

        val lp = binding.root.layoutParams
        if (lp!=null && lp is StaggeredGridLayoutManager.LayoutParams){
            lp.isFullSpan = true
        }
    }

    override fun bind(t: Girl) {
        image.load(t.url){
            transformations(BlurTransformation(image.context,25f,0.8f))
        }
    }

    companion object{
        fun create( parent: ViewGroup): TopViewHolder {
            val binding = ListItemGirlsTopBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return TopViewHolder(binding)
        }
    }

}