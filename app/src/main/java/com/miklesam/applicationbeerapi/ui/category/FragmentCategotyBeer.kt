package com.miklesam.applicationbeerapi.ui.category

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.paging.LoadState
import com.miklesam.applicationbeerapi.R
import com.miklesam.applicationbeerapi.databinding.FragmentCategoryBinding
import com.miklesam.applicationbeerapi.paging.BeerLoadStateAdapter
import com.miklesam.applicationbeerapi.paging.BeerPagingAdapter
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
            catecoryRecycler.itemAnimator = null
            catecoryRecycler.adapter = adapter.withLoadStateHeaderAndFooter(
                header = BeerLoadStateAdapter {
                    adapter.retry()
                },
                footer = BeerLoadStateAdapter {
                    adapter.retry()
                }
            )
            categoryRetry.setOnClickListener {
                adapter.retry()
            }
        }

        viewModel.beers.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                categoryProgress.isVisible = loadState.source.refresh is LoadState.Loading
                catecoryRecycler.isVisible = loadState.source.refresh is LoadState.NotLoading
                categoryRetry.isVisible = loadState.source.refresh is LoadState.Error
                textWithError.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    catecoryRecycler.isVisible = false
                    textEmpty.isVisible = true
                } else {
                    textEmpty.isVisible = false
                }
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_category, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    binding.catecoryRecycler.scrollToPosition(0)
                    viewModel.searchCategoryNeers(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }
}
