package com.cy.beautygankio.ui.girls.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
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

        return binding.root
    }

    private fun prepareSharedElementTransition() {

        val transition = TransitionInflater.from(context)
            .inflateTransition(R.transition.image_shared_element_transition)
        sharedElementEnterTransition = transition

        postponeEnterTransition()
    }

}