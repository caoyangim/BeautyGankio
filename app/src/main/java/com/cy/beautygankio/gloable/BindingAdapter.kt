package com.cy.beautygankio.gloable


import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.request.CachePolicy

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone:Boolean){
    view.visibility = if (isGone){
        View.GONE
    }else{
        View.VISIBLE
    }
}

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        view.load(imageUrl){
            diskCachePolicy(CachePolicy.ENABLED)
        }

    }
}