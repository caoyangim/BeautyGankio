package com.cy.beautygankio.ui.girls.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.cy.beautygankio.data.Girl
import com.cy.beautygankio.databinding.ListItemGirlsBinding

abstract class ItemViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
    abstract fun bind(t:Girl)
}