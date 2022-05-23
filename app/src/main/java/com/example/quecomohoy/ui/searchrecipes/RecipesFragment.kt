package com.example.quecomohoy.ui.searchrecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.quecomohoy.databinding.FragmentRecipesBinding
import com.example.quecomohoy.ui.RecipeViewModel
import com.example.quecomohoy.ui.RecipeViewModelFactory
import com.example.quecomohoy.ui.searchrecipes.adapters.RecipesAdapter

class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null

    private val binding get() = _binding!!

    private val recipeViewModel : RecipeViewModel by viewModels(
        {requireParentFragment()},
        {RecipeViewModelFactory()}
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adpater = RecipesAdapter(listOf())
        binding.recipesRecycler.adapter = adpater

        recipeViewModel.recipes.observe(viewLifecycleOwner){
            adpater.updateData(it);
        }
    }

}