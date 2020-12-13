package com.miklesam.applicationbeerapi.ui.random

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.miklesam.applicationbeerapi.R
import com.miklesam.applicationbeerapi.databinding.FragmentRandomBinding
import com.miklesam.applicationbeerapi.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentRandomBeer : Fragment(R.layout.fragment_random) {

    private val randomViewModel: RandomViewModel by viewModels()
    private lateinit var binding: FragmentRandomBinding
    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRandomBinding.bind(view)
        observeViewModel()
        getRandomBeer()
        binding.fab.setOnClickListener {
            getRandomBeer()
        }
    }

    private fun getRandomBeer() {
        scope.launch {
            randomViewModel.getRandomBeer()
        }
    }

    private fun observeViewModel() {
        val circularProgressDrawable = CircularProgressDrawable(requireContext())
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        randomViewModel.progressBarEvent.observe(viewLifecycleOwner) {
            if (it) {
                binding.randomProgress.visibility = View.VISIBLE
                binding.someText.visibility = View.GONE
                binding.tagLineText.visibility = View.GONE
                binding.descriptionText.visibility = View.GONE
                binding.fab.visibility = View.GONE
                binding.beerImage.visibility = View.GONE
            } else {
                binding.randomProgress.visibility = View.GONE
                binding.someText.visibility = View.VISIBLE
                binding.someText.visibility = View.VISIBLE
                binding.tagLineText.visibility = View.VISIBLE
                binding.descriptionText.visibility = View.VISIBLE
                binding.fab.visibility = View.VISIBLE
                binding.beerImage.visibility = View.VISIBLE
            }
        }

        randomViewModel.beer.observe(viewLifecycleOwner) {
            binding.someText.text = it.name
            binding.tagLineText.text = it.tagline
            binding.descriptionText.text = it.description
            Glide.with(this)
                .load(it.image_url)
                .placeholder(circularProgressDrawable)
                .into(binding.beerImage)
            (activity as MainActivity).supportActionBar?.title = it.name
        }

        randomViewModel.toastEvent.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }
}
