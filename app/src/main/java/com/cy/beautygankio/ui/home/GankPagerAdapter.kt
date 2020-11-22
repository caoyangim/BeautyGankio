package com.cy.beautygankio.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cy.beautygankio.ui.girls.GirlsFragment

const val GIRL_PAGE_INDEX = 0
const val UNDETERMINED_PAGE_INDEX = 1

class GankPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment){

    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        GIRL_PAGE_INDEX to { GirlsFragment.newInstance() }
//        UNDETERMINED_PAGE_INDEX to { GirlsFragment.newInstance() }
    )

    override fun getItemCount(): Int = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment =
        tabFragmentsCreators[position]?.invoke()?: throw IndexOutOfBoundsException()


}