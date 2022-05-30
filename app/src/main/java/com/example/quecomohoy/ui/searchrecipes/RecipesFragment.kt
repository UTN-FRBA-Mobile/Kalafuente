package com.example.quecomohoy.ui.searchrecipes

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
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
        val adapter = RecipesAdapter(emptyList())
        binding.recipesRecycler.adapter = adapter

        recipeViewModel.recipes.observe(viewLifecycleOwner){
            binding.progress.isVisible = false
            binding.recipesRecycler.isVisible = true
            adapter.updateData(it);
        }

        recipeViewModel.isSearching.observe(viewLifecycleOwner){
            binding.recipesRecycler.isInvisible = it
            binding.progress.isVisible = it
        }

        val ingredientIds = arguments?.getIntArray("ids")?.toList()

        ingredientIds?.also {
            recipeViewModel.getRecipesByIngredients(it)
        }
    }

}