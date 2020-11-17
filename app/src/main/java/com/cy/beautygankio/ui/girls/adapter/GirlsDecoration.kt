package com.cy.beautygankio.ui.girls.adapter

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

class GirlsDecoration :RecyclerView.ItemDecoration(){

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        outRect.set(10,10,10,10)
    }
}