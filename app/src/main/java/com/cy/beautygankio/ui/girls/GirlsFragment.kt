package com.cy.beautygankio.ui.girls

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.cy.beautygankio.R
import com.cy.beautygankio.databinding.GirlsFragmentBinding
import com.cy.beautygankio.ui.girls.adapter.GirlAdapter
import com.cy.beautygankio.ui.girls.adapter.GirlsDecoration
import com.cy.beautygankio.ui.girls.adapter.GirlsLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GirlsFragment : Fragment() {

    private lateinit var adapter:GirlAdapter
    private var searchJob: Job? = null
    var currentPosition:Int = 0
    var mView:View? = null

    companion object {
        fun newInstance() = GirlsFragment()
    }

    private lateinit var binding :GirlsFragmentBinding
    private val viewModel: GirlsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = GirlsFragmentBinding.inflate(inflater, container, false)

        if (mView == null){
            adapter = GirlAdapter(this)
            binding.girlsList.also {
                it.adapter = adapter.withLoadStateFooter(
                    footer = GirlsLoadStateAdapter { adapter.retry() }
                )
                adapter.addLoadStateListener { loadState ->
                    binding.loadState = loadState.source.refresh
                }
                it.addItemDecoration(GirlsDecoration())
            }
            binding.retryButton.setOnClickListener { adapter.retry() }
            initSwipeFresh()
            subscribeUi(adapter, binding)

            mView = binding.root
        }
        return mView
    }

    private fun initSwipeFresh() {
        binding.swiperefresh.apply {
            setColorSchemeColors(resources.getColor(R.color.colorAccent))
            setOnRefreshListener {
                adapter.refresh()
            }
        }
    }

    private fun prepareTransitions() {
        parentFragment.also {
            it?.exitTransition = TransitionInflater.from(it?.context)
                .inflateTransition(R.transition.grid_exit_transition)
            it?.postponeEnterTransition()

        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scrollToPosition()
        prepareTransitions()

    }

    private fun scrollToPosition() {
        binding.girlsList.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(
                v: View,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {
                binding.girlsList.removeOnLayoutChangeListener(this)
                val layoutManager: RecyclerView.LayoutManager = binding.girlsList.layoutManager!!
                val viewAtPosition = layoutManager.findViewByPosition(currentPosition)
                // Scroll to position if the view for the current position is null (not currently part of
                // layout manager children), or it's not completely visible.
                if (viewAtPosition == null || layoutManager
                        .isViewPartiallyVisible(viewAtPosition, false, true)
                ) {
                    binding.girlsList.post(Runnable {
                        layoutManager.scrollToPosition(currentPosition)
                    })
                }
            }
        })
    }
    private fun subscribeUi(adapter: GirlAdapter, binding: GirlsFragmentBinding) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.girls.collectLatest {
                adapter.submitData(it)
                binding.swiperefresh.isRefreshing = false
            }
        }

    }

}