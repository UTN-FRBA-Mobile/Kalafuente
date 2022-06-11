package com.example.quecomohoy.ui.searchrecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.example.quecomohoy.R
import com.example.quecomohoy.data.model.Ingredient
import com.example.quecomohoy.databinding.FragmentIngredientsBinding
import com.example.quecomohoy.ui.IngredientViewModel
import com.example.quecomohoy.ui.IngredientViewModelFactory
import com.example.quecomohoy.ui.Status
import com.example.quecomohoy.ui.searchrecipes.adapters.IngredientsAdapter
import com.example.quecomohoy.ui.searchrecipes.adapters.SelectedIngredientAdapter
import com.google.android.material.snackbar.Snackbar

class IngredientsFragment : Fragment() {

    private var _binding: FragmentIngredientsBinding? = null

    private val binding get() = _binding!!

    private val ingredientViewModel: IngredientViewModel by viewModels(
        factoryProducer = { IngredientViewModelFactory() },
        ownerProducer = { requireParentFragment() }
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

        val addedIngredientsAdapter =
            SelectedIngredientAdapter(ingredientViewModel.getSelectedIngredients()) { index: Int ->
                ingredientViewModel.removeIngredient(index)
                showAddedIngredients(ingredientViewModel.hasSelectedIngredients())
            }

        binding.verticalRecyclerView.adapter = adpater
        binding.horizontalRecyclerView.adapter = addedIngredientsAdapter

        ingredientViewModel.ingredients.observe(viewLifecycleOwner) {
            val searchTerm = it.additionalData?.get("searchTerm") as String?
            when(it.status){
                Status.SUCCESS -> {
                    val ingredients = it.data.orEmpty()
                    binding.progress.isVisible = false
                    binding.notFound.isVisible = ingredients.isEmpty() && !searchTerm.isNullOrEmpty()
                    binding.verticalRecyclerView.isVisible = true
                    adpater.updateData(ingredients)
                }
                Status.LOADING -> {
                    binding.progress.isVisible = true
                    binding.notFound.isVisible = false
                    binding.verticalRecyclerView.isVisible = false
                    showAddedIngredients( searchTerm.isNullOrEmpty() && ingredientViewModel.hasSelectedIngredients())
                }
                Status.ERROR -> {
                    binding.progress.isVisible = false
                    binding.verticalRecyclerView.isVisible = false
                    Snackbar.make(view, "Hubo un error", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Reintentar"){
                            searchTerm?.let { s -> ingredientViewModel.getIngredientsByName(s) }
                        }.show()
                }
            }
        }

        ingredientViewModel.addedIngredient.observe(viewLifecycleOwner) {
            if (lifecycle.currentState != Lifecycle.State.STARTED) {
                addedIngredientsAdapter.addItem(it)
            }
        }
    }

    private fun showAddedIngredients(b: Boolean) {
        binding.selectedIngredients.isVisible = b
    }
}