package com.miklesam.applicationbeerapi

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.miklesam.applicationbeerapi.databinding.FragmentRandomBinding
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
        scope.launch {
            randomViewModel.getRandomBeer()
        }
    }

    private fun observeViewModel() {
        randomViewModel.progressBarEvent.observe(viewLifecycleOwner) {
            if (it) {
                binding.randomProgress.visibility = View.VISIBLE
                binding.someText.visibility = View.GONE
            } else {
                binding.randomProgress.visibility = View.GONE
                binding.someText.visibility = View.VISIBLE
            }
        }
    }

}