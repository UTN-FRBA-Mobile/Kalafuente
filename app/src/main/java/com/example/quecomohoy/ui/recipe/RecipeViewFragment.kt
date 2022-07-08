package com.example.quecomohoy.ui.recipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quecomohoy.databinding.RecipeViewFragmentBinding
import com.example.quecomohoy.IngredientAdapter
import com.example.quecomohoy.data.model.recipe.Recipe
import com.example.quecomohoy.ui.RecipeViewModel
import com.example.quecomohoy.ui.RecipeViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso

const val INGREDIENTS_TAB = 0
const val STEPS_TAB = 1

class RecipeViewFragment : Fragment() {
    private var _binding: RecipeViewFragmentBinding? = null
    private val binding get() = _binding!!
    private val recipeViewModel: RecipeViewModel by viewModels(
        { requireParentFragment() },
        { RecipeViewModelFactory() }
    )

    companion object {
        fun newInstance() = RecipeViewFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = RecipeViewFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    private fun initTabs(recipe: Recipe){
        binding.rvStepItem.isVisible = false
        val ingredientsAdapter = IngredientAdapter(recipe.ingredients)
        val stepsAdapter = StepsAdapter(recipe.steps)
        val title: TextView = binding.recomendationTitle

        binding.rvStepItem.adapter = stepsAdapter;
        binding.rvIngredientItem.adapter = ingredientsAdapter;
        title.text = recipe.name
        Picasso.get()
            .load(recipe.picture)
            .fit()
            .into(binding.img);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvIngredientItem.hasFixedSize()
        binding.rvIngredientItem.layoutManager = LinearLayoutManager(requireContext())
        binding.rvStepItem.layoutManager = LinearLayoutManager(requireContext())

        val tabLayout = binding.tabLayout
        recipeViewModel.getRecipeById(requireArguments().getInt("id"))

        recipeViewModel.recipe.observe(viewLifecycleOwner){
            it.data?.let { it1 -> initTabs(it1) }
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.rvIngredientItem.isVisible = tab.position == INGREDIENTS_TAB
                binding.rvStepItem.isVisible = tab.position == STEPS_TAB

            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}