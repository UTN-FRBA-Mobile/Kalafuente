package com.example.quecomohoy.ui.searchrecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.quecomohoy.data.model.Ingredient
import com.example.quecomohoy.databinding.FragmentIngredientsBinding
import com.example.quecomohoy.ui.searchrecipes.adapters.SelectedIngredientAdapter
import com.example.quecomohoy.ui.searchrecipes.adapters.IngredientsAdapter

class IngredientsFragment : Fragment() {

    private var _binding: FragmentIngredientsBinding? = null

    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels({requireParentFragment()}, {SearchViewModelFactory()})

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
            viewModel.addIngredient(ingredient)
            showAddedIngredients(true)
        }

        val addedIngredientsAdapter = SelectedIngredientAdapter { index: Int ->
            viewModel.removeIngredient(index)
            showAddedIngredients(viewModel.selectedIngredients.isNotEmpty())
        }

        binding.verticalRecyclerView.adapter = adpater
        binding.horizontalRecyclerView.adapter = addedIngredientsAdapter

        viewModel.ingredients.observe(viewLifecycleOwner){
            adpater.updateData(it)
        }

        viewModel.addedIngredient.observe(viewLifecycleOwner){
            addedIngredientsAdapter.addItem(it)
        }
    }

    fun showAddedIngredients(b : Boolean){
        binding.selectedIngredients.visibility = if(b) View.VISIBLE else View.GONE
    }
}