package com.cy.beautygankio.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import com.cy.beautygankio.R
import com.cy.beautygankio.databinding.FragmentGankBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GankFragment : Fragment() {

    lateinit var binding:FragmentGankBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGankBinding.inflate(inflater,container,false)

        setFragmentResultListener(getString(R.string.fragment_request_key)) { key, bundle ->
            binding.appbarLayout.setExpanded(false)
        }

        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter = GankPagerAdapter(this)
        TabLayoutMediator(tabLayout,viewPager) { tab, position ->
            tab.text = getTabTitle(position)
//            tab.setIcon(getTabIcon(position))
        }.attach()

        return binding.root
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            GIRL_PAGE_INDEX -> R.drawable.ic_attachment
            UNDETERMINED_PAGE_INDEX -> R.drawable.ic_attachment
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            GIRL_PAGE_INDEX -> getString(R.string.GIRL_PAGE_TITLE)
            UNDETERMINED_PAGE_INDEX -> getString(R.string.GIRL_PAGE_TITLE)
            else -> null
        }
    }


}