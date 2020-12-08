package com.miklesam.applicationbeerapi

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.miklesam.applicationbeerapi.databinding.FragmentCategoryBinding
import com.miklesam.applicationbeerapi.databinding.FragmentRandomBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentCategotyBeer : Fragment(R.layout.fragment_category) {

    private val viewModel by viewModels<CategoryViewModel>()

    private lateinit var binding: FragmentCategoryBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoryBinding.bind(view)

        val adapter = BeerPagingAdapter()

        binding.apply {
            catecoryRecycler.setHasFixedSize(true)
            catecoryRecycler.adapter = adapter.withLoadStateHeaderAndFooter(
                header = BeerLoadStateAdapter{
                    adapter.retry()
                },
                footer = BeerLoadStateAdapter{
                    adapter.retry()
                }
            )
        }

        viewModel.beers.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

    }
}