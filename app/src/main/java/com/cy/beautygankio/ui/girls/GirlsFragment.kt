package com.cy.beautygankio.ui.girls

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.cy.beautygankio.databinding.GirlsFragmentBinding
import com.cy.beautygankio.ui.girls.adapter.GirlAdapter
import com.cy.beautygankio.ui.girls.adapter.GirlsDecoration
import com.cy.beautygankio.ui.girls.adapter.GirlsLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GirlsFragment : Fragment() {

    private val adapter = GirlAdapter()

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
        subscribeUi(adapter,binding)
        return binding.root
    }

    private fun subscribeUi(adapter: GirlAdapter, binding: GirlsFragmentBinding) {
        /*viewModel.girls.observe(viewLifecycleOwner){
            adapter.submitList(it)
            binding.hasGirls = true
        }*/
        lifecycleScope.launch {
            viewModel.findGirls().collectLatest {
//                binding.hasGirls = true
                adapter.submitData(it)
            }
        }

    }

}