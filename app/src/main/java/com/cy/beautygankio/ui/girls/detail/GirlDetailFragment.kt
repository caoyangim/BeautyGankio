package com.cy.beautygankio.ui.girls.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.app.SharedElementCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import coil.load
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.cy.beautygankio.R
import com.cy.beautygankio.databinding.FragmentGirlDetailBinding

class GirlDetailFragment : Fragment() {

    private lateinit var binding:FragmentGirlDetailBinding
    private val args:GirlDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGirlDetailBinding.inflate(inflater, container, false)

        binding.girl = args.girl

        binding.imageDetail.load(args.girl.url){
            listener(
                fun(request: ImageRequest) {},
                fun(request: ImageRequest) {},
                fun(request, throwable) {
                    startPostponedEnterTransition()
                },
                fun(request, metadata) {
                    startPostponedEnterTransition()
                }
            )
        }

        prepareSharedElementTransition()

        binding.imageDetail.setOnClickListener {
            back()
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            back()
        }

        return binding.root
    }

    private fun back() {
        setFragmentResult(getString(R.string.fragment_request_key), bundleOf("bundleKey" to "result"))
        NavHostFragment.findNavController(this@GirlDetailFragment)
            .popBackStack();
    }

    private fun prepareSharedElementTransition() {

        val transition = TransitionInflater.from(context)
            .inflateTransition(R.transition.image_shared_element_transition)
        sharedElementEnterTransition = transition

        postponeEnterTransition()
    }

}