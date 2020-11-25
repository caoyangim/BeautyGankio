package com.cy.beautygankio.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.cy.beautygankio.MainActivity
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
        binding = FragmentGankBinding.inflate(inflater, container, false)

        linkageToolbarAndDrawerWithAnim()

        setFragmentResultListener(getString(R.string.fragment_request_key)) { key, bundle ->
            binding.appbarLayout.setExpanded(false)
        }

        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter = GankPagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
//            tab.setIcon(getTabIcon(position))
        }.attach()

        return binding.root
    }

    //设置导航栏图标
    private fun linkageToolbarAndDrawer() {
        (activity as MainActivity).also {
            val navController = it.findNavController(R.id.nav_host)
            val appBarConfiguration = AppBarConfiguration(navController.graph,binding.drawerLayout)
            binding.toolbar.setupWithNavController(navController,appBarConfiguration)
        }
    }

    //设置带动画的导航栏图标
    private fun linkageToolbarAndDrawerWithAnim() {
        val toggle = ActionBarDrawerToggle(
            activity,binding.drawerLayout,binding.toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        //设置左上角显示三道横线
        toggle.syncState()
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