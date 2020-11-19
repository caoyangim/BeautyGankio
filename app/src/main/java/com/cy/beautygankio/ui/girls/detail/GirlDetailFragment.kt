package com.cy.beautygankio.ui.girls.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.cy.beautygankio.databinding.FragmentGirlDetailBinding

/**
 * A simple [Fragment] subclass.
 * Use the [GirlDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GirlDetailFragment : Fragment() {

    private lateinit var binding:FragmentGirlDetailBinding
    private val args:GirlDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGirlDetailBinding.inflate(inflater,container,false)

        binding.girl = args.girl

        return binding.root
    }

}