package com.miklesam.applicationbeerapi.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.miklesam.applicationbeerapi.R
import com.miklesam.applicationbeerapi.databinding.FragmentCategoryDetailBinding
import kotlinx.android.synthetic.main.fragment_category_detail.*

class CategoryDetailFragment : Fragment(R.layout.fragment_category_detail) {
    private val args by navArgs<CategoryDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentCategoryDetailBinding.bind(view)
        binding.apply {
            val beer = args.beer
            Glide.with(this@CategoryDetailFragment)
                .load(beer.image_url)
                .error(R.drawable.ic_baseline_sync_24)
                .into(beerImage)
            some_text.text = beer.name
            descriptionText.text = beer.description
            tagLineText.text = beer.tagline
            (activity as MainActivity).supportActionBar?.title = beer.name
        }
    }
}
