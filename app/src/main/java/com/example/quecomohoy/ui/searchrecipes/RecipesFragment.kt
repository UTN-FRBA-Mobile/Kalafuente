package com.example.quecomohoy.ui.searchrecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
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

    private val recipeViewModel: RecipeViewModel by viewModels(
        { requireParentFragment() },
        { RecipeViewModelFactory() }
    )

    companion object {
        const val NAV_ACTION_ID = "navigationActionId"
        fun newInstance(@IdRes navigationActionId: Int): RecipesFragment {
            val fragment = RecipesFragment()
            val args = Bundle()
            args.putInt("navigationActionId", navigationActionId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navActionId = arguments?.getInt(NAV_ACTION_ID)
        val adapter = if(navActionId != 0 && navActionId != null) RecipesAdapter(navigationActionId = navActionId) else RecipesAdapter()

        binding.recipesRecycler.adapter = adapter

        recipeViewModel.recipes.observe(viewLifecycleOwner) {
            adapter.updateData(it);
        }

        recipeViewModel.isSearching.observe(viewLifecycleOwner) {
            binding.recipesRecycler.isInvisible = it
            binding.progress.isVisible = it
        }

        val ingredientIds = arguments?.getIntArray("ids")?.toList()

        ingredientIds?.also {
            recipeViewModel.getRecipesByIngredients(it)
        }
    }

}