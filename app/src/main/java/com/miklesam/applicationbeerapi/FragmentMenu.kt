package com.miklesam.applicationbeerapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.miklesam.applicationbeerapi.databinding.FragmentMenuBinding
import dagger.hilt.android.AndroidEntryPoint

class FragmentMenu : Fragment(R.layout.fragment_menu) {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentMenuBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMenuBinding.bind(view)
        navController = findNavController()

        binding.beerRandom.setOnClickListener {
            navController.navigate(R.id.action_fragmentMenu_to_fragmentRandomBeer)
        }

        binding.beerCategory.setOnClickListener {
            navController.navigate(R.id.action_fragmentMenu_to_fragmentCategotyBeer)
        }
    }
}