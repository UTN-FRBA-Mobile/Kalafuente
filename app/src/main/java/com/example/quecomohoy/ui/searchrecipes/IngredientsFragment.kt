package com.example.quecomohoy.ui.searchrecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.example.quecomohoy.data.model.Ingredient
import com.example.quecomohoy.databinding.FragmentIngredientsBinding
import com.example.quecomohoy.ui.IngredientViewModel
import com.example.quecomohoy.ui.IngredientViewModelFactory
import com.example.quecomohoy.ui.searchrecipes.adapters.IngredientsAdapter
import com.example.quecomohoy.ui.searchrecipes.adapters.SelectedIngredientAdapter

class IngredientsFragment : Fragment() {

    private var _binding: FragmentIngredientsBinding? = null

    private val binding get() = _binding!!

    private val ingredientViewModel : IngredientViewModel by viewModels(
        factoryProducer = {IngredientViewModelFactory()},
        ownerProducer = {requireParentFragment()}
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIngredientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showAddedIngredients(false)

        val adpater = IngredientsAdapter { ingredient: Ingredient ->
            binding.verticalRecyclerView.isInvisible = true
            ingredientViewModel.addIngredient(ingredient)
            showAddedIngredients(true)
        }

        val addedIngredientsAdapter = SelectedIngredientAdapter(ingredientViewModel.getSelectedIngredients()){ index: Int ->
            ingredientViewModel.removeIngredient(index)
            showAddedIngredients(ingredientViewModel.hasSelectedIngredients())
        }

        binding.verticalRecyclerView.adapter = adpater
        binding.horizontalRecyclerView.adapter = addedIngredientsAdapter

        ingredientViewModel.ingredients.observe(viewLifecycleOwner){
            binding.progress.isVisible = false
            binding.notFound.isVisible = it.isNullOrEmpty()
            binding.verticalRecyclerView.isVisible = it.isNotEmpty()
            adpater.updateData(it)
        }

        ingredientViewModel.addedIngredient.observe(viewLifecycleOwner){
            if(lifecycle.currentState != Lifecycle.State.STARTED){
                addedIngredientsAdapter.addItem(it)
            }
        }

        ingredientViewModel.isSearching.observe(viewLifecycleOwner){
            binding.progress.isVisible = it
            binding.notFound.isVisible = false
            binding.verticalRecyclerView.isInvisible = it
            showAddedIngredients(!it)
        }
    }

    private fun showAddedIngredients(b : Boolean){
        binding.selectedIngredients.isVisible = b
    }
}